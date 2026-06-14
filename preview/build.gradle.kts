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

    sourceSets {
        commonMain.dependencies {
            implementation(project(":ui"))
            implementation(project(":preview-insets"))
            implementation(libs.compose.animation)
            implementation(libs.composables.unstyled)
            api(libs.compose.foundation)
            implementation(libs.compose.runtime)
            implementation(libs.compose.ui)
        }
    }
}
