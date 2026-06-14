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
