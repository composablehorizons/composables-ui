/*
 * Copyright (c) 2026 Composable Horizons
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

val demoVersionName = libs.versions.ui.get()

fun androidVersionCodeFrom(versionName: String): Int {
  val parts = versionName.substringBefore("-").removePrefix("v").split(".").map { it.toInt() }

  check(parts.size == 3) {
    "Expected a semantic version with major, minor, and patch parts, got '$versionName'."
  }

  val (major, minor, patch) = parts
  check(minor in 0..99 && patch in 0..99) {
    "Android versionCode only supports minor and patch values from 0 to 99, got '$versionName'."
  }

  return major * 10_000 + minor * 100 + patch
}

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.compose)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.android.application)
}

compose.resources { packageOfResClass = "com.composables.ui.demo.generated.resources" }

java { toolchain { languageVersion = JavaLanguageVersion.of(17) } }

kotlin {
  jvmToolchain { languageVersion = JavaLanguageVersion.of(17) }

  jvm()

  wasmJs {
    browser {
      val rootDirPath = project.rootDir.path
      val projectDirPath = project.projectDir.path
      commonWebpackConfig {
        outputFileName = "composeApp.js"

        devServer =
            (devServer ?: KotlinWebpackConfig.DevServer()).apply {
              static =
                  (static ?: mutableListOf()).apply {
                    add(rootDirPath)
                    add(projectDirPath)
                  }
            }
      }
    }
    binaries.executable()
  }

  androidTarget { compilerOptions { jvmTarget = JvmTarget.JVM_17 } }

  sourceSets {
    commonMain.dependencies {
      implementation(project(":ui"))
      implementation(libs.composables.icons.lucide)
      implementation(libs.androidx.navigation3.ui)
      implementation(libs.composables.unstyled)
      implementation(libs.composables.unstyled.build.modifier)
      implementation(libs.compose.components.resources)
    }

    jvmMain.dependencies {
      implementation(compose.desktop.currentOs) {
        exclude("org.jetbrains.compose.material")
        exclude("org.jetbrains.compose.material3")
      }
    }
    androidMain.dependencies { implementation(libs.androidx.activity.compose) }
  }
}

android {
  namespace = "com.composables.ui.demo"
  compileSdk = libs.versions.android.compile.sdk.get().toInt()

  signingConfigs {
    getByName("debug") {
      storeFile = layout.projectDirectory.file("demo-debug.keystore").asFile
      storePassword = "android"
      keyAlias = "demo-debug"
      keyPassword = "android"
    }
  }

  defaultConfig {
    applicationId = "com.composables.ui.demo"
    minSdk = libs.versions.android.min.sdk.get().toInt()
    targetSdk = libs.versions.android.compile.sdk.get().toInt()
    versionCode = androidVersionCodeFrom(demoVersionName)
    versionName = demoVersionName
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
