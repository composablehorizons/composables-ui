package com.composables.cli

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import java.io.File
import java.nio.file.Files
import java.util.concurrent.TimeUnit
import kotlin.test.Test

class CliIntegrationTest {

  @Test
  fun `published shadow jar runs standalone`() {
    val shadowJar = shadowJarArtifact()

    val result =
        runProcess(
            command = listOf("java", "-jar", shadowJar.absolutePath, "--version"),
            workingDir = shadowJar.parentFile,
            timeoutSeconds = 60,
        )

    assertThat(result.finished).isTrue()
    assertThat(result.exitCode).isEqualTo(0)
    assertThat(Regex("""\d+\.\d+\.\d+""").matches(result.output.trim())).isTrue()
    assertThat(result.output).doesNotContain("NoClassDefFoundError")
  }

  @Test
  fun `cli init creates a jvm project that compiles`() {
    val rootDir = createTempRoot("composables-cli-init")
    try {
      val projectDir = File(rootDir, "sample-app")
      val launcher = installedLauncher()

      val createResult =
          runProcess(
              command =
                  listOf(
                      launcher.absolutePath,
                      "init",
                      projectDir.absolutePath,
                      "--package",
                      "com.example.sampleapp",
                      "--app-name",
                      "Sample App",
                      "--targets",
                      "jvm",
                  ),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(createResult.finished).isTrue()
      assertThat(createResult.exitCode).isEqualTo(0)
      assertThat(createResult.output).contains("Success! Your new Compose app is ready")
      assertThat(createResult.output)
          .contains("${projectGradleScript()} :desktopApp:hotRunJvm --auto")
      assertJvmReadme(projectDir)
      assertGeneratedProjectHasGitignore(projectDir)

      val compileResult =
          runProcess(
              command = projectGradleCommand(":shared:compileKotlinJvm"),
              workingDir = projectDir,
              timeoutSeconds = 180,
          )

      assertThat(compileResult.finished).isTrue()
      assertThat(compileResult.exitCode).isEqualTo(0)
      assertThat(compileResult.output).contains("BUILD SUCCESSFUL")
      assertGeneratedProjectUsesKtfmt(projectDir)
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli init creates an android-only project`() {
    val rootDir = createTempRoot("composables-cli-init-android-only")
    try {
      val projectDir = File(rootDir, "sample-app")
      val launcher = installedLauncher()

      val createResult =
          runProcess(
              command =
                  listOf(
                      launcher.absolutePath,
                      "init",
                      projectDir.absolutePath,
                      "--package",
                      "com.example.sampleapp",
                      "--app-name",
                      "Sample App",
                      "--targets",
                      "android",
                  ),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(createResult.finished).isTrue()
      assertThat(createResult.exitCode).isEqualTo(0)
      assertThat(createResult.output).contains("Success! Your new Compose app is ready")
      assertAndroidReadme(projectDir)
      assertThat(File(projectDir, "shared").isDirectory).isTrue()
      assertThat(File(projectDir, "androidApp").isDirectory).isTrue()
      assertThat(File(projectDir, "desktopApp").exists()).isFalse()
      assertThat(File(projectDir, "iosApp").exists()).isFalse()
      assertThat(File(projectDir, "webApp").exists()).isFalse()

      val tasksResult =
          runProcess(
              command = projectGradleCommand("tasks", "--all"),
              workingDir = projectDir,
              timeoutSeconds = 180,
          )

      assertThat(tasksResult.finished).isTrue()
      assertThat(tasksResult.exitCode).isEqualTo(0)
      assertThat(tasksResult.output).contains("androidApp:installDebug")
      assertThat(tasksResult.output).doesNotContain("webApp:wasmJsBrowserDevelopmentRun")
      assertThat(tasksResult.output).doesNotContain("desktopApp:run")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli init creates a wasm-only project that compiles`() {
    val rootDir = createTempRoot("composables-cli-init-wasm-only")
    try {
      val projectDir = File(rootDir, "sample-app")
      val launcher = installedLauncher()

      val createResult =
          runProcess(
              command =
                  listOf(
                      launcher.absolutePath,
                      "init",
                      projectDir.absolutePath,
                      "--package",
                      "com.example.sampleapp",
                      "--app-name",
                      "Sample App",
                      "--targets",
                      "wasm",
                  ),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(createResult.finished).isTrue()
      assertThat(createResult.exitCode).isEqualTo(0)
      assertThat(createResult.output).contains("Success! Your new Compose app is ready")
      assertWasmReadme(projectDir)

      val compileResult =
          runProcess(
              command =
                  projectGradleCommand(
                      ":shared:compileKotlinWasmJs", ":webApp:compileKotlinWasmJs"),
              workingDir = projectDir,
              timeoutSeconds = 180,
          )

      assertThat(compileResult.finished).isTrue()
      assertThat(compileResult.exitCode).isEqualTo(0)
      assertThat(compileResult.output).contains("BUILD SUCCESSFUL")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli init with no args runs interactively and creates a jvm project that compiles`() {
    val rootDir = createTempRoot("composables-cli-init-interactive")
    try {
      val projectDir = File(rootDir, "sample-app")
      val launcher = installedLauncher()

      val createResult =
          runProcess(
              command = listOf(launcher.absolutePath, "init"),
              workingDir = rootDir,
              stdin = "sample-app\ncom.example.sampleapp\nSample App\nn\ny\nn\nn\n",
              timeoutSeconds = 60,
          )

      assertThat(createResult.finished).isTrue()
      assertThat(createResult.exitCode).isEqualTo(0)
      assertThat(createResult.output).contains("Success! Your new Compose app is ready")
      assertThat(createResult.output)
          .contains("${projectGradleScript()} :desktopApp:hotRunJvm --auto")
      assertJvmReadme(projectDir)

      val compileResult =
          runProcess(
              command = projectGradleCommand(":shared:compileKotlinJvm"),
              workingDir = projectDir,
              timeoutSeconds = 180,
          )

      assertThat(compileResult.finished).isTrue()
      assertThat(compileResult.exitCode).isEqualTo(0)
      assertThat(compileResult.output).contains("BUILD SUCCESSFUL")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli init with no args fails cleanly without stdin`() {
    val rootDir = createTempRoot("composables-cli-init-no-stdin")
    try {
      val launcher = installedLauncher()

      val createResult =
          runProcess(
              command = listOf(launcher.absolutePath, "init"),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(createResult.finished).isTrue()
      assertThat(createResult.exitCode).isEqualTo(1)
      assertThat(createResult.output).contains("Interactive mode requires stdin")
      assertThat(createResult.output).doesNotContain("ReadAfterEOFException")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli mcp help lists mcp subcommands`() {
    val rootDir = createTempRoot("composables-cli-mcp-help")
    try {
      val launcher = installedLauncher()

      val result =
          runProcess(
              command = listOf(launcher.absolutePath, "mcp", "--help"),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(result.finished).isTrue()
      assertThat(result.exitCode).isEqualTo(0)
      assertThat(result.output).contains("install")
      assertThat(result.output).contains("start")
      assertThat(result.output).doesNotContain("--client")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli mcp install help exposes client selection`() {
    val rootDir = createTempRoot("composables-cli-mcp-install")
    try {
      val launcher = installedLauncher()

      val result =
          runProcess(
              command = listOf(launcher.absolutePath, "mcp", "install", "--help"),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(result.finished).isTrue()
      assertThat(result.exitCode).isEqualTo(0)
      assertThat(result.output).contains("--client")
      assertThat(result.output).contains("--overwrite")
      assertThat(result.output).contains("firebender")
      assertThat(result.output).contains("zed")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli mcp install writes cursor config`() {
    val rootDir = createTempRoot("composables-cli-mcp-install-cursor")
    try {
      val launcher = installedLauncher()
      File(rootDir, "settings.gradle.kts").writeText("""pluginManagement {}""")
      val nestedDir = File(rootDir, "features/chat").apply { mkdirs() }

      val result =
          runProcess(
              command = listOf(launcher.absolutePath, "mcp", "install", "--client", "cursor"),
              workingDir = nestedDir,
              timeoutSeconds = 60,
          )

      assertThat(result.finished).isTrue()
      assertThat(result.exitCode).isEqualTo(0)
      assertThat(result.output).contains("Installed Composables MCP for Cursor.")
      val content = File(rootDir, ".cursor/mcp.json").readText()
      assertThat(content).contains(""""command": "composables"""")
      assertThat(content).contains(""""mcp"""")
      assertThat(content).contains(""""start"""")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli mcp install fails outside a Gradle project for project clients`() {
    val rootDir = createTempRoot("composables-cli-mcp-install-no-project")
    try {
      val launcher = installedLauncher()

      val result =
          runProcess(
              command = listOf(launcher.absolutePath, "mcp", "install", "--client", "cursor"),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(result.finished).isTrue()
      assertThat(result.exitCode).isEqualTo(1)
      assertThat(result.output).contains("Could not find a Gradle project root")
      assertThat(result.output).contains("Run this command from the root of your project")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli mcp install writes firebender project config`() {
    val rootDir = createTempRoot("composables-cli-mcp-install-firebender")
    try {
      val launcher = installedLauncher()
      File(rootDir, "settings.gradle.kts").writeText("""pluginManagement {}""")

      val result =
          runProcess(
              command = listOf(launcher.absolutePath, "mcp", "install", "--client", "firebender"),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(result.finished).isTrue()
      assertThat(result.exitCode).isEqualTo(0)
      assertThat(result.output).contains("Installed Composables MCP for Firebender.")
      val content = File(rootDir, "firebender.json").readText()
      assertThat(content).contains(""""mcpServers"""")
      assertThat(content).contains(""""command": "composables"""")
      assertThat(content).contains(""""mcp"""")
      assertThat(content).contains(""""start"""")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli mcp install writes zed project config`() {
    val rootDir = createTempRoot("composables-cli-mcp-install-zed")
    try {
      val launcher = installedLauncher()
      File(rootDir, "settings.gradle.kts").writeText("""pluginManagement {}""")

      val result =
          runProcess(
              command =
                  listOf(
                      launcher.absolutePath,
                      "mcp",
                      "install",
                      "--client",
                      "zed",
                  ),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(result.finished).isTrue()
      assertThat(result.exitCode).isEqualTo(0)
      assertThat(result.output).contains("Installed Composables MCP for Zed.")
      val content = File(rootDir, ".zed/settings.json").readText()
      assertThat(content).contains(""""context_servers"""")
      assertThat(content).contains(""""command": "composables"""")
      assertThat(content).contains(""""mcp"""")
      assertThat(content).contains(""""start"""")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli mcp start exposes tools and creates project over stdio`() {
    val rootDir = createTempRoot("composables-cli-mcp-server")
    try {
      val launcher = installedLauncher()
      val projectDir = File(rootDir, "mcp-app")
      val stdin =
          mcpFrame(
              """{"jsonrpc":"2.0","id":1,"method":"initialize","params":{"protocolVersion":"2025-06-18","capabilities":{},"clientInfo":{"name":"test-client","version":"1.0.0"}}}""") +
              mcpFrame("""{"jsonrpc":"2.0","method":"notifications/initialized"}""") +
              mcpFrame("""{"jsonrpc":"2.0","id":2,"method":"tools/list","params":{}}""") +
              mcpFrame(
                  """{"jsonrpc":"2.0","id":3,"method":"tools/call","params":{"name":"composables_create_project","arguments":{"directory":"mcp-app","packageName":"com.example.mcpapp","appName":"MCP App","targets":"jvm"}}}""")

      val result =
          runProcessUntilOutputContains(
              command = listOf(launcher.absolutePath, "mcp", "start"),
              workingDir = rootDir,
              stdin = stdin,
              expectedOutput = "Created Compose Multiplatform project",
              timeoutSeconds = 60,
          )

      assertThat(result.finished).isTrue()
      assertThat(result.output).contains("composables_docs_search")
      assertThat(result.output).contains("composables_create_project")
      assertThat(result.output).contains("Created Compose Multiplatform project")
      assertThat(File(projectDir, "settings.gradle.kts").exists()).isTrue()
      assertThat(
              File(projectDir, "shared/src/commonMain/kotlin/com/example/mcpapp/App.kt").exists())
          .isTrue()
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli mcp start adds module over stdio`() {
    val rootDir = createTempRoot("composables-cli-mcp-add-module")
    try {
      val launcher = installedLauncher()
      val projectDir = File(rootDir, "base-app")
      val createResult =
          runProcess(
              command =
                  listOf(
                      launcher.absolutePath,
                      "init",
                      projectDir.absolutePath,
                      "--package",
                      "com.example.baseapp",
                      "--app-name",
                      "Base App",
                      "--targets",
                      "jvm",
                  ),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )
      assertThat(createResult.finished).isTrue()
      assertThat(createResult.exitCode).isEqualTo(0)

      val stdin =
          mcpFrame(
              """{"jsonrpc":"2.0","id":1,"method":"initialize","params":{"protocolVersion":"2025-06-18","capabilities":{},"clientInfo":{"name":"test-client","version":"1.0.0"}}}""") +
              mcpFrame("""{"jsonrpc":"2.0","method":"notifications/initialized"}""") +
              mcpFrame(
                  """{"jsonrpc":"2.0","id":2,"method":"tools/call","params":{"name":"composables_add_module","arguments":{"projectRoot":"base-app","path":"features/chat","packageName":"com.example.chat","appName":"Chat","targets":"jvm"}}}""")

      val result =
          runProcessUntilOutputContains(
              command = listOf(launcher.absolutePath, "mcp", "start"),
              workingDir = rootDir,
              stdin = stdin,
              expectedOutput = "Added Compose app module group",
              timeoutSeconds = 60,
          )

      assertThat(result.finished).isTrue()
      assertThat(result.output).contains("Added Compose app module group")
      assertThat(File(projectDir, "features/chat/shared").isDirectory).isTrue()
      assertThat(File(projectDir, "features/chat/desktopApp").isDirectory).isTrue()
      assertThat(File(projectDir, "settings.gradle.kts").readText())
          .contains("""include(":features:chat:shared")""")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli init requires overwrite for non-empty directories`() {
    val rootDir = createTempRoot("composables-cli-init-existing")
    try {
      val projectDir =
          File(rootDir, "sample-app").apply {
            mkdirs()
            File(this, "keep.txt").writeText("existing")
          }
      val launcher = installedLauncher()

      val createResult =
          runProcess(
              command =
                  listOf(
                      launcher.absolutePath,
                      "init",
                      projectDir.absolutePath,
                      "--package",
                      "com.example.sampleapp",
                      "--app-name",
                      "Sample App",
                      "--targets",
                      "jvm",
                  ),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(createResult.finished).isTrue()
      assertThat(createResult.exitCode).isEqualTo(1)
      assertThat(createResult.output).contains("already exists and is not empty")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli init with partial args fails without prompting`() {
    val rootDir = createTempRoot("composables-cli-init-partial")
    try {
      val launcher = installedLauncher()

      val createResult =
          runProcess(
              command =
                  listOf(
                      launcher.absolutePath,
                      "init",
                      "sample-app",
                      "--package",
                      "com.example.sampleapp",
                  ),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(createResult.finished).isTrue()
      assertThat(createResult.exitCode).isEqualTo(1)
      assertThat(createResult.output).contains("When using init non-interactively")
      assertThat(createResult.output).contains("--app-name")
      assertThat(createResult.output).contains("--targets")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli add module creates a nested app module group that compiles`() {
    val rootDir = createTempRoot("composables-cli-add-module")
    try {
      val isWindows = System.getProperty("os.name").startsWith("Windows")
      val projectName = if (isWindows) "s" else "sample-app"
      val rootPackage = if (isWindows) "c.e.s" else "com.example.sampleapp"
      val rootAppName = if (isWindows) "S" else "Sample App"
      val nestedModulePath = if (isWindows) "a/f" else "apps/feature-app"
      val nestedPackage = if (isWindows) "c.e.f" else "com.example.featureapp"
      val nestedAppName = if (isWindows) "F" else "Feature App"
      val nestedPackagePath = nestedPackage.replace(".", "/")
      val nestedModuleGradlePath = ":" + nestedModulePath.replace('/', ':')
      val projectDir = File(rootDir, projectName)
      val launcher = installedLauncher()

      val initResult =
          runProcess(
              command =
                  listOf(
                      launcher.absolutePath,
                      "init",
                      projectDir.absolutePath,
                      "--package",
                      rootPackage,
                      "--app-name",
                      rootAppName,
                      "--targets",
                      "android,jvm,wasm",
                  ),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(initResult.finished).isTrue()
      assertThat(initResult.exitCode).isEqualTo(0)

      rewriteProjectToUiStyleConventions(projectDir)
      val rootBuildBeforeAdd = File(projectDir, "build.gradle.kts").readText()
      val versionCatalogBeforeAdd = File(projectDir, "gradle/libs.versions.toml").readText()

      val addResult =
          runProcess(
              command =
                  listOf(
                      launcher.absolutePath,
                      "add",
                      "module",
                      nestedModulePath,
                      "--type",
                      "app",
                      "--package",
                      nestedPackage,
                      "--app-name",
                      nestedAppName,
                      "--targets",
                      "android,jvm,wasm",
                  ),
              workingDir = projectDir,
              timeoutSeconds = 60,
          )

      assertThat(addResult.finished).isTrue()
      assertThat(addResult.exitCode).isEqualTo(0)
      val nestedModuleDir = File(projectDir, nestedModulePath)
      assertThat(File(nestedModuleDir, "shared/build.gradle.kts").exists()).isTrue()
      assertThat(
              File(nestedModuleDir, "shared/src/commonMain/kotlin/$nestedPackagePath/App.kt")
                  .exists())
          .isTrue()
      assertThat(File(nestedModuleDir, "androidApp/build.gradle.kts").exists()).isTrue()
      assertThat(File(nestedModuleDir, "desktopApp/build.gradle.kts").exists()).isTrue()
      assertThat(File(nestedModuleDir, "webApp/build.gradle.kts").exists()).isTrue()
      assertThat(File(nestedModuleDir, "iosApp").exists()).isFalse()
      val settingsContent = File(projectDir, "settings.gradle.kts").readText()
      assertThat(settingsContent).contains("""include("$nestedModuleGradlePath:shared")""")
      assertThat(settingsContent).contains("""include("$nestedModuleGradlePath:androidApp")""")
      assertThat(settingsContent).contains("""include("$nestedModuleGradlePath:desktopApp")""")
      assertThat(settingsContent).contains("""include("$nestedModuleGradlePath:webApp")""")
      assertThat(File(projectDir, "build.gradle.kts").readText()).isEqualTo(rootBuildBeforeAdd)
      assertThat(File(projectDir, "gradle/libs.versions.toml").readText())
          .isEqualTo(versionCatalogBeforeAdd)

      val sharedBuildFile = File(nestedModuleDir, "shared/build.gradle.kts").readText()
      val androidAppBuildFile = File(nestedModuleDir, "androidApp/build.gradle.kts").readText()
      val desktopMainFile =
          File(nestedModuleDir, "desktopApp/src/jvmMain/kotlin/$nestedPackagePath/main.kt")
              .readText()
      val webBuildFile = File(nestedModuleDir, "webApp/build.gradle.kts").readText()
      val webIndexFile =
          File(nestedModuleDir, "webApp/src/wasmJsMain/resources/index.html").readText()
      assertThat(sharedBuildFile).contains("alias(libs.plugins.kotlin.multiplatform)")
      assertThat(sharedBuildFile).contains("alias(libs.plugins.compose)")
      assertThat(sharedBuildFile).contains("alias(libs.plugins.compose.compiler)")
      assertThat(sharedBuildFile)
          .contains("alias(libs.plugins.android.kotlin.multiplatform.library)")
      assertThat(sharedBuildFile)
          .contains("compileSdk = libs.versions.android.compile.sdk.get().toInt()")
      assertThat(sharedBuildFile).contains("minSdk = libs.versions.android.min.sdk.get().toInt()")
      assertThat(sharedBuildFile).contains("implementation(libs.composables.ui)")
      assertThat(sharedBuildFile).contains("implementation(libs.composables.uri.painter)")
      assertThat(androidAppBuildFile)
          .contains("""implementation(project("$nestedModuleGradlePath:shared"))""")
      assertThat(desktopMainFile).contains("""singleWindowApplication(title = "$nestedAppName")""")
      assertThat(webBuildFile).contains("injectWasmPreloads")
      assertThat(webIndexFile).contains("<title>$nestedAppName</title>")

      if (isWindows) {
        val evaluationResult =
            runProcess(
                command = projectGradleCommand("$nestedModuleGradlePath:webApp:tasks", "--all"),
                workingDir = projectDir,
                timeoutSeconds = 180,
            )

        assertThat(evaluationResult.finished).isTrue()
        assertThat(evaluationResult.exitCode).isEqualTo(0)
        assertThat(evaluationResult.output).contains("wasmJsBrowserDistribution")
      } else {
        val compileResult =
            runProcess(
                command =
                    projectGradleCommand(
                        "$nestedModuleGradlePath:shared:compileKotlinJvm",
                        "$nestedModuleGradlePath:shared:compileKotlinWasmJs",
                        "$nestedModuleGradlePath:desktopApp:compileKotlinJvm",
                        "$nestedModuleGradlePath:webApp:compileKotlinWasmJs",
                    ),
                workingDir = projectDir,
                timeoutSeconds = 180,
            )

        assertThat(compileResult.finished).isTrue()
        assertThat(compileResult.exitCode).isEqualTo(0)
        assertThat(compileResult.output).contains("BUILD SUCCESSFUL")

        val distributionResult =
            runProcess(
                command =
                    projectGradleCommand(
                        "$nestedModuleGradlePath:webApp:wasmJsBrowserDistribution"),
                workingDir = projectDir,
                timeoutSeconds = 180,
            )

        assertThat(distributionResult.finished).isTrue()
        assertThat(distributionResult.exitCode).isEqualTo(0)
        val distributedIndexFile =
            File(
                    projectDir,
                    "$nestedModulePath/webApp/build/dist/wasmJs/productionExecutable/index.html",
                )
                .readText()
        assertThat(distributedIndexFile).contains("<!-- wasm-preloads:start -->")
        assertThat(distributedIndexFile).contains("""rel="preload"""")
      }
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli add module creates a flat library module that compiles`() {
    val rootDir = createTempRoot("composables-cli-add-library-module")
    try {
      val isWindows = System.getProperty("os.name").startsWith("Windows")
      val projectName = if (isWindows) "s" else "sample-app"
      val rootPackage = if (isWindows) "c.e.s" else "com.example.sampleapp"
      val rootAppName = if (isWindows) "S" else "Sample App"
      val libraryModulePath = if (isWindows) "l" else "screen-templates"
      val libraryPackage = if (isWindows) "c.e.l" else "com.example.templates"
      val libraryPackagePath = libraryPackage.replace(".", "/")
      val libraryModuleGradlePath = ":" + libraryModulePath.replace('/', ':')
      val projectDir = File(rootDir, projectName)
      val launcher = installedLauncher()

      val initResult =
          runProcess(
              command =
                  listOf(
                      launcher.absolutePath,
                      "init",
                      projectDir.absolutePath,
                      "--package",
                      rootPackage,
                      "--app-name",
                      rootAppName,
                      "--targets",
                      "android,jvm,wasm",
                  ),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(initResult.finished).isTrue()
      assertThat(initResult.exitCode).isEqualTo(0)

      rewriteProjectToUiStyleConventions(projectDir)
      val rootBuildBeforeAdd = File(projectDir, "build.gradle.kts").readText()
      val versionCatalogBeforeAdd = File(projectDir, "gradle/libs.versions.toml").readText()

      val addResult =
          runProcess(
              command =
                  listOf(
                      launcher.absolutePath,
                      "add",
                      "module",
                      libraryModulePath,
                      "--type",
                      "library",
                      "--package",
                      libraryPackage,
                      "--targets",
                      "jvm,wasm",
                  ),
              workingDir = projectDir,
              timeoutSeconds = 60,
          )

      assertThat(addResult.finished).isTrue()
      assertThat(addResult.exitCode).isEqualTo(0)
      assertThat(addResult.output).contains("Library Module Configuration")
      assertThat(addResult.output).doesNotContain("App Name")

      val libraryModuleDir = File(projectDir, libraryModulePath)
      assertThat(File(libraryModuleDir, "build.gradle.kts").exists()).isTrue()
      assertThat(
              File(libraryModuleDir, "src/commonMain/kotlin/$libraryPackagePath/Library.kt")
                  .exists())
          .isTrue()
      assertThat(File(libraryModuleDir, "shared").exists()).isFalse()
      assertThat(File(libraryModuleDir, "desktopApp").exists()).isFalse()
      assertThat(File(libraryModuleDir, "webApp").exists()).isFalse()

      val settingsContent = File(projectDir, "settings.gradle.kts").readText()
      assertThat(settingsContent).contains("""include("$libraryModuleGradlePath")""")
      assertThat(File(projectDir, "build.gradle.kts").readText()).isEqualTo(rootBuildBeforeAdd)
      assertThat(File(projectDir, "gradle/libs.versions.toml").readText())
          .isEqualTo(versionCatalogBeforeAdd)

      val libraryBuildFile = File(libraryModuleDir, "build.gradle.kts").readText()
      assertThat(libraryBuildFile).contains("alias(libs.plugins.kotlin.multiplatform)")
      assertThat(libraryBuildFile).contains("alias(libs.plugins.compose)")
      assertThat(libraryBuildFile).contains("alias(libs.plugins.compose.compiler)")
      assertThat(libraryBuildFile).contains("implementation(libs.composables.ui)")
      assertThat(libraryBuildFile).contains("implementation(libs.composables.uri.painter)")
      assertThat(libraryBuildFile).contains("jvm()")
      assertThat(libraryBuildFile).contains("wasmJs")
      assertThat(libraryBuildFile).doesNotContain("binaries.executable")

      if (isWindows) {
        val evaluationResult =
            runProcess(
                command = projectGradleCommand("$libraryModuleGradlePath:tasks", "--all"),
                workingDir = projectDir,
                timeoutSeconds = 180,
            )

        assertThat(evaluationResult.finished).isTrue()
        assertThat(evaluationResult.exitCode).isEqualTo(0)
        assertThat(evaluationResult.output).contains("compileKotlinJvm")
      } else {
        val compileResult =
            runProcess(
                command =
                    projectGradleCommand(
                        "$libraryModuleGradlePath:compileKotlinJvm",
                        "$libraryModuleGradlePath:compileKotlinWasmJs",
                    ),
                workingDir = projectDir,
                timeoutSeconds = 180,
            )

        assertThat(compileResult.finished).isTrue()
        assertThat(compileResult.exitCode).isEqualTo(0)
        assertThat(compileResult.output).contains("BUILD SUCCESSFUL")
      }
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `cli add module prompts for module type interactively`() {
    val rootDir = createTempRoot("composables-cli-add-module-interactive")
    try {
      val projectDir = File(rootDir, "sample-app")
      val launcher = installedLauncher()

      val initResult =
          runProcess(
              command =
                  listOf(
                      launcher.absolutePath,
                      "init",
                      projectDir.absolutePath,
                      "--package",
                      "com.example.sampleapp",
                      "--app-name",
                      "Sample App",
                      "--targets",
                      "jvm,wasm",
                  ),
              workingDir = rootDir,
              timeoutSeconds = 60,
          )

      assertThat(initResult.finished).isTrue()
      assertThat(initResult.exitCode).isEqualTo(0)

      val addResult =
          runProcess(
              command = listOf(launcher.absolutePath, "add", "module"),
              workingDir = projectDir,
              stdin =
                  """
                  screen-templates
                  2
                  com.example.templates
                  n
                  y
                  n
                  y
                  """
                      .trimIndent() + "\n",
              timeoutSeconds = 60,
          )

      assertThat(addResult.finished).isTrue()
      assertThat(addResult.exitCode).isEqualTo(0)
      assertThat(addResult.output).contains("What type of module do you want to add?")
      assertThat(addResult.output).contains("Library Module Configuration")
      assertThat(File(projectDir, "screen-templates/build.gradle.kts").exists()).isTrue()
      assertThat(File(projectDir, "screen-templates/shared").exists()).isFalse()
      assertThat(File(projectDir, "settings.gradle.kts").readText())
          .contains("""include(":screen-templates")""")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  @Test
  fun `rewriteProjectToUiStyleConventions removes type safe project accessors on CRLF settings files`() {
    val rootDir = createTempRoot("composables-cli-ui-style-crlf")
    try {
      val projectDir = File(rootDir, "sample-app").apply { mkdirs() }
      File(projectDir, "settings.gradle.kts")
          .writeText(
              "rootProject.name = \"sample-app\"\r\n\r\nenableFeaturePreview(\"TYPESAFE_PROJECT_ACCESSORS\")\r\n\r\ninclude(\":shared\")\r\n",
          )

      rewriteProjectToUiStyleConventions(projectDir)

      val rewritten = File(projectDir, "settings.gradle.kts").readText()
      assertThat(rewritten).doesNotContain("enableFeaturePreview(\"TYPESAFE_PROJECT_ACCESSORS\")")
    } finally {
      rootDir.deleteRecursively()
    }
  }

  private fun rewriteProjectToUiStyleConventions(projectDir: File) {
    val replacements =
        listOf(
            "libs.plugins.jetbrains.kotlin.multiplatform" to "libs.plugins.kotlin.multiplatform",
            "libs.plugins.jetbrains.compose.compiler" to "libs.plugins.compose.compiler",
            "libs.plugins.jetbrains.compose" to "libs.plugins.compose",
            "libs.versions.android.compileSdk.get().toInt()" to
                "libs.versions.android.compile.sdk.get().toInt()",
            "libs.versions.android.minSdk.get().toInt()" to
                "libs.versions.android.min.sdk.get().toInt()",
            "libs.versions.android.targetSdk.get().toInt()" to
                "libs.versions.android.target.sdk.get().toInt()",
            "implementation(projects.shared)" to """implementation(project(":shared"))""",
            "jetbrains-kotlin-multiplatform" to "kotlin-multiplatform",
            "jetbrains-compose-compiler" to "compose-compiler",
            "jetbrains-compose" to "compose",
            "android-compileSdk" to "android-compile-sdk",
            "android-minSdk" to "android-min-sdk",
            "android-targetSdk" to "android-target-sdk",
        )

    projectDir
        .walkTopDown()
        .filter { it.isFile && (it.extension == "kts" || it.name == "libs.versions.toml") }
        .forEach { file ->
          val updated =
              replacements.fold(file.readText()) { content, (from, to) ->
                content.replace(from, to)
              }
          val normalized =
              updated.replace(
                  Regex("""enableFeaturePreview\("TYPESAFE_PROJECT_ACCESSORS"\)\r?\n(?:\r?\n)?"""),
                  "",
              )
          file.writeText(normalized)
        }
  }

  private fun installedLauncher(): File {
    val scriptName =
        if (System.getProperty("os.name").startsWith("Windows")) "composables.bat"
        else "composables"
    val launcher = File("build/install/composables/bin/$scriptName")
    check(launcher.isFile) { "Expected installed launcher at ${launcher.absolutePath}" }
    return launcher
  }

  private fun shadowJarArtifact(): File {
    val jar = File("build/libs/composables.jar")
    check(jar.isFile) { "Expected shadow jar at ${jar.absolutePath}" }
    return jar
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

  private fun projectGradleScript(): String =
      if (System.getProperty("os.name").startsWith("Windows")) {
        "gradlew.bat"
      } else {
        "./gradlew"
      }

  private fun projectGradleCommand(vararg arguments: String): List<String> = buildList {
    add(projectGradleScript())
    add("--no-daemon")
    add("--console=plain")
    addAll(arguments)
  }

  private fun assertJvmReadme(projectDir: File) {
    val readme = File(projectDir, "README.md")
    assertThat(readme.exists()).isTrue()

    val content = readme.readText()
    assertThat(content).contains("# ${projectDir.name}")
    assertThat(content).contains("## Run")
    assertThat(content).contains("`./gradlew :desktopApp:hotRunJvm --auto`")
    assertThat(content).doesNotContain(":androidApp:installDebug")
    assertThat(content).doesNotContain("iosApp/iosApp.xcodeproj")
    assertThat(content).doesNotContain(":webApp:wasmJsBrowserDevelopmentRun")

    val hotRunHelp =
        runProcess(
            command = projectGradleCommand("help", "--task", ":desktopApp:hotRunJvm"),
            workingDir = projectDir,
            timeoutSeconds = 180,
        )

    assertThat(hotRunHelp.finished).isTrue()
    assertThat(hotRunHelp.exitCode).isEqualTo(0)
    assertThat(hotRunHelp.output).contains("Path")
    assertThat(hotRunHelp.output).contains(":desktopApp:hotRunJvm")
    assertThat(hotRunHelp.output).contains("--auto")
    assertThat(hotRunHelp.output)
        .contains("Enables automatic recompilation/reload once the source files change")
  }

  private fun assertAndroidReadme(projectDir: File) {
    val readme = File(projectDir, "README.md")
    assertThat(readme.exists()).isTrue()

    val content = readme.readText()
    assertThat(content).contains("# ${projectDir.name}")
    assertThat(content).contains("## Run")
    assertThat(content).contains("`./gradlew :androidApp:installDebug`")
    assertThat(content).doesNotContain(":desktopApp:hotRunJvm --auto")
    assertThat(content).doesNotContain("iosApp/iosApp.xcodeproj")
    assertThat(content).doesNotContain(":webApp:wasmJsBrowserDevelopmentRun")
  }

  private fun assertWasmReadme(projectDir: File) {
    val readme = File(projectDir, "README.md")
    assertThat(readme.exists()).isTrue()

    val content = readme.readText()
    assertThat(content).contains("# ${projectDir.name}")
    assertThat(content).contains("## Run")
    assertThat(content).contains("`./gradlew :webApp:wasmJsBrowserDevelopmentRun`")
    assertThat(content).doesNotContain(":androidApp:installDebug")
    assertThat(content).doesNotContain(":desktopApp:hotRunJvm --auto")
    assertThat(content).doesNotContain("iosApp/iosApp.xcodeproj")
  }

  private fun assertGeneratedProjectUsesKtfmt(projectDir: File) {
    val rootBuild = File(projectDir, "build.gradle.kts").readText()
    assertThat(rootBuild).contains("alias(libs.plugins.spotless)")
    assertThat(rootBuild).contains("ktfmt()")

    val readme = File(projectDir, "README.md").readText()
    assertThat(readme).contains("## Code Formatting")
    assertThat(readme).contains("This project uses ktfmt, provided via the Spotless gradle plugin.")
    assertThat(readme).contains("./gradlew spotlessCheck")
    assertThat(readme).contains("./gradlew spotlessApply")

    val sharedApp =
        File(projectDir, "shared/src/commonMain/kotlin/com/example/sampleapp/App.kt")
            .readText()
            .replace("\r\n", "\n")
    assertThat(sharedApp).contains("val backStack = remember { NavBackStack<NavKey>(HomeRoute) }")
    assertThat(sharedApp).contains("entry<HomeRoute> { HomeScreen")
    assertThat(sharedApp).contains("entry<DetailsRoute> { DetailsScreen")
    assertThat(sharedApp).contains("modifier = Modifier.fillMaxSize(),")
    assertThat(sharedApp).contains("data object HomeRoute : NavKey")
    assertThat(sharedApp).contains("data object DetailsRoute : NavKey")
    assertThat(sharedApp).doesNotContain("safeDrawingPadding")
    assertThat(sharedApp).doesNotContain("fun HomeScreen")
    assertThat(sharedApp).doesNotContain("fun DetailsScreen")

    val homeScreen =
        File(projectDir, "shared/src/commonMain/kotlin/com/example/sampleapp/HomeScreen.kt")
            .readText()
    assertThat(homeScreen).contains("fun HomeScreen(onDetailsClick: () -> Unit)")
    assertThat(homeScreen).doesNotContain("private fun HomeScreen")

    val detailsScreen =
        File(projectDir, "shared/src/commonMain/kotlin/com/example/sampleapp/DetailsScreen.kt")
            .readText()
    assertThat(detailsScreen).contains("fun DetailsScreen(onBackClick: () -> Unit)")
    assertThat(detailsScreen).doesNotContain("private fun DetailsScreen")

    val sharedBuild = File(projectDir, "shared/build.gradle.kts").readText()
    assertThat(sharedBuild).contains("implementation(libs.androidx.navigation3.ui)")

    val desktopMain =
        File(projectDir, "desktopApp/src/jvmMain/kotlin/com/example/sampleapp/main.kt").readText()
    assertThat(desktopMain)
        .contains("fun main() = singleWindowApplication(title = \"Sample App\") { App() }")
  }

  private fun assertGeneratedProjectHasGitignore(projectDir: File) {
    val gitignore = File(projectDir, ".gitignore")
    assertThat(gitignore.exists()).isTrue()

    val content = gitignore.readText()
    assertThat(content).contains(".idea")
    assertThat(content).contains(".gradle")
    assertThat(content).contains("**/build/")
  }

  private fun runProcess(
      command: List<String>,
      workingDir: File,
      stdin: String = "",
      extraEnvironment: Map<String, String> = emptyMap(),
      timeoutSeconds: Long,
  ): ProcessResult {
    val process =
        ProcessBuilder(platformCommand(command))
            .directory(workingDir)
            .redirectErrorStream(true)
            .apply { environment().putAll(extraEnvironment) }
            .start()

    val output = StringBuilder()
    val readerThread =
        Thread {
              process.inputStream.bufferedReader().useLines { lines ->
                lines.forEach { line -> output.appendLine(line) }
              }
            }
            .apply { start() }

    process.outputStream.bufferedWriter().use { writer ->
      if (stdin.isNotEmpty()) {
        writer.write(stdin)
        writer.flush()
      }
    }

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

  private fun runProcessUntilOutputContains(
      command: List<String>,
      workingDir: File,
      stdin: String,
      expectedOutput: String,
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

    process.outputStream.bufferedWriter().use { writer ->
      writer.write(stdin)
      writer.flush()
    }

    val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(timeoutSeconds)
    var matched = false
    while (System.nanoTime() < deadline) {
      if (output.contains(expectedOutput)) {
        matched = true
        break
      }
      if (!process.isAlive) {
        break
      }
      Thread.sleep(25)
    }

    if (process.isAlive) {
      process.destroyForcibly()
      process.waitFor(5, TimeUnit.SECONDS)
    }
    readerThread.join()

    return ProcessResult(
        finished = matched,
        exitCode = if (!process.isAlive) process.exitValue() else -1,
        output = output.toString(),
    )
  }

  private fun mcpFrame(json: String): String = "$json\n"

  private data class ProcessResult(
      val finished: Boolean,
      val exitCode: Int,
      val output: String,
  )
}
