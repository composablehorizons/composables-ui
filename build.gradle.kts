plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.android.application) apply false
}

val docsSource = layout.projectDirectory.dir("docs")
val generatedDocsPages = layout.buildDirectory.dir("generated/docs/pages")
val generatedDemoSources = layout.buildDirectory.dir("generated/docs/demo-sources")
val generatedComponentSources = layout.buildDirectory.dir("generated/docs/component-sources")

val docsDemoSources = mapOf(
    "button-sizes" to "ButtonSizesExample.kt",
    "button-primary" to "PrimaryButtonExample.kt",
    "button-secondary" to "SecondaryButtonExample.kt",
    "button-outlined" to "OutlinedButtonExample.kt",
    "button-ghost" to "GhostButtonExample.kt",
    "button-destructive" to "DestructiveButtonExample.kt",
)

val docsComponentSources = mapOf(
    "Button.kt" to "components/src/commonMain/kotlin/com/composables/ui/components/Button.kt",
    "Theme.kt" to "components/src/commonMain/kotlin/com/composables/ui/theme/Theme.kt",
)

val generateDocsDemoSources by tasks.registering {
    group = "documentation"
    description = "Prepares Composables UI demo sources for display in documentation."

    val demoSourceDirectory = project(":demo").layout.projectDirectory.dir("src/commonMain/kotlin/com/composables/ui/demo/examples")

    inputs.files(docsDemoSources.values.distinct().map { demoSourceDirectory.file(it) })
    outputs.dir(generatedDemoSources)

    doLast {
        val outputDirectory = generatedDemoSources.get().asFile
        outputDirectory.deleteRecursively()
        outputDirectory.mkdirs()

        docsDemoSources.values.distinct().forEach { fileName ->
            val sourceFile = demoSourceDirectory.file(fileName).asFile
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

    inputs.files(docsComponentSources.values.map { layout.projectDirectory.file(it) })
    outputs.dir(generatedComponentSources)

    doLast {
        val outputDirectory = generatedComponentSources.get().asFile
        outputDirectory.deleteRecursively()
        outputDirectory.mkdirs()

        docsComponentSources.forEach { (displayName, sourcePath) ->
            val sourceFile = layout.projectDirectory.file(sourcePath).asFile
            check(sourceFile.isFile) {
                "Missing component source: ${sourceFile.relativeTo(rootDir)}"
            }

            outputDirectory.resolve(displayName).writeText(sourceFile.readText().trimEnd() + "\n")
        }
    }
}

val generateDocsApiReference by tasks.registering(Exec::class) {
    group = "documentation"
    description = "Expands Composables UI API reference markers from Kotlin source."

    inputs.dir(docsSource.dir("pages"))
    inputs.file(docsSource.file("api-descriptions.json"))
    inputs.files(fileTree("components/src/commonMain/kotlin") {
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
        val componentSourcePattern = Regex("""<ComponentSource\s+file="([A-Za-z0-9._-]+\.kt)"\s*/>""")

        pages.forEach { page ->
            val text = page.readText()
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
        val deploymentId = System.currentTimeMillis().toString()
        val demoManifest = docsDemoSources.entries.joinToString(",\n") { (id, fileName) ->
            """
                "$id": {
                  "app": "composables-ui-demos",
                  "source": "demo-sources/$fileName",
                  "title": "/examples/$fileName",
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
                  "idQueryParameter": "id",
                  "deploymentId": "$deploymentId"
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
