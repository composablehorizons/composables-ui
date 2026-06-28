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
package com.composables.ui.preview

import java.io.File

actual fun isDevicePreviewHotReloadAvailable(): Boolean {
  return System.getProperty("compose.reload.isActive") == "true"
}

actual fun requestDevicePreviewHotReload() {
  val workingDirectory = File(System.getProperty("user.dir")).absoluteFile
  val gradleRoot = findGradleRoot(workingDirectory)
  val gradleExecutable = gradleRoot?.let { root ->
    val wrapperName = if (isWindows) "gradlew.bat" else "gradlew"
    File(root, wrapperName).takeIf { it.isFile }
  }
  val command = if (gradleExecutable != null) {
    listOf(gradleExecutable.absolutePath, "reload")
  } else {
    listOf("gradle", "reload")
  }

  ProcessBuilder(command)
    .directory(gradleRoot ?: workingDirectory)
    .redirectOutput(ProcessBuilder.Redirect.INHERIT)
    .redirectError(ProcessBuilder.Redirect.INHERIT)
    .start()
}

private val isWindows: Boolean
  get() = System.getProperty("os.name")
    .startsWith("Windows", ignoreCase = true)

private fun findGradleRoot(start: File): File? {
  return generateSequence(start) { it.parentFile }
    .firstOrNull { directory ->
      File(directory, "gradlew").isFile || File(directory, "gradlew.bat").isFile
    }
}
