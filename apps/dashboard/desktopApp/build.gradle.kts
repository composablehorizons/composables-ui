import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(project(":apps:dashboard:shared"))
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.composables.ui.dashboard.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.composables.ui.dashboard"
            packageVersion = "1.0.0"
        }
    }
}
