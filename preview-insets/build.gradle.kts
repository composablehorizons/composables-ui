@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
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

    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":ui"))
            implementation(libs.compose.animation)
            api(libs.compose.foundation)
            implementation(libs.compose.runtime)
            implementation(libs.compose.ui)
        }
    }
}
