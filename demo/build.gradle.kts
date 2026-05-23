import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

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
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.application)
}

compose.resources {
    packageOfResClass = "com.composables.ui.demo.generated.resources"
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
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"

                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
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
            implementation(libs.androidx.navigation.compose)
            implementation(libs.compose.components.resources)
            implementation(libs.composables.icons.lucide)
            implementation(libs.composables.unstyled)
            implementation(libs.composables.unstyled.build.modifier)
        }

        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs) {
                    exclude("org.jetbrains.compose.material")
                    exclude("org.jetbrains.compose.material3")
                }
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
    namespace = "com.composables.ui.demo"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "com.composables.ui.demo"
        minSdk = libs.versions.android.min.sdk.get().toInt()
        targetSdk = libs.versions.android.compile.sdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"
    }
}

compose.desktop {
    application {
        mainClass = "com.composables.ui.demo.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "composables-ui-demo"
            packageVersion = "1.0.0"
        }
    }
}
