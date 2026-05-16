@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
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

kotlin {
    jvmToolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }

    jvm()

    js {
        browser()
    }

    android {
        namespace = "com.composables.one"
        compileSdk = libs.versions.android.compile.sdk.get().toInt()
        minSdk = libs.versions.android.min.sdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposablesOne"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.composables.unstyled.bottom.sheet)
            implementation(libs.composables.unstyled.build.modifier)
            implementation(libs.composables.unstyled.button)
            implementation(libs.composables.unstyled.checkbox)
            implementation(libs.composables.unstyled.colored.indication)
            implementation(libs.composables.unstyled.dialog)
            implementation(libs.composables.unstyled.dropdown.menu)
            implementation(libs.composables.unstyled.focus.ring)
            implementation(libs.composables.unstyled.icon)
            implementation(libs.composables.unstyled.modal)
            implementation(libs.composables.unstyled.modal.bottom.sheet)
            implementation(libs.composables.unstyled.outline)
            implementation(libs.composables.unstyled.progress)
            implementation(libs.composables.unstyled.radio.group)
            implementation(libs.composables.unstyled.scrim)
            implementation(libs.composables.unstyled.separators)
            implementation(libs.composables.unstyled.slider)
            implementation(libs.composables.unstyled.tab.group)
            implementation(libs.composables.unstyled.text.field)
            api(libs.composables.unstyled.theming)
            implementation(libs.composables.unstyled.toggle.switch)
            implementation(libs.composables.unstyled.window.container.size)
            api(libs.kotlinx.datetime)

            api(compose.foundation)
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(libs.ktor.client.core)
            implementation(libs.composables.icons.lucide)
        }
    }
}
