import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.application)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    jvmToolchain {
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
            implementation(project(":ui"))
            implementation(libs.compose.foundation)
            implementation(libs.androidx.navigation3.ui)
            implementation(libs.composables.unstyled)
            implementation(libs.composables.uri.painter)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.composables.unstyled.build.modifier)
            implementation(project(":preview-insets"))
        }

        wasmJsMain.dependencies {
            implementation(project(":preview"))
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
