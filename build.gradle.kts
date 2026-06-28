plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.maven.publish) apply false
    alias(libs.plugins.spotless)
}

subprojects {
    apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target(
                fileTree(project.projectDir) {
                    include("src/**/*.kt")
                    exclude("src/**/kotlin/**/iconography/**/*.kt")
                }
            )
            ktlint().editorConfigOverride(
                mapOf(
                    "indent_size" to 2,
                    "continuation_indent_size" to 2,
                    "ktlint_standard_filename" to "disabled",
                )
            )
            licenseHeaderFile(rootProject.file("$rootDir/spotless/copyright.kt"))
        }
        format("kts") {
            target("**/*.kts")
            targetExclude("**/build/**/*.kts")
            licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
        }
        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
            licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
        }
    }

    val injectWasmPreloads by tasks.registering {
        description = "Injects preload links for generated Wasm distribution artifacts."

        doLast {
            val distDir = layout.buildDirectory.dir("dist/wasmJs/productionExecutable").get().asFile
            val indexFile = distDir.resolve("index.html")
            if (!indexFile.isFile) return@doLast

            val scriptPreloads = distDir
                .listFiles { file -> file.isFile && file.extension == "js" }
                .orEmpty()
                .sortedBy { it.name }
                .map { """  <link rel="preload" href="${it.name}" as="script">""" }

            val wasmPreloads = distDir
                .listFiles { file -> file.isFile && file.extension == "wasm" }
                .orEmpty()
                .sortedBy { it.name }
                .map { """  <link rel="preload" href="${it.name}" as="fetch" type="application/wasm" crossorigin>""" }

            val preloadBlock = (scriptPreloads + wasmPreloads).joinToString(
                separator = "\n",
                prefix = "  <!-- wasm-preloads:start -->\n",
                postfix = "\n  <!-- wasm-preloads:end -->",
            )

            val existingPreloadBlock = Regex(
                pattern = """\n?  <!-- wasm-preloads:start -->.*?  <!-- wasm-preloads:end -->\n?""",
                options = setOf(RegexOption.DOT_MATCHES_ALL),
            )
            val indexHtml = indexFile.readText().replace(existingPreloadBlock, "\n")
            val updatedIndexHtml = indexHtml.replaceFirst("</title>", "</title>\n$preloadBlock")
            indexFile.writeText(updatedIndexHtml)
        }
    }

    tasks.matching { it.name == "wasmJsBrowserDistribution" }.configureEach {
        finalizedBy(injectWasmPreloads)
    }
}

val docsSource = layout.projectDirectory.dir("docs")
val docsSourcesFile = docsSource.file("sources.yml")
val generatedDocsPages = layout.buildDirectory.dir("generated/docs/pages")
val generatedDemoSources = layout.buildDirectory.dir("generated/docs/demo-sources")
val generatedComponentSources = layout.buildDirectory.dir("generated/docs/component-sources")

data class DocsSourceSection(
    val root: String,
    val files: Map<String, String>,
)

data class DocsSources(
    val demos: DocsSourceSection,
    val components: DocsSourceSection,
)

