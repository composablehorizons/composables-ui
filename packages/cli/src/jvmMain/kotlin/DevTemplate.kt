package com.composables.cli

import java.io.File

private const val DEFAULT_TEMPLATE_APP_NAME = "Template Dev App"
private const val DEFAULT_TEMPLATE_PACKAGE = "com.example.templatedev"
private const val DEFAULT_TEMPLATE_MODULE = "composeApp"
private const val DEFAULT_TEMPLATE_DIR = "app"

fun main() {
  val outputRoot =
      System.getProperty("composables.template.outputRoot")
          ?: error("Missing system property: composables.template.outputRoot")

  val projectDirName = System.getProperty("composables.template.projectDir", DEFAULT_TEMPLATE_DIR)
  val packageName = System.getProperty("composables.template.package", DEFAULT_TEMPLATE_PACKAGE)
  val moduleName = System.getProperty("composables.template.module", DEFAULT_TEMPLATE_MODULE)
  val appName = System.getProperty("composables.template.appName", DEFAULT_TEMPLATE_APP_NAME)
  val targets = parseTargets(System.getProperty("composables.template.targets"))

  val rootDir = File(outputRoot)
  if (!rootDir.exists()) {
    rootDir.mkdirs()
  }

  val targetDir = rootDir.resolve(projectDirName)
  if (targetDir.exists()) {
    targetDir.deleteRecursively()
  }

  cloneGradleProject(
      targetDir = rootDir.absolutePath,
      dirName = projectDirName,
      packageName = packageName,
      appName = appName,
      targets = targets,
      moduleName = moduleName,
  )

  println("Template rendered at ${targetDir.absolutePath}")
  println("Targets: ${targets.joinToString(", ")}")
}

private fun parseTargets(rawTargets: String?): Set<String> {
  val parsedTargets =
      rawTargets?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() }?.toSet()
          ?: setOf(ANDROID, JVM, IOS, WASM)

  require(parsedTargets.isNotEmpty()) { "At least one target is required" }
  require(parsedTargets.all { it in setOf(ANDROID, JVM, IOS, WASM) }) {
    "Unsupported targets: ${parsedTargets.filterNot { it in setOf(ANDROID, JVM, IOS, WASM) }}"
  }

  return parsedTargets
}
