import org.jetbrains.compose.desktop.application.dsl.TargetFormat

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

    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":one"))
            implementation(libs.androidx.navigation.compose)
            implementation(libs.composables.icons.lucide)
            implementation(libs.composables.unstyled.button)
            implementation(libs.composables.unstyled.icon)
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs) {
                    exclude("org.jetbrains.compose.material")
                    exclude("org.jetbrains.compose.material3")
                }
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.composables.one.demo.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "composables-one-demo"
            packageVersion = "1.0.0"
        }
    }
}
