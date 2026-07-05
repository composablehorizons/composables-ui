package com.composables.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.UsageError
import io.ktor.utils.io.streams.asInput
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import io.modelcontextprotocol.kotlin.sdk.server.StdioServerTransport
import io.modelcontextprotocol.kotlin.sdk.types.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.types.Implementation
import io.modelcontextprotocol.kotlin.sdk.types.ServerCapabilities
import io.modelcontextprotocol.kotlin.sdk.types.TextContent
import io.modelcontextprotocol.kotlin.sdk.types.ToolAnnotations
import io.modelcontextprotocol.kotlin.sdk.types.ToolSchema
import java.io.File
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.io.asSink
import kotlinx.io.buffered
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject

class McpStart : CliktCommand("start") {
  override fun help(context: Context): String =
      """
        Starts the Composables MCP server over stdio.
    """
          .trimIndent()

  override fun run() {
    runMcpServer()
  }
}

internal fun runMcpServer() {
  System.setProperty("kotlin-logging.logStartupMessage", "false")

  val server =
      Server(
          Implementation(
              name = "composables",
              version = BuildConfig.Version,
          ),
          ServerOptions(
              capabilities =
                  ServerCapabilities(tools = ServerCapabilities.Tools(listChanged = false)),
          ),
      )

  server.registerComposablesTools()

  val transport =
      StdioServerTransport(
          input = System.`in`.asInput(),
          output = System.out.asSink().buffered(),
      )

  runBlocking {
    val session = server.createSession(transport)
    val done = Job()
    session.onClose { done.complete() }
    done.join()
  }
}

private fun Server.registerComposablesTools() {
  addTool(
      name = "composables_docs_list",
      description = "List all Composables UI documentation pages.",
      inputSchema = ToolSchema(),
      toolAnnotations = ToolAnnotations(readOnlyHint = true, openWorldHint = true),
  ) {
    callTool { fetchDocsApi("list") }
  }

  addTool(
      name = "composables_docs_search",
      description = "Search Composables UI documentation pages.",
      inputSchema =
          ToolSchema(
              properties =
                  buildJsonObject {
                    putJsonObject("query") {
                      put("type", "string")
                      put("description", "Search query, for example 'dropdown menu'.")
                    }
                  },
              required = listOf("query"),
          ),
      toolAnnotations = ToolAnnotations(readOnlyHint = true, openWorldHint = true),
  ) { request ->
    callTool {
      val query = request.arguments.requiredString("query")
      fetchDocsApi("search", mapOf("q" to query))
    }
  }

  addTool(
      name = "composables_docs_get",
      description = "Get one Composables UI documentation page as Markdown by slug.",
      inputSchema =
          ToolSchema(
              properties =
                  buildJsonObject {
                    putJsonObject("slug") {
                      put("type", "string")
                      put("description", "Documentation page slug.")
                    }
                  },
              required = listOf("slug"),
          ),
      toolAnnotations = ToolAnnotations(readOnlyHint = true, openWorldHint = true),
  ) { request ->
    callTool {
      val slug = request.arguments.requiredString("slug")
      fetchUrl(docsMarkdownUrl(slug))
    }
  }

  addTool(
      name = "composables_create_project",
      description =
          "Create a new Compose Multiplatform project using the bundled Composables template.",
      inputSchema =
          ToolSchema(
              properties =
                  buildJsonObject {
                    putJsonObject("directory") {
                      put("type", "string")
                      put("description", "Directory path to create the new project in.")
                    }
                    putJsonObject("packageName") {
                      put("type", "string")
                      put("description", "Java/Kotlin package name, for example 'com.example.app'.")
                    }
                    putJsonObject("appName") {
                      put("type", "string")
                      put("description", "Display name for the app.")
                    }
                    putJsonObject("targets") {
                      put("type", "string")
                      put("description", "Comma-separated targets: android,jvm,ios,wasm.")
                    }
                    putJsonObject("workingDirectory") {
                      put("type", "string")
                      put(
                          "description",
                          "Base directory for resolving a relative project directory.")
                    }
                    putJsonObject("moduleName") {
                      put("type", "string")
                      put("description", "Shared module name. Defaults to 'shared'.")
                    }
                    putJsonObject("overwrite") {
                      put("type", "boolean")
                      put("description", "Overwrite an existing target directory.")
                    }
                  },
              required = listOf("directory", "packageName", "appName", "targets"),
          ),
      toolAnnotations = ToolAnnotations(readOnlyHint = false, destructiveHint = true),
  ) { request ->
    callTool {
      val arguments = request.arguments
      val packageName = arguments.requiredString("packageName")
      val appName = arguments.requiredString("appName")
      val targets = parseTargets(arguments.requiredString("targets"))
      val workingDirectory =
          arguments.optionalString("workingDirectory") ?: System.getProperty("user.dir")
      val target =
          resolveTargetDirectory(
              workingDir = workingDirectory, projectPath = arguments.requiredString("directory"))
      val moduleName = arguments.optionalString("moduleName") ?: SHARED_MODULE
      val overwrite = arguments.optionalBoolean("overwrite") ?: false

      requireValidPackageName(packageName)
      requireValidAppName(appName)
      if (!isValidModuleName(moduleName)) {
        throw UsageError(
            "Invalid module name. Must contain at least one letter or digit and only letters, digits, hyphens, or underscores.")
      }
      validateInitTargetDirectory(target, overwrite)
      if (!target.exists() && !target.mkdirs()) {
        throw UsageError("Failed to create directory at ${target.absolutePath}")
      }

      cloneGradleProjectAt(
          target = target,
          packageName = packageName,
          appName = appName,
          targets = targets,
          moduleName = moduleName,
      )

      """
        Created Compose Multiplatform project at ${target.absolutePath}
        App name: $appName
        Package: $packageName
        Shared module: $moduleName
        Targets: ${targets.joinToString(", ")}
      """
          .trimIndent()
    }
  }

  addTool(
      name = "composables_add_module",
      description = "Add a new Compose app module group to an existing Gradle project.",
      inputSchema =
          ToolSchema(
              properties =
                  buildJsonObject {
                    putJsonObject("projectRoot") {
                      put("type", "string")
                      put("description", "Root directory of an existing Gradle project.")
                    }
                    putJsonObject("path") {
                      put("type", "string")
                      put("description", "Module group path to create inside the project.")
                    }
                    putJsonObject("packageName") {
                      put("type", "string")
                      put(
                          "description",
                          "Java/Kotlin package name, for example 'com.example.feature'.")
                    }
                    putJsonObject("appName") {
                      put("type", "string")
                      put("description", "Display name for the generated app module group.")
                    }
                    putJsonObject("targets") {
                      put("type", "string")
                      put("description", "Comma-separated targets: android,jvm,ios,wasm.")
                    }
                    putJsonObject("overwrite") {
                      put("type", "boolean")
                      put("description", "Overwrite an existing target directory.")
                    }
                  },
              required = listOf("path", "packageName", "appName", "targets"),
          ),
      toolAnnotations = ToolAnnotations(readOnlyHint = false, destructiveHint = true),
  ) { request ->
    callTool {
      val arguments = request.arguments
      val projectRoot =
          File(arguments.optionalString("projectRoot") ?: System.getProperty("user.dir"))
              .absoluteFile
      val packageName = arguments.requiredString("packageName")
      val appName = arguments.requiredString("appName")
      val targets = parseTargets(arguments.requiredString("targets"))
      val target =
          resolveTargetDirectory(
              workingDir = projectRoot.absolutePath, projectPath = arguments.requiredString("path"))
      val overwrite = arguments.optionalBoolean("overwrite") ?: false

      validateExistingGradleProject(projectRoot)
      requireValidPackageName(packageName)
      requireValidAppName(appName)
      validateModuleTargetDirectory(projectRoot, target, overwrite)

      val modulePath = toRelativeModulePath(projectRoot, target)
      val conventions = inferProjectConventions(projectRoot, targets)
      val includedModules =
          createModuleGroup(
              projectRoot = projectRoot,
              appRootDir = target,
              packageName = packageName,
              appName = appName,
              targets = targets,
              conventions = conventions,
          )
      addModuleToSettings(projectRoot, includedModules)

      """
        Added Compose app module group at ${target.absolutePath}
        App name: $appName
        Package: $packageName
        Module: $modulePath
        Targets: ${targets.joinToString(", ")}
        Included Gradle modules: ${includedModules.joinToString(", ")}
      """
          .trimIndent()
    }
  }
}

