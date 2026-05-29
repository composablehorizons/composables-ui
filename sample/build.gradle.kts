import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.application)
}

java {
    toolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    jvmToolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":components"))
            implementation(libs.compose.foundation)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.composables.icons.lucide)
            implementation(libs.composables.unstyled)
            implementation(libs.composables.uri.painter)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.composables.unstyled.build.modifier)
        }

        jvmMain.dependencies {
            implementation(project(":preview"))
            implementation(compose.desktop.currentOs) {
                exclude("org.jetbrains.compose.material")
                exclude("org.jetbrains.compose.material3")
            }
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}

tasks.named("wasmJsBrowserDistribution") {
    finalizedBy(injectWasmPreloads)
}

android {
    namespace = "com.composables.ui.sample"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "com.composables.ui.sample"
        minSdk = libs.versions.android.min.sdk.get().toInt()
        targetSdk = libs.versions.android.compile.sdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"
    }
}

compose.desktop {
    application {
        mainClass = "com.composables.ui.sample.MainKt"
    }
}
