package com.composables.cli

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import java.io.File
import java.nio.file.Files
import java.util.concurrent.TimeUnit
import kotlin.test.Test

class NpmPackageIntegrationTest {

  @Test
  fun `published npm package installs and runs`() {
    val rootDir = createTempRoot("composables-cli-npm")
    try {
      val tarball = npmPackageTarball()

      val installResult =
          runProcess(
              command = listOf(npmExecutable(), "install", tarball.absolutePath),
              workingDir = rootDir,
              timeoutSeconds = 120,
          )

      assertThat(installResult.finished).isTrue()
      assertThat(installResult.exitCode).isEqualTo(0)
      assertThat(installResult.output).contains("added")

      val versionResult =
          runProcess(
              command = listOf(npmInstalledLauncher(rootDir).absolutePath, "--version"),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(versionResult.finished).isTrue()
      assertThat(versionResult.exitCode).isEqualTo(0)
      assertThat(Regex("""\d+\.\d+\.\d+""").matches(versionResult.output.trim())).isTrue()
    } finally {
      rootDir.deleteRecursively()
    }
  }

  private fun npmPackageTarball(): File {
    val tarballDir = File("build/npm/tarball")
    val tarballs = tarballDir.listFiles { file -> file.extension == "tgz" }.orEmpty()
    check(tarballs.size == 1) {
      "Expected exactly one npm tarball in ${tarballDir.absolutePath}, found ${tarballs.joinToString { it.name }}"
    }
    return tarballs.single()
  }

  private fun createTempRoot(prefix: String): File {
    val safePrefix =
        if (System.getProperty("os.name").startsWith("Windows")) {
          prefix.take(8)
        } else {
          prefix
        }
    return Files.createTempDirectory(safePrefix).toFile()
  }

  private fun npmInstalledLauncher(rootDir: File): File {
    val launcherName =
        if (System.getProperty("os.name").startsWith("Windows")) "composables.cmd"
        else "composables"
    val launcher = File(rootDir, "node_modules/.bin/$launcherName")
    check(launcher.isFile) { "Expected npm launcher at ${launcher.absolutePath}" }
    return launcher
  }

  private fun npmExecutable(): String =
      if (System.getProperty("os.name").startsWith("Windows")) "npm.cmd" else "npm"

  private fun runProcess(
      command: List<String>,
      workingDir: File,
      timeoutSeconds: Long,
  ): ProcessResult {
    val process =
        ProcessBuilder(platformCommand(command))
            .directory(workingDir)
            .redirectErrorStream(true)
            .start()

    val output = StringBuilder()
    val readerThread =
        Thread {
              process.inputStream.bufferedReader().useLines { lines ->
                lines.forEach { line -> output.appendLine(line) }
              }
            }
            .apply { start() }

    val finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS)
    if (!finished) {
      process.destroyForcibly()
    }
    readerThread.join()

    return ProcessResult(
        finished = finished,
        exitCode = if (finished) process.exitValue() else -1,
        output = output.toString(),
    )
  }

  private fun platformCommand(command: List<String>): List<String> {
    val executable = command.firstOrNull() ?: return command
    val isWindows = System.getProperty("os.name").startsWith("Windows")
    return if (isWindows && (executable.endsWith(".bat") || executable.endsWith(".cmd"))) {
      listOf("cmd.exe", "/c") + command
    } else {
      command
    }
  }

  private data class ProcessResult(
      val finished: Boolean,
      val exitCode: Int,
      val output: String,
  )
}
