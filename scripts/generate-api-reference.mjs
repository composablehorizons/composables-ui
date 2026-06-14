import fs from 'node:fs/promises';
import path from 'node:path';
import process from 'node:process';

const repoRoot = path.resolve(import.meta.dirname, '..');
const outputDir = path.resolve(process.argv[2] ?? path.join(repoRoot, 'build/generated/docs/pages'));
const pagesDir = path.join(repoRoot, 'docs/pages');
const apiReferencePath = path.join(repoRoot, 'docs/api-reference.yml');
const versionsPath = path.join(repoRoot, 'gradle/libs.versions.toml');
const sourceDir = path.join(repoRoot, 'ui/src/commonMain/kotlin/com/composables/ui');

function fail(message) {
  console.error(`generate-api-reference: ${message}`);
  process.exit(1);
}

function escapeRegex(value) {
  return value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
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

function normalizeSymbolConfig(symbol) {
  if (typeof symbol === 'string') return { name: symbol };
  if (symbol && typeof symbol === 'object' && typeof symbol.name === 'string') return symbol;
  fail(`Invalid API symbol config: ${JSON.stringify(symbol)}.`);
}

function parseApiReferenceConfig(yaml) {
  const config = {};
  let currentId = null;
  let currentSymbol = null;
  let inSymbols = false;
  let inValues = false;

  for (const [lineIndex, rawLine] of yaml.split(/\r?\n/).entries()) {
    const withoutComment = rawLine.replace(/\s+#.*$/, '');
    if (withoutComment.trim() === '') continue;

    const indent = withoutComment.match(/^\s*/)[0].length;
    const line = withoutComment.trim();

    if (indent === 0) {
      const match = line.match(/^([A-Za-z0-9._-]+):$/);
      if (!match) fail(`Invalid API reference config at line ${lineIndex + 1}: ${rawLine}`);
      currentId = match[1];
      config[currentId] = { symbols: [] };
      currentSymbol = null;
      inSymbols = false;
      inValues = false;
      continue;
    }

    if (!currentId) fail(`Config entry appears before an API id at line ${lineIndex + 1}.`);

    if (indent === 2 && line === 'symbols:') {
      inSymbols = true;
      inValues = false;
      currentSymbol = null;
      continue;
    }

    if (!inSymbols) fail(`Only symbols are supported in API reference config at line ${lineIndex + 1}.`);

    if (indent === 4 && line.startsWith('- ')) {
      const value = line.slice(2).trim();
      inValues = false;
      if (value.startsWith('name: ')) {
        currentSymbol = { name: value.slice('name: '.length).trim() };
        config[currentId].symbols.push(currentSymbol);
      } else {
        currentSymbol = null;
        config[currentId].symbols.push(value);
      }
      continue;
    }

    if (indent === 6 && line === 'values:') {
      if (!currentSymbol) fail(`values must follow an object symbol at line ${lineIndex + 1}.`);
      currentSymbol.values = [];
      inValues = true;
      continue;
    }

    if (indent === 8 && line.startsWith('- ')) {
      if (!inValues || !currentSymbol?.values) fail(`Value appears outside values list at line ${lineIndex + 1}.`);
      currentSymbol.values.push(line.slice(2).trim());
      continue;
    }

    fail(`Invalid API reference config at line ${lineIndex + 1}: ${rawLine}`);
  }

  for (const [id, entry] of Object.entries(config)) {
    if (entry.symbols.length === 0) fail(`API reference "${id}" must define at least one symbol.`);
  }

  return config;
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

function parseKDoc(source, declarationStart) {
  const beforeDeclaration = source.slice(0, declarationStart).trimEnd();
  if (!beforeDeclaration.endsWith('*/')) return null;

  const kDocStart = beforeDeclaration.lastIndexOf('/**');
  if (kDocStart === -1) return null;

  const raw = beforeDeclaration.slice(kDocStart)
    .replace(/^\/\*\*/, '')
    .replace(/\*\/$/, '');
  const lines = raw.split(/\r?\n/)
    .map((line) => line.replace(/^\s*\*\s?/, '').trimEnd());

  const tags = new Map();
  const body = [];
  for (const line of lines) {
    if (line.startsWith('@param ')) {
      const value = line.slice('@param '.length).trim();
      const separator = value.search(/\s/);
      if (separator === -1) {
        tags.set(value, '');
      } else {
        tags.set(value.slice(0, separator), value.slice(separator).trim());
      }
      continue;
    }

    if (line.startsWith('@')) continue;
    body.push(line);
  }

  return {
    text: body.join('\n').trim(),
    params: tags,
  };
}

function parseFunctions(source, name) {
  const escapedName = escapeRegex(name);
  const matches = [...source.matchAll(
    new RegExp(
      String.raw`\b(?:suspend\s+)?fun\s+(?:<[^>]+>\s+)?(?:[A-Za-z0-9_?.<>,()\s]+\.)?${escapedName}\s*\(`,
      'g',
    ),
  )];
  if (matches.length === 0) return null;

  return matches.map((match) => {
    const declarationStart = findDeclarationStart(source, match.index);
    let index = match.index + match[0].length;
    let depth = 1;
    let paramsSource = '';
    while (index < source.length && depth > 0) {
      const char = source[index];
      if (char === '(') depth += 1;
      if (char === ')') depth -= 1;
      if (depth > 0) paramsSource += char;
      index += 1;
    }

    const params = splitTopLevel(paramsSource)
      .map((param) => {
        const cleaned = param.replace(/^(@Composable\s+)?(crossinline\s+|noinline\s+)?/, '').trim();
        const colonIndex = cleaned.indexOf(':');
        if (colonIndex === -1) return null;
        const paramName = cleaned.slice(0, colonIndex).trim();
        const type = trimDefault(cleaned.slice(colonIndex + 1));
        return { name: paramName, type };
      })
      .filter(Boolean);

    const declarationEnd = findDeclarationEnd(source, index);
    const signature = source.slice(declarationStart, declarationEnd).trim();
    const kDoc = parseKDoc(source, declarationStart);

    return {
      kDoc,
      params,
      signature,
    };
  });
}

function parseTopLevelProperty(source, name) {
  const escapedName = escapeRegex(name);
  const match = source.match(new RegExp(String.raw`^val\s+${escapedName}\b`, 'm'));
  if (!match || match.index == null) return null;

  const declarationStart = findDeclarationStart(source, match.index);
  const declarationEnd = findDeclarationEnd(source, match.index + match[0].length);
  const signature = source.slice(declarationStart, declarationEnd).trim();
  const kDoc = parseKDoc(source, declarationStart);

  return signature === '' ? null : { kDoc, signature };
}

function findDeclarationStart(source, index) {
  let start = source.lastIndexOf('\n', index - 1);
  start = start === -1 ? 0 : start + 1;

  while (start > 0) {
    const previousLineEnd = start - 1;
    const previousLineStart = source.lastIndexOf('\n', previousLineEnd - 1);
    const lineStart = previousLineStart === -1 ? 0 : previousLineStart + 1;
    const line = source.slice(lineStart, previousLineEnd).trim();
    if (!line.startsWith('@')) {
      break;
    }
    start = lineStart;
  }

  return start;
}

function findDeclarationEnd(source, index) {
  let current = index;
  let parenDepth = 0;
  let angleDepth = 0;
  while (current < source.length) {
    const char = source[current];
    if (char === '(') parenDepth += 1;
    if (char === ')') parenDepth = Math.max(0, parenDepth - 1);
    if (char === '<') angleDepth += 1;
    if (char === '>') angleDepth = Math.max(0, angleDepth - 1);
    if ((char === '{' || char === '=') && parenDepth === 0 && angleDepth === 0) {
      break;
    }
    current += 1;
  }
  return current;
}

function parseTypeDeclaration(source, name) {
  const escapedName = escapeRegex(name);
  const match = source.match(
    new RegExp(String.raw`\b(?:value\s+class|class|enum\s+class|object)\s+${escapedName}\b`, 'm'),
  );
  if (!match || match.index == null) return null;

  const declarationStart = findDeclarationStart(source, match.index);
  const declarationEnd = findDeclarationEnd(source, match.index + match[0].length);
  const signature = source.slice(declarationStart, declarationEnd).trim();
  const kDoc = parseKDoc(source, declarationStart);

  return signature === '' ? null : { kDoc, signature };
}

function parseEnumValues(source, name) {
  const match = source.match(new RegExp(`enum\\s+class\\s+${name}\\s*\\{([\\s\\S]*?)\\n\\}`));
  if (!match) return null;
  return splitTopLevel(match[1])
    .map((value) => value.replace(/\/\/.*$/gm, '').trim())
    .map((value) => value.replace(/,$/, '').trim())
    .filter(Boolean);
}

function findMatchingBrace(source, openBraceIndex) {
  let depth = 0;
  let inString = false;
  let stringQuote = '';

  for (let index = openBraceIndex; index < source.length; index += 1) {
    const char = source[index];
    const previous = source[index - 1];

    if (inString) {
      if (char === stringQuote && previous !== '\\') {
        inString = false;
      }
      continue;
    }

    if (char === '"' || char === "'") {
      inString = true;
      stringQuote = char;
      continue;
    }

    if (char === '{') depth += 1;
    if (char === '}') depth -= 1;
    if (depth === 0) return index;
  }

  return -1;
}

function parseCompanionValues(source, name) {
  const classMatch = source.match(new RegExp(`\\b(?:value\\s+class|class|object|enum\\s+class)\\s+${escapeRegex(name)}\\b`));
  if (!classMatch || classMatch.index == null) return null;

  const classOpenBrace = source.indexOf('{', classMatch.index);
  if (classOpenBrace === -1) return null;

  const classCloseBrace = findMatchingBrace(source, classOpenBrace);
  if (classCloseBrace === -1) return null;

  const classBody = source.slice(classOpenBrace + 1, classCloseBrace);
  const companionMatch = classBody.match(/\bcompanion\s+object\s*\{/);
  if (!companionMatch || companionMatch.index == null) return null;

  const companionOpenBrace = classOpenBrace + 1 + companionMatch.index + companionMatch[0].length - 1;
  const companionCloseBrace = findMatchingBrace(source, companionOpenBrace);
  if (companionCloseBrace === -1) return null;

  const companionBodyStart = companionOpenBrace + 1;
  const companionBody = source.slice(companionBodyStart, companionCloseBrace);
  return [...companionBody.matchAll(/\bval\s+([A-Za-z0-9_]+)/g)].map((match) => {
    const declarationStart = findDeclarationStart(source, companionBodyStart + match.index);
    return {
      description: parseKDoc(source, declarationStart)?.text ?? '',
      name: match[1],
    };
  });
}

async function readAllKotlinSources() {
  const files = (await fs.readdir(sourceDir, { recursive: true }))
    .filter((file) => file.endsWith('.kt'))
    .map((file) => path.join(sourceDir, file));
  const entries = await Promise.all(files.map(async (file) => [file, await fs.readFile(file, 'utf8')]));
  return entries.map(([, source]) => source).join('\n\n');
}

function parseVersionCatalogVersions(toml) {
  const versions = new Map();
  let inVersionsSection = false;

  for (const line of toml.split(/\r?\n/)) {
    const trimmed = line.trim();
    if (trimmed === '' || trimmed.startsWith('#')) continue;

    const sectionMatch = trimmed.match(/^\[([^\]]+)]$/);
    if (sectionMatch) {
      inVersionsSection = sectionMatch[1] === 'versions';
      continue;
    }

    if (!inVersionsSection) continue;

    const versionMatch = trimmed.match(/^([A-Za-z0-9_.-]+)\s*=\s*"([^"]+)"\s*(?:#.*)?$/);
    if (versionMatch) {
      versions.set(versionMatch[1], versionMatch[2]);
    }
  }

  return versions;
}

function resolveVersionTokens(markdown, versions, pagePath) {
  return markdown.replaceAll(
    /\{\{\s*libs\.versions\.([A-Za-z0-9_.-]+)\s*}}/g,
    (match, versionName) => {
      const version = versions.get(versionName);
      if (!version) {
        fail(`Unknown version catalog token '${match}' in ${path.relative(repoRoot, pagePath)}.`);
      }
      return version;
    },
  );
}

function requireKDoc(kDoc, symbolName) {
  if (!kDoc || kDoc.text === '') {
    fail(`Missing KDoc description for API symbol "${symbolName}".`);
  }
  return kDoc;
}

function renderFunctionReference(name, signature, params, kDoc) {
  const doc = requireKDoc(kDoc, name);
  const rows = params.map((param) => {
    const description = doc.params.get(param.name) ?? '';
    return `| \`${escapeMarkdown(param.name)}\` | \`${escapeMarkdown(param.type)}\` | ${escapeMarkdown(description)} |`;
  });
  const output = [
    `### ${name}`,
    '',
    doc.text,
    '',
    '```kotlin',
    signature,
    '```',
    '',
  ];
  if (rows.length > 0) {
    output.push(
      '| Parameter | Type | Description |',
      '|-----------|------|-------------|',
      ...rows,
      '',
    );
  }
  return output.join('\n');
}

function renderValuesReference(name, signature, values, kDoc) {
  const doc = requireKDoc(kDoc, name);
  const rows = values.map((value) => `| \`${escapeMarkdown(value.name)}\` | ${escapeMarkdown(value.description)} |`);
  return [
    `### ${name}`,
    '',
    doc.text,
    '',
    '```kotlin',
    signature,
    '```',
    '',
    '| Value | Description |',
    '|-------|-------------|',
    ...rows,
    '',
  ].join('\n');
}

function renderPropertyReference(name, signature, kDoc) {
  const doc = requireKDoc(kDoc, name);
  return [
    `### ${name}`,
    '',
    doc.text,
    '',
    '```kotlin',
    signature,
    '```',
    '',
  ].join('\n');
}

function configuredValues(allValues, symbolConfig) {
  if (!symbolConfig.values) return allValues;

  return symbolConfig.values.map((valueName) => {
    const value = allValues.find((candidate) => candidate.name === valueName);
    if (!value) {
      fail(`Could not find configured value "${valueName}" for "${symbolConfig.name}".`);
    }
    return value;
  });
}

function renderReference(id, source, apiReference) {
  const config = apiReference[id];
  if (!config) fail(`Missing API description for "${id}".`);

  return config.symbols.map((rawSymbol) => {
    const symbolConfig = normalizeSymbolConfig(rawSymbol);
    const symbol = symbolConfig.name;
    const overloads = parseFunctions(source, symbol);
    if (overloads) {
      return overloads
        .map((overload, index) => renderFunctionReference(
          overloads.length > 1 ? `${symbol} (${index + 1})` : symbol,
          overload.signature,
          overload.params,
          overload.kDoc,
        ))
        .join('\n');
    }

    const property = parseTopLevelProperty(source, symbol);
    if (property) {
      return renderPropertyReference(symbol, property.signature, property.kDoc);
    }

    const enumValues = parseEnumValues(source, symbol);
    if (enumValues) {
      const declaration = parseTypeDeclaration(source, symbol);
      if (!declaration) fail(`Could not find type declaration for "${symbol}".`);
      const values = configuredValues(enumValues.map((value) => ({ description: '', name: value })), symbolConfig);
      return renderValuesReference(symbol, declaration.signature, values, declaration.kDoc);
    }

    const companionValues = parseCompanionValues(source, symbol);
    if (companionValues) {
      const declaration = parseTypeDeclaration(source, symbol);
      if (!declaration) fail(`Could not find type declaration for "${symbol}".`);
      return renderValuesReference(symbol, declaration.signature, configuredValues(companionValues, symbolConfig), declaration.kDoc);
    }

    fail(`Could not find API symbol "${symbol}" for "${id}".`);
  }).join('\n');
}

async function main() {
  const apiReference = parseApiReferenceConfig(await fs.readFile(apiReferencePath, 'utf8'));
  const versions = parseVersionCatalogVersions(await fs.readFile(versionsPath, 'utf8'));
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
      (_match, id) => renderReference(id, source, apiReference),
    );
    await fs.writeFile(path.join(outputDir, entry.name), resolveVersionTokens(expanded, versions, pagePath));
  }
}

main().catch((error) => fail(error.stack ?? error.message));
