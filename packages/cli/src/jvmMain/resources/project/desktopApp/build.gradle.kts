import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  alias(libs.plugins.jetbrains.kotlin.multiplatform)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.jetbrains.compose.compiler)
}

kotlin {
  jvm()

  sourceSets {
    jvmMain.dependencies {
      implementation(projects.{{shared_module_accessor}})
      implementation(compose.desktop.currentOs)
    }
  }
}

compose.desktop {
  application {
    mainClass = "{{namespace}}.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "{{namespace}}"
      packageVersion = "1.0.0"
    }
  }
}
