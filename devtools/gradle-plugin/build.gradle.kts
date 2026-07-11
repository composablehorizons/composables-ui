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
plugins {
  `kotlin-dsl`
  `java-gradle-plugin`
  `maven-publish`
}

java { toolchain { languageVersion = JavaLanguageVersion.of(17) } }

group = "com.composables"
version = "0.1.0"

dependencies {
  compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.4.0")
  compileOnly("org.jetbrains.compose:compose-gradle-plugin:1.11.1")
  implementation("org.jetbrains.compose.hot-reload:hot-reload-gradle-plugin:1.1.1")
}

gradlePlugin {
  plugins {
    create("composablesDevTools") {
      id = "com.composables.devtools"
      implementationClass = "com.composables.devtools.gradle.ComposablesDevToolsPlugin"
    }
  }
}