fun readDocsSources(file: File): DocsSources {
    val roots = linkedMapOf<String, String>()
    val files = linkedMapOf(
        "demos" to linkedMapOf<String, String>(),
        "components" to linkedMapOf<String, String>(),
    )
    var currentSection: String? = null
    var inFiles = false

    file.readLines().forEachIndexed { index, rawLine ->
        val lineWithoutComment = rawLine.replace(Regex("""\s+#.*$"""), "")
        if (lineWithoutComment.isBlank()) return@forEachIndexed

        val indent = lineWithoutComment.indexOfFirst { !it.isWhitespace() }.let { if (it == -1) 0 else it }
        val line = lineWithoutComment.trim()

        if (indent == 0) {
            check(line.endsWith(":")) {
                "Invalid docs sources entry at ${file.relativeTo(rootDir)}:${index + 1}: $rawLine"
            }
            val section = line.removeSuffix(":")
            check(section in files.keys) {
                "Unsupported docs sources section '$section' at ${file.relativeTo(rootDir)}:${index + 1}"
            }
            currentSection = section
            inFiles = false
            return@forEachIndexed
        }

        check(currentSection != null) {
            "Invalid docs sources entry at ${file.relativeTo(rootDir)}:${index + 1}: $rawLine"
        }

        if (indent == 2) {
            if (line == "files:") {
                inFiles = true
                return@forEachIndexed
            }

            val separator = line.indexOf(": ")
            check(separator > 0 && line.take(separator) == "root") {
                "Invalid docs sources section entry at ${file.relativeTo(rootDir)}:${index + 1}: $rawLine"
            }
            roots[currentSection!!] = line.drop(separator + 2)
            inFiles = false
            return@forEachIndexed
        }

        check(indent == 4 && inFiles) {
            "Invalid docs sources mapping at ${file.relativeTo(rootDir)}:${index + 1}: $rawLine"
        }

        val separator = line.indexOf(": ")
        check(separator > 0) {
            "Invalid docs sources mapping at ${file.relativeTo(rootDir)}:${index + 1}: $rawLine"
        }
        files.getValue(currentSection!!)[line.take(separator)] = line.drop(separator + 2)
    }

    listOf("demos", "components").forEach { section ->
        check(roots[section]?.isNotBlank() == true) {
            "Missing root for docs sources section '$section' in ${file.relativeTo(rootDir)}"
        }
        check(files.getValue(section).isNotEmpty()) {
            "Missing files for docs sources section '$section' in ${file.relativeTo(rootDir)}"
        }
    }

    return DocsSources(
        demos = DocsSourceSection(root = roots.getValue("demos"), files = files.getValue("demos")),
        components = DocsSourceSection(root = roots.getValue("components"), files = files.getValue("components")),
    )
}

val docsSources = readDocsSources(docsSourcesFile.asFile)
val docsDemoSourceRoot = layout.projectDirectory.dir(docsSources.demos.root)
val docsComponentSourceRoot = layout.projectDirectory.dir(docsSources.components.root)
val docsDemoSources = docsSources.demos.files
val docsComponentSources = docsSources.components.files

val generateDocsDemoSources by tasks.registering {
    group = "documentation"
    description = "Prepares Composables UI demo sources for display in documentation."

    inputs.file(docsSourcesFile)
    inputs.files(docsDemoSources.values.distinct().map { docsDemoSourceRoot.file(it) })
    outputs.dir(generatedDemoSources)

    doLast {
        val outputDirectory = generatedDemoSources.get().asFile
        outputDirectory.deleteRecursively()
        outputDirectory.mkdirs()

        docsDemoSources.values.distinct().forEach { fileName ->
            val sourceFile = docsDemoSourceRoot.file(fileName).asFile
            check(sourceFile.isFile) {
                "Missing demo source: ${sourceFile.relativeTo(rootDir)}"
            }
            val displaySource = sourceFile
                .readText()
                .replace(Regex("""\A\s*/\*.*?\*/\s*""", RegexOption.DOT_MATCHES_ALL), "")
                .replace(Regex("""(?m)^\s*package\s+[A-Za-z0-9_.]+\s*\R+"""), "")
                .trim('\n')

            outputDirectory.resolve(fileName).writeText(displaySource + "\n")
        }
    }
}

val generateDocsComponentSources by tasks.registering {
    group = "documentation"
    description = "Prepares Composables UI component sources for display in documentation."

    inputs.file(docsSourcesFile)
    inputs.files(docsComponentSources.values.map { docsComponentSourceRoot.file(it) })
    outputs.dir(generatedComponentSources)

    doLast {
        val outputDirectory = generatedComponentSources.get().asFile
        outputDirectory.deleteRecursively()
        outputDirectory.mkdirs()

        docsComponentSources.forEach { (displayName, sourcePath) ->
            val sourceFile = docsComponentSourceRoot.file(sourcePath).asFile
            check(sourceFile.isFile) {
                "Missing component source: ${sourceFile.relativeTo(rootDir)}"
            }

            val outputFile = outputDirectory.resolve(displayName)
            outputFile.parentFile?.mkdirs()
            outputFile.writeText(sourceFile.readText().trimEnd() + "\n")
        }
    }
}

