@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

java {
    toolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }
}

compose.resources {
    packageOfResClass = "com.composables.ui.generated.resources"
}

kotlin {
    jvmToolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }

    jvm()

    js {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    android {
        namespace = "com.composables.ui"
        compileSdk = libs.versions.android.compile.sdk.get().toInt()
        minSdk = libs.versions.android.min.sdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposablesUI"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.composables.unstyled)
            implementation("com.composables:composeunstyled-build-modifier:${libs.versions.unstyled.get()}")
            implementation("com.composables:composeunstyled-stack:${libs.versions.unstyled.get()}")
            implementation(libs.composables.interaction.capabilities)
            implementation("com.composables:ripple-indication:1.1.0")
            api("org.jetbrains.compose.foundation:foundation:1.11.0")
            implementation("org.jetbrains.compose.ui:ui:1.11.0")
            implementation("org.jetbrains.compose.runtime:runtime:1.11.0")
            implementation("org.jetbrains.compose.components:components-resources:1.11.0")
        }
    }
}
