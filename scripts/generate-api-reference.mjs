import fs from 'node:fs/promises';
import path from 'node:path';
import process from 'node:process';

const repoRoot = path.resolve(import.meta.dirname, '..');
const outputDir = path.resolve(process.argv[2] ?? path.join(repoRoot, 'build/generated/docs/pages'));
const pagesDir = path.join(repoRoot, 'docs/pages');
const descriptionsPath = path.join(repoRoot, 'docs/api-descriptions.json');
const sourceDir = path.join(repoRoot, 'components/src/commonMain/kotlin/com/composables/ui');

function fail(message) {
  console.error(`generate-api-reference: ${message}`);
  process.exit(1);
}

function escapeMarkdown(value) {
  return String(value)
    .replaceAll('|', '\\|')
    .replaceAll('\n', ' ')
    .trim();
}

function trimDefault(value) {
  const index = value.indexOf('=');
  return index === -1 ? value.trim() : value.slice(0, index).trim();
}

function splitTopLevel(value, separator = ',') {
  const parts = [];
  let current = '';
  let depth = 0;
  let inString = false;
  let stringQuote = '';

  for (let index = 0; index < value.length; index += 1) {
    const char = value[index];
    const previous = value[index - 1];
    if (inString) {
      current += char;
      if (char === stringQuote && previous !== '\\') {
        inString = false;
      }
      continue;
    }
    if (char === '"' || char === "'") {
      inString = true;
      stringQuote = char;
      current += char;
      continue;
    }
    if (char === '(' || char === '<' || char === '[' || char === '{') {
      depth += 1;
    } else if (char === ')' || char === '>' || char === ']' || char === '}') {
      depth = Math.max(0, depth - 1);
    }
    if (char === separator && depth === 0) {
      if (current.trim() !== '') parts.push(current.trim());
      current = '';
      continue;
    }
    current += char;
  }

  if (current.trim() !== '') parts.push(current.trim());
  return parts;
}

function parseFunction(source, name) {
  const match = source.match(new RegExp(`(?:@Composable\\s+)?fun\\s+${name}\\s*\\(`));
  if (!match) return null;

  let index = match.index + match[0].length;
  let depth = 1;
  let params = '';
  while (index < source.length && depth > 0) {
    const char = source[index];
    if (char === '(') depth += 1;
    if (char === ')') depth -= 1;
    if (depth > 0) params += char;
    index += 1;
  }

  return splitTopLevel(params)
    .map((param) => {
      const cleaned = param.replace(/^(@Composable\s+)?(crossinline\s+|noinline\s+)?/, '').trim();
      const colonIndex = cleaned.indexOf(':');
      if (colonIndex === -1) return null;
      const paramName = cleaned.slice(0, colonIndex).trim();
      const type = trimDefault(cleaned.slice(colonIndex + 1));
      return { name: paramName, type };
    })
    .filter(Boolean);
}

function parseEnumValues(source, name) {
  const match = source.match(new RegExp(`enum\\s+class\\s+${name}\\s*\\{([\\s\\S]*?)\\n\\}`));
  if (!match) return null;
  return splitTopLevel(match[1])
    .map((value) => value.replace(/\/\/.*$/gm, '').trim())
    .map((value) => value.replace(/,$/, '').trim())
    .filter(Boolean);
}

function parseCompanionValues(source, name) {
  const classMatch = source.match(new RegExp(`class\\s+${name}\\b[\\s\\S]*?companion\\s+object\\s*\\{([\\s\\S]*?)\\n\\s*\\}`));
  if (!classMatch) return null;
  return [...classMatch[1].matchAll(/\bval\s+([A-Za-z0-9_]+)/g)].map((match) => match[1]);
}

async function readAllKotlinSources() {
  const files = (await fs.readdir(sourceDir, { recursive: true }))
    .filter((file) => file.endsWith('.kt'))
    .map((file) => path.join(sourceDir, file));
  const entries = await Promise.all(files.map(async (file) => [file, await fs.readFile(file, 'utf8')]));
  return entries.map(([, source]) => source).join('\n\n');
}

function renderFunctionReference(name, params, config) {
  const rows = params.map((param) => {
    const description = config.parameters?.[param.name] ?? '';
    return `| \`${escapeMarkdown(param.name)}\` | \`${escapeMarkdown(param.type)}\` | ${escapeMarkdown(description)} |`;
  });
  return [
    `### ${name}`,
    '',
    config.descriptions?.[name] ?? '',
    '',
    '| Parameter | Type | Description |',
    '|-----------|------|-------------|',
    ...rows,
    '',
  ].join('\n');
}

function renderValuesReference(name, values, config) {
  const descriptions = config.values?.[name] ?? {};
  const rows = values.map((value) => `| \`${escapeMarkdown(value)}\` | ${escapeMarkdown(descriptions[value] ?? '')} |`);
  return [
    `### ${name}`,
    '',
    config.descriptions?.[name] ?? '',
    '',
    '| Value | Description |',
    '|-------|-------------|',
    ...rows,
    '',
  ].join('\n');
}

function renderReference(id, source, descriptions) {
  const config = descriptions[id];
  if (!config) fail(`Missing API description for "${id}".`);

  return config.symbols.map((symbol) => {
    const params = parseFunction(source, symbol);
    if (params) return renderFunctionReference(symbol, params, config);

    const enumValues = parseEnumValues(source, symbol);
    if (enumValues) return renderValuesReference(symbol, enumValues, config);

    const companionValues = parseCompanionValues(source, symbol);
    if (companionValues) return renderValuesReference(symbol, companionValues, config);

    fail(`Could not find API symbol "${symbol}" for "${id}".`);
  }).join('\n');
}

async function main() {
  const descriptions = JSON.parse(await fs.readFile(descriptionsPath, 'utf8'));
  const source = await readAllKotlinSources();
  const entries = (await fs.readdir(pagesDir, { withFileTypes: true }))
    .filter((entry) => entry.isFile() && entry.name.endsWith('.md'));

  await fs.rm(outputDir, { recursive: true, force: true });
  await fs.mkdir(outputDir, { recursive: true });

  for (const entry of entries) {
    const pagePath = path.join(pagesDir, entry.name);
    const markdown = await fs.readFile(pagePath, 'utf8');
    const expanded = markdown.replaceAll(
      /<ApiReference\s+id="([A-Za-z0-9._-]+)"\s*\/>/g,
      (_match, id) => renderReference(id, source, descriptions),
    );
    await fs.writeFile(path.join(outputDir, entry.name), expanded);
  }
}

main().catch((error) => fail(error.stack ?? error.message));