val generateDocsApiReference by tasks.registering(Exec::class) {
    group = "documentation"
    description = "Expands Composables UI docs markers and version tokens."

    inputs.dir(docsSource.dir("pages"))
    inputs.file(docsSource.file("api-reference.yml"))
    inputs.file(layout.projectDirectory.file("scripts/generate-api-reference.mjs"))
    inputs.file(layout.projectDirectory.file("gradle/libs.versions.toml"))
    inputs.files(fileTree("ui/src/commonMain/kotlin") {
        include("**/*.kt")
    })
    outputs.dir(generatedDocsPages)

    commandLine(
        "node",
        layout.projectDirectory.file("scripts/generate-api-reference.mjs").asFile.absolutePath,
        generatedDocsPages.get().asFile.absolutePath,
    )
}

tasks.register<Sync>("bundleDocs") {
    group = "documentation"
    description = "Bundles Composables UI docs and the demo web app."

    dependsOn(":demo:wasmJsBrowserDistribution")
    dependsOn(generateDocsApiReference)
    dependsOn(generateDocsDemoSources)
    dependsOn(generateDocsComponentSources)

    val bundleDirectory = layout.buildDirectory.dir("docs-bundle/ui")
    val demoDistribution = project(":demo").layout.buildDirectory.dir("dist/wasmJs/productionExecutable")

    inputs.file(docsSourcesFile)

    into(bundleDirectory)
    from(docsSource.file("docs.yml"))
    from(generatedDocsPages) {
        into("pages")
    }
    from(generatedDemoSources) {
        into("demo-sources")
    }
    from(generatedComponentSources) {
        into("component-sources")
    }
    from(demoDistribution) {
        into("apps/composables-ui-demos")
    }

    doFirst {
        val pages = fileTree(generatedDocsPages) {
            include("**/*.md")
        }.files
        val legacyPatterns = listOf(
            "```compose",
            "<ApiReference",
            "<ComposeApp",
            "<UnstyledDemo",
        )
        val demoPattern = Regex("""<UiDemo\s+id="([A-Za-z0-9._-]+)"\s*/>""")
        val componentSourcePattern = Regex("""<ComponentSource\s+file="([A-Za-z0-9._/-]+\.kt)"\s*/>""")
        val unresolvedVersionTokenPattern = Regex("""\{\{\s*libs\.versions\.[A-Za-z0-9_.-]+\s*}}""")

        pages.forEach { page ->
            val text = page.readText()
            check(unresolvedVersionTokenPattern !in text) {
                "Unresolved version token found in ${page.relativeTo(rootDir)}"
            }

            legacyPatterns.forEach { pattern ->
                check(pattern !in text) {
                    "Legacy Compose preview reference '$pattern' found in ${page.relativeTo(rootDir)}"
                }
            }

            Regex("""<UiDemo\b[^>]*>""").findAll(text).forEach { match ->
                val demoId = demoPattern.matchEntire(match.value)?.groupValues?.get(1)
                check(demoId != null) {
                    "Invalid UiDemo marker in ${page.relativeTo(rootDir)}: ${match.value}"
                }
                check(docsDemoSources.containsKey(demoId)) {
                    "No demo source registered for '$demoId' in ${page.relativeTo(rootDir)}"
                }
            }

            Regex("""<ComponentSource\b[^>]*>""").findAll(text).forEach { match ->
                val sourceFile = componentSourcePattern.matchEntire(match.value)?.groupValues?.get(1)
                check(sourceFile != null) {
                    "Invalid ComponentSource marker in ${page.relativeTo(rootDir)}: ${match.value}"
                }
                check(docsComponentSources.containsKey(sourceFile)) {
                    "No component source registered for '$sourceFile' in ${page.relativeTo(rootDir)}"
                }
            }
        }
    }

    doLast {
        val outputDirectory = bundleDirectory.get().asFile
        val demoManifest = docsDemoSources.entries.joinToString(",\n") { (id, fileName) ->
            """
                "$id": {
                  "app": "composables-ui-demos",
                  "source": "demo-sources/$fileName",
                  "title": "examples/$fileName",
                  "language": "kotlin"
                }""".trimIndent()
        }
        outputDirectory.resolve("manifest.json").writeText(
            """
            {
              "schemaVersion": 1,
              "library": "composables-ui",
              "target": {
                "sourceLibrary": "ui"
              },
              "content": {
                "navigation": "docs.yml",
                "pages": "pages"
              },
              "apps": {
                "composables-ui-demos": {
                  "path": "apps/composables-ui-demos",
                  "entry": "index.html",
                  "idQueryParameter": "id"
                }
              },
              "demos": {
            $demoManifest
              }
            }
            """.trimIndent() + "\n",
        )
    }
}
