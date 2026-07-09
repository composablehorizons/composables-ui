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
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.compose)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.android.application)
}

java { toolchain { languageVersion = JavaLanguageVersion.of(17) } }

kotlin {
  jvmToolchain { languageVersion = JavaLanguageVersion.of(17) }

  jvm()

  @OptIn(ExperimentalWasmDsl::class)
  wasmJs {
    browser { commonWebpackConfig { outputFileName = "composeApp.js" } }
    binaries.executable()
  }

  androidTarget { compilerOptions { jvmTarget = JvmTarget.JVM_17 } }

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

    wasmJsMain.dependencies { implementation(project(":preview")) }

    jvmMain.dependencies {
      implementation(project(":preview"))
      implementation(compose.desktop.currentOs) {
        exclude("org.jetbrains.compose.material")
        exclude("org.jetbrains.compose.material3")
      }
    }

    androidMain.dependencies { implementation(libs.androidx.activity.compose) }
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

compose.desktop { application { mainClass = "com.composables.ui.sample.MainKt" } }
