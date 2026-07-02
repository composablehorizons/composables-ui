import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "webApp.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        wasmJsMain.dependencies {
            implementation(libs.compose.ui)
            implementation(project(":apps:dashboard:shared"))
        }
    }
}

fun registerPreloadInjectionTask(
    distributionTarget: String,
    markerName: String,
    includeWasmArtifacts: Boolean,
) = tasks.run {
    val taskName = "inject${distributionTarget.replaceFirstChar(Char::titlecase)}Preloads"
    if (taskName in names) {
        named(taskName)
    } else {
        register(taskName) {
    description = "Injects preload links for generated $distributionTarget distribution artifacts."
    val distributionDir = layout.buildDirectory.dir("dist/$distributionTarget/productionExecutable")
    val preloadMarker = markerName
    val preloadWasmArtifacts = includeWasmArtifacts

    doLast {
        val distDir = distributionDir.get().asFile
        val indexFile = distDir.resolve("index.html")
        if (!indexFile.isFile) return@doLast

        val scriptPreloads = distDir
            .listFiles { file -> file.isFile && file.extension == "js" }
            .orEmpty()
            .sortedBy { it.name }
            .map { "  <link rel=\"preload\" href=\"${it.name}\" as=\"script\">" }

        val artifactPreloads = if (preloadWasmArtifacts) {
            distDir
                .listFiles { file -> file.isFile && file.extension == "wasm" }
                .orEmpty()
                .sortedBy { it.name }
                .map {
                    "  <link rel=\"preload\" href=\"${it.name}\" as=\"fetch\" type=\"application/wasm\" crossorigin>"
                }
        } else {
            emptyList()
        }

        val preloadBlock = (scriptPreloads + artifactPreloads).joinToString(
            separator = "\n",
            prefix = "  <!-- $preloadMarker:start -->\n",
            postfix = "\n  <!-- $preloadMarker:end -->",
        )

        val existingPreloadBlock = Regex(
            pattern = "\\n?  <!-- $preloadMarker:start -->.*?  <!-- $preloadMarker:end -->\\n?",
            options = setOf(RegexOption.DOT_MATCHES_ALL),
        )
        val indexHtml = indexFile.readText().replace(existingPreloadBlock, "\n")
        val updatedIndexHtml = indexHtml.replaceFirst("</title>", "</title>\n$preloadBlock")
        indexFile.writeText(updatedIndexHtml)
    }
}
    }
}

val injectWasmPreloads = registerPreloadInjectionTask(
    distributionTarget = "wasmJs",
    markerName = "wasm-preloads",
    includeWasmArtifacts = true,
)

tasks.matching { it.name == "wasmJsBrowserDistribution" }.configureEach {
    finalizedBy(injectWasmPreloads)
}