private fun callTool(block: () -> String): CallToolResult =
    try {
      CallToolResult(content = listOf(TextContent(block())))
    } catch (error: UsageError) {
      CallToolResult(
          content = listOf(TextContent(error.message ?: "Invalid request.")), isError = true)
    } catch (error: IllegalArgumentException) {
      CallToolResult(
          content = listOf(TextContent(error.message ?: "Invalid request.")), isError = true)
    } catch (error: Exception) {
      CallToolResult(content = listOf(TextContent(error.message ?: "Tool failed.")), isError = true)
    }

private fun JsonObject?.requiredString(name: String): String {
  val value = optionalString(name)
  if (value.isNullOrBlank()) {
    throw UsageError("The '$name' parameter is required.")
  }
  return value
}

private fun JsonObject?.optionalString(name: String): String? =
    this?.get(name)?.stringValueOrNull()?.trim()?.takeIf { it.isNotEmpty() }

private fun JsonObject?.optionalBoolean(name: String): Boolean? =
    this?.get(name)?.let { element ->
      (element as? JsonPrimitive)?.booleanOrNull
          ?: throw UsageError("The '$name' parameter must be a boolean.")
    }

private fun JsonElement.stringValueOrNull(): String? =
    (this as? JsonPrimitive)?.jsonPrimitive?.contentOrNull

private fun requireValidPackageName(packageName: String) {
  if (!isValidPackageName(packageName)) {
    throw UsageError(
        "Invalid package name. Must be a valid Java package name (e.g., com.example.app)")
  }
}

private fun requireValidAppName(appName: String) {
  if (!isValidAppName(appName)) {
    throw UsageError("Invalid app name. Must contain at least one letter or digit")
  }
}
