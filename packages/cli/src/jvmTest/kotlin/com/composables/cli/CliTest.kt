package com.composables.cli

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import assertk.assertions.exists
import assertk.assertions.isDirectory
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.isTrue
import com.github.ajalt.clikt.core.UsageError
import java.io.File
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertFailsWith

class CliTest {

  @Test
  fun `cloneGradleProject renders jvm template and replaces placeholders`() {
    withTempDir { targetDir ->
      cloneGradleProject(
          targetDir = targetDir.absolutePath,
          dirName = "newApp",
          packageName = "com.composables.demo",
          moduleName = "shared",
          appName = "The App",
          targets = setOf(JVM),
      )

      val projectDir = File(targetDir, "newApp")
      val sharedBuildFile = File(projectDir, "shared/build.gradle.kts")
      val desktopBuildFile = File(projectDir, "desktopApp/build.gradle.kts")
      val rootBuildFile = File(projectDir, "build.gradle.kts")
      val readmeFile = File(projectDir, "README.md")
      val gitignoreFile = File(projectDir, ".gitignore")
      val settingsFile = File(projectDir, "settings.gradle.kts")
      val appFile = File(projectDir, "shared/src/commonMain/kotlin/com/composables/demo/App.kt")
      val desktopMainFile =
          File(projectDir, "desktopApp/src/jvmMain/kotlin/com/composables/demo/main.kt")

      assertThat(projectDir.isDirectory, "Generated project directory should exist").isTrue()
      assertThat(sharedBuildFile.exists(), "Generated shared module build file should exist")
          .isTrue()
      assertThat(desktopBuildFile.exists(), "Generated desktop module build file should exist")
          .isTrue()
      assertThat(readmeFile.exists(), "Generated README should exist").isTrue()
      assertThat(gitignoreFile.exists(), "Generated .gitignore should exist").isTrue()
      assertThat(settingsFile.exists(), "Generated settings file should exist").isTrue()
      assertThat(appFile.exists(), "App source should be moved to the requested package").isTrue()
      assertThat(desktopMainFile.exists(), "Desktop launcher source should exist").isTrue()

      assertThat(
              File(projectDir, "iosApp").exists(),
              "iOS app scaffold should be skipped for JVM-only template runs")
          .isFalse()
      assertThat(
              File(projectDir, "androidApp").exists(),
              "Android app scaffold should be skipped for JVM-only template runs")
          .isFalse()
      assertThat(
              File(projectDir, "webApp").exists(),
              "Web app scaffold should be skipped for JVM-only template runs")
          .isFalse()
      assertThat(
              File(projectDir, "shared/src/iosMain").exists(),
              "iOS sources should be omitted for JVM-only template runs")
          .isFalse()

      val sharedBuildContent = sharedBuildFile.readText()
      val desktopBuildContent = desktopBuildFile.readText()
      val desktopMainContent = desktopMainFile.readText()
      val rootBuildContent = rootBuildFile.readText()
      val readmeContent = readmeFile.readText()
      val gitignoreContent = gitignoreFile.readText()
      val settingsContent = settingsFile.readText()
      val appContent = appFile.readText()

      assertThat(sharedBuildContent).contains("jvm()")
      assertThat(sharedBuildContent).contains("implementation(libs.composables.ui)")
      assertThat(sharedBuildContent).contains("implementation(libs.composables.uri.painter)")
      assertThat(sharedBuildContent).contains("implementation(libs.compose.ui.tooling.preview)")
      assertThat(sharedBuildContent).doesNotContain("androidLibrary {")
      assertThat(sharedBuildContent).doesNotContain("android {")
      assertThat(sharedBuildContent).doesNotContain("iosArm64()")
      assertThat(sharedBuildContent).doesNotContain("wasmJs()")
      assertThat(sharedBuildContent).doesNotContain("{{shared_module_name}}")
      assertThat(desktopBuildContent).contains("implementation(projects.shared)")
      assertThat(desktopBuildContent).contains("mainClass = \"com.composables.demo.MainKt\"")
      assertThat(desktopMainContent).contains("""singleWindowApplication(title = "The App")""")

      assertThat(rootBuildContent).doesNotContain("composeCompatibilityBrowserDistribution")
      assertThat(rootBuildContent).doesNotContain("jsBrowserDistribution")
      assertThat(rootBuildContent).doesNotContain("wasmJsBrowserDistribution")
      assertThat(rootBuildContent).doesNotContain("js-preloads")
      assertThat(rootBuildContent).doesNotContain("wasm-preloads")
      assertThat(settingsContent).contains("""rootProject.name = "newApp"""")
      assertThat(settingsContent).contains("""include(":shared")""")
      assertThat(settingsContent).contains("""include(":desktopApp")""")
      assertThat(readmeContent).contains("# newApp")
      assertThat(readmeContent).contains("`./gradlew :desktopApp:hotRunJvm --auto`")
      assertThat(readmeContent).doesNotContain(":androidApp:installDebug")
      assertThat(readmeContent).doesNotContain("iosApp/iosApp.xcodeproj")
      assertThat(readmeContent).doesNotContain(":webApp:wasmJsBrowserDevelopmentRun")
      assertThat(gitignoreContent).contains(".idea")
      assertThat(gitignoreContent).contains(".gradle")
      assertThat(gitignoreContent).contains("**/build/")

      assertThat(appContent).contains("package com.composables.demo")
      assertThat(appContent).contains("import androidx.compose.ui.tooling.preview.Preview")
      assertThat(appContent).contains("Hello Beautiful World!")
      assertThat(appContent).contains("Go to App.kt to edit your app")
      assertThat(appContent)
          .contains(
              "Pro tip: Use the `dev` configuration in your IDE to auto-reload your app when you edit your code")
      assertThat(appContent).doesNotContain("{{app_name}}")
      assertThat(appContent).doesNotContain("{{namespace}}")
    }
  }

  @Test
  fun `cloneGradleProject renders Android as a separate app module`() {
    withTempDir { targetDir ->
      cloneGradleProject(
          targetDir = targetDir.absolutePath,
          dirName = "newApp",
          packageName = "com.composables.demo",
          moduleName = "sharedUi",
          appName = "The App",
          targets = setOf(ANDROID, JVM, IOS, WASM),
      )

      val projectDir = File(targetDir, "newApp")
      val sharedBuildFile = File(projectDir, "sharedUi/build.gradle.kts")
      val androidAppBuildFile = File(projectDir, "androidApp/build.gradle.kts")
      val readmeFile = File(projectDir, "README.md")
      val settingsFile = File(projectDir, "settings.gradle.kts")
      val mainActivityFile =
          File(projectDir, "androidApp/src/main/kotlin/com/composables/demo/MainActivity.kt")

      assertThat(sharedBuildFile).exists()
      assertThat(androidAppBuildFile).exists()
      assertThat(readmeFile).exists()
      assertThat(mainActivityFile).exists()
      assertThat(File(projectDir, "sharedUi/src/androidMain").exists()).isFalse()

      val sharedBuildContent = sharedBuildFile.readText()
      val androidAppBuildContent = androidAppBuildFile.readText()
      val desktopMainContent =
          File(projectDir, "desktopApp/src/jvmMain/kotlin/com/composables/demo/main.kt").readText()
      val readmeContent = readmeFile.readText()
      val settingsContent = settingsFile.readText()

      assertThat(sharedBuildContent)
          .contains("alias(libs.plugins.android.kotlin.multiplatform.library)")
      assertThat(sharedBuildContent).contains("android {")
      assertThat(sharedBuildContent).doesNotContain("androidLibrary {")
      assertThat(sharedBuildContent).contains("""namespace = "com.composables.demo.sharedUi"""")
      assertThat(sharedBuildContent).contains("androidRuntimeClasspath(libs.compose.ui.tooling)")
      assertThat(sharedBuildContent).contains("implementation(libs.composables.uri.painter)")
      assertThat(sharedBuildContent).doesNotContain("alias(libs.plugins.android.application)")
      assertThat(sharedBuildContent).doesNotContain("androidMain.dependencies")
      assertThat(sharedBuildContent).doesNotContain("defaultConfig {")

      assertThat(androidAppBuildContent).contains("alias(libs.plugins.android.application)")
      assertThat(androidAppBuildContent).contains("buildFeatures {")
      assertThat(androidAppBuildContent).contains("implementation(projects.sharedUi)")
      assertThat(desktopMainContent).contains("""singleWindowApplication(title = "The App")""")
      assertThat(settingsContent).contains("""include(":androidApp")""")
      assertThat(settingsContent).contains("""include(":desktopApp")""")
      assertThat(settingsContent).contains("""include(":webApp")""")

      assertThat(readmeContent).contains("# newApp")
      assertThat(readmeContent).contains("`./gradlew :desktopApp:hotRunJvm --auto`")
      assertThat(readmeContent).contains("`./gradlew :androidApp:installDebug`")
      assertThat(readmeContent).contains("`iosApp/iosApp.xcodeproj`")
      assertThat(readmeContent).contains("`./gradlew :webApp:wasmJsBrowserDevelopmentRun`")
      assertThat(readmeContent.indexOf("- Android:") > readmeContent.indexOf("- JVM:")).isTrue()
      assertThat(
              readmeContent.indexOf("- iOS:") >
                  readmeContent.indexOf("- Android install from terminal:"))
          .isTrue()
      assertThat(readmeContent.indexOf("- Wasm:") > readmeContent.indexOf("- iOS:")).isTrue()
    }
  }

  @Test
  fun `parseTargets normalizes and de-duplicates targets`() {
    val targets = parseTargets("JVM, android, jvm, ios")

    assertThat(targets).isEqualTo(linkedSetOf(JVM, ANDROID, IOS))
  }

  @Test
  fun `parseTargets rejects unknown targets`() {
    val error = assertFailsWith<IllegalArgumentException> { parseTargets("android,desktop") }

    assertThat(error.message ?: "").contains("Unknown targets: desktop")
  }

  @Test
  fun `cloneGradleProject renders wasm preload wiring only when wasm target is selected`() {
    withTempDir { targetDir ->
      cloneGradleProject(
          targetDir = targetDir.absolutePath,
          dirName = "newApp",
          packageName = "com.composables.demo",
          moduleName = "shared",
          appName = "The App",
          targets = setOf(WASM),
      )

      val projectDir = File(targetDir, "newApp")
      val rootBuildContent = File(projectDir, "build.gradle.kts").readText()
      val sharedBuildContent = File(projectDir, "shared/build.gradle.kts").readText()
      val webAppBuildContent = File(projectDir, "webApp/build.gradle.kts").readText()
      val readmeContent = File(projectDir, "README.md").readText()

      assertThat(rootBuildContent).contains("wasmJsBrowserDistribution")
      assertThat(rootBuildContent).contains("wasm-preloads")
      assertThat(rootBuildContent).doesNotContain("composeCompatibilityBrowserDistribution")
      assertThat(rootBuildContent).doesNotContain("jsBrowserDistribution")
      assertThat(rootBuildContent).doesNotContain("js-preloads")
      assertThat(sharedBuildContent).contains("wasmJs {")
      assertThat(sharedBuildContent).contains("browser()")
      assertThat(sharedBuildContent).doesNotContain("js {")
      assertThat(webAppBuildContent).contains("implementation(libs.compose.ui)")
      assertThat(webAppBuildContent).contains("wasmJs {")
      assertThat(readmeContent).contains("# newApp")
      assertThat(readmeContent).contains("`./gradlew :webApp:wasmJsBrowserDevelopmentRun`")
      assertThat(readmeContent).doesNotContain(":desktopApp:hotRunJvm --auto")
    }
  }

  @Test
  fun `buildProjectStartCommand prefers first runnable generated instruction`() {
    assertThat(
            buildProjectStartCommand(
                targets = setOf(JVM, ANDROID, WASM), gradleCommand = "./gradlew"))
        .isEqualTo("./gradlew :desktopApp:hotRunJvm --auto")
    assertThat(buildProjectStartCommand(targets = setOf(WASM), gradleCommand = "./gradlew"))
        .isEqualTo("./gradlew :webApp:wasmJsBrowserDevelopmentRun")
    assertThat(buildProjectStartCommand(targets = setOf(ANDROID), gradleCommand = "./gradlew"))
        .isEqualTo("./gradlew :androidApp:installDebug")
    assertThat(buildProjectStartCommand(targets = setOf(IOS), gradleCommand = "./gradlew"))
        .isEqualTo("./gradlew build")
  }

  @Test
  fun `updateRootBuildFile adds missing plugins once`() {
    withTempDir { targetDir ->
      File(targetDir, "build.gradle.kts")
          .writeText(
              """
                plugins {
                    alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false
                }
                """
                  .trimIndent(),
          )

      updateRootBuildFile(targetDir.absolutePath, setOf(ANDROID))
      updateRootBuildFile(targetDir.absolutePath, setOf(ANDROID))

      val content = File(targetDir, "build.gradle.kts").readText()

      assertThat(
              content.countOccurrences(
                  """alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false"""))
          .isEqualTo(1)
      assertThat(content.countOccurrences("""alias(libs.plugins.jetbrains.compose) apply false"""))
          .isEqualTo(1)
      assertThat(
              content.countOccurrences(
                  """alias(libs.plugins.jetbrains.compose.compiler) apply false"""))
          .isEqualTo(1)
      assertThat(content).doesNotContain("alias(libs.plugins.jetbrains.compose.hotreload)")
      assertThat(
              content.countOccurrences("""alias(libs.plugins.android.application) apply false"""))
          .isEqualTo(1)
      assertThat(
              content.countOccurrences(
                  """alias(libs.plugins.android.kotlin.multiplatform.library) apply false"""))
          .isEqualTo(1)
    }
  }

  @Test
  fun `updateVersionCatalog adds android entries once`() {
    withTempDir { targetDir ->
      File(targetDir, "gradle").mkdirs()
      File(targetDir, "gradle/libs.versions.toml")
          .writeText(
              """
                [versions]
                kotlin = "2.2.21"

                [libraries]

                [plugins]
                jetbrains-kotlin-multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
                """
                  .trimIndent() + "\n",
          )

      updateVersionCatalog(targetDir.absolutePath, setOf(ANDROID))
      updateVersionCatalog(targetDir.absolutePath, setOf(ANDROID))

      val content = File(targetDir, "gradle/libs.versions.toml").readText()

      assertThat(content.countOccurrences("""agp = "9.2.1"""")).isEqualTo(1)
      assertThat(content.countOccurrences("""iconsLucide = "1.0.0"""")).isEqualTo(1)
      assertThat(
              content.countOccurrences(
                  """androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }"""))
          .isEqualTo(1)
      assertThat(
              content.countOccurrences(
                  """composables-ui = { group = "com.composables", name = "ui", version.ref = "composablesUi" }"""))
          .isEqualTo(1)
      assertThat(
              content.countOccurrences(
                  """composables-icons-lucide = { group = "com.composables", name = "icons-lucide", version.ref = "iconsLucide" }"""))
          .isEqualTo(1)
      assertThat(
              content.countOccurrences(
                  """compose-ui = { group = "org.jetbrains.compose.ui", name = "ui", version.ref = "compose" }"""))
          .isEqualTo(1)
      assertThat(
              content.countOccurrences(
                  """android-application = { id = "com.android.application", version.ref = "agp" }"""))
          .isEqualTo(1)
      assertThat(
              content.countOccurrences(
                  """android-kotlin-multiplatform-library = { id = "com.android.kotlin.multiplatform.library", version.ref = "agp" }"""))
          .isEqualTo(1)
      assertThat(content).contains("""compose = "1.11.1"""")
      assertThat(content).doesNotContain("composeHotReload")
      assertThat(content).contains("""composablesUi = "0.1.0"""")
    }
  }

  @Test
  fun `gradleScript uses batch file on windows`() {
    withOsName("Windows 11") { assertThat(gradleScript).isEqualTo("gradlew.bat") }
  }

  @Test
  fun `gradleScript uses shell script on unix`() {
    withOsName("Linux") { assertThat(gradleScript).isEqualTo("./gradlew") }
  }

  @Test
  fun `docsApiUrl uses the public Composables docs endpoint by default`() {
    withDocsBaseUrl(null) {
      assertThat(docsApiUrl("list"))
          .isEqualTo("https://composables.com/api/composables-ui-docs/list")
    }
  }

  @Test
  fun `docsApiUrl respects a custom base url and encodes query parameters`() {
    withDocsBaseUrl("http://127.0.0.1:8080/") {
      assertThat(
              docsApiUrl(
                  endpoint = "search",
                  queryParameters = mapOf("q" to "dropdown menu"),
              ),
          )
          .isEqualTo("http://127.0.0.1:8080/api/composables-ui-docs/search?q=dropdown+menu")
    }
  }

  @Test
  fun `docsMarkdownUrl points at the resolved ui docs markdown page`() {
    withDocsBaseUrl("http://127.0.0.1:8080/") {
      assertThat(docsMarkdownUrl("buttons")).isEqualTo("http://127.0.0.1:8080/ui/docs/buttons.md")
    }
  }

  @Test
  fun `renderDocsLinks prints slug and absolute docs link`() {
    withDocsBaseUrl("http://127.0.0.1:8080/") {
      val output =
          renderDocsLinks(
              """
                {
                  "items": [
                    {
                      "slug": "bottom-sheet",
                      "title": "Bottom Sheet",
                      "url": "/ui/docs/bottom-sheet"
                    },
                    {
                      "slug": "buttons",
                      "title": "Buttons",
                      "url": "/ui/docs/buttons"
                    }
                  ]
                }
              """
                  .trimIndent(),
          )

      assertThat(output)
          .isEqualTo(
              """
                bottom-sheet  http://127.0.0.1:8080/ui/docs/bottom-sheet
                buttons       http://127.0.0.1:8080/ui/docs/buttons
              """
                  .trimIndent(),
          )
    }
  }

  @Test
  fun `renderDocsLinks preserves absolute docs links`() {
    val output =
        renderDocsLinks(
            """
              {
                "items": [
                  {
                    "slug": "cli",
                    "url": "https://composables.com/ui/docs/cli"
                  }
                ]
              }
            """
                .trimIndent(),
        )

    assertThat(output).isEqualTo("cli  https://composables.com/ui/docs/cli")
  }

  @Test
  fun `renderDocsLinks skips entries without a slug`() {
    withDocsBaseUrl("http://127.0.0.1:8080/") {
      val output =
          renderDocsLinks(
              """
                {
                  "items": [
                    {
                      "title": "Missing slug",
                      "url": "/ui/docs/missing"
                    },
                    {
                      "slug": "buttons"
                    }
                  ]
                }
              """
                  .trimIndent(),
          )

      assertThat(output).isEqualTo("buttons  http://127.0.0.1:8080/ui/docs/buttons")
    }
  }

  @Test
  fun `mcpUrl points at the hosted mcp endpoint`() {
    withDocsBaseUrl("http://127.0.0.1:8080/") {
      assertThat(mcpUrl()).isEqualTo("http://127.0.0.1:8080/mcp")
    }
  }

  @Test
  fun `installAndroidStudioMcpServer creates mcp config when missing`() {
    withTempDir { targetDir ->
      val result =
          installAndroidStudioMcpServer(
              configDir = targetDir,
              serverUrl = "https://composables.com/mcp",
          )
      val configFile = result.configFile

      assertThat(configFile).exists()
      assertThat(configFile.readText()).contains(""""composables"""")
      assertThat(configFile.readText()).contains(""""httpUrl": "https://composables.com/mcp"""")
      assertThat(result is AndroidStudioMcpInstallResult.Installed).isTrue()
    }
  }

  @Test
  fun `installAndroidStudioMcpServer preserves existing servers`() {
    withTempDir { targetDir ->
      val configFile =
          File(targetDir, "mcp.json").apply {
            writeText(
                """
                    {
                      "mcpServers": {
                        "figma": {
                          "httpUrl": "https://mcp.figma.com/mcp"
                        }
                      }
                    }
                    """
                    .trimIndent(),
            )
          }

      val result =
          installAndroidStudioMcpServer(
              configDir = targetDir,
              serverUrl = "https://composables.com/mcp",
          )

      val content = configFile.readText()
      assertThat(content).contains(""""figma"""")
      assertThat(content).contains(""""https://mcp.figma.com/mcp"""")
      assertThat(content).contains(""""composables"""")
      assertThat(content).contains(""""https://composables.com/mcp"""")
      assertThat(result is AndroidStudioMcpInstallResult.Installed).isTrue()
    }
  }

  @Test
  fun `installAndroidStudioMcpServer returns already installed when same url exists`() {
    withTempDir { targetDir ->
      val configFile =
          File(targetDir, "mcp.json").apply {
            writeText(
                """
                    {
                      "mcpServers": {
                        "composables": {
                          "httpUrl": "https://composables.com/mcp"
                        }
                      }
                    }
                    """
                    .trimIndent(),
            )
          }

      val result =
          installAndroidStudioMcpServer(
              configDir = targetDir,
              serverUrl = "https://composables.com/mcp",
          )

      assertThat(result is AndroidStudioMcpInstallResult.AlreadyInstalled).isTrue()
      assertThat(configFile.readText()).contains(""""https://composables.com/mcp"""")
    }
  }

  @Test
  fun `installAndroidStudioMcpServer rejects conflicting config without overwrite`() {
    withTempDir { targetDir ->
      File(targetDir, "mcp.json")
          .writeText(
              """
                {
                  "mcpServers": {
                    "composables": {
                      "httpUrl": "https://example.com/mcp"
                    }
                  }
                }
                """
                  .trimIndent(),
          )

      val error =
          assertFailsWith<UsageError> {
            installAndroidStudioMcpServer(
                configDir = targetDir,
                serverUrl = "https://composables.com/mcp",
            )
          }

      assertThat(error.message ?: "").contains("Use --overwrite to replace it.")
    }
  }

  @Test
  fun `installAndroidStudioMcpServer overwrites conflicting config when requested`() {
    withTempDir { targetDir ->
      val configFile =
          File(targetDir, "mcp.json").apply {
            writeText(
                """
                    {
                      "mcpServers": {
                        "composables": {
                          "httpUrl": "https://example.com/mcp"
                        },
                        "figma": {
                          "httpUrl": "https://mcp.figma.com/mcp"
                        }
                      }
                    }
                    """
                    .trimIndent(),
            )
          }

      val result =
          installAndroidStudioMcpServer(
              configDir = targetDir,
              serverUrl = "https://composables.com/mcp",
              overwrite = true,
          )

      val content = configFile.readText()
      assertThat(result is AndroidStudioMcpInstallResult.Installed).isTrue()
      assertThat(content).contains(""""https://composables.com/mcp"""")
      assertThat(content).contains(""""figma"""")
    }
  }

  @Test
  fun `installAndroidStudioMcpServer fails cleanly when mcpServers is malformed`() {
    withTempDir { targetDir ->
      File(targetDir, "mcp.json")
          .writeText(
              """
                {
                  "mcpServers": []
                }
                """
                  .trimIndent(),
          )

      val error =
          assertFailsWith<UsageError> {
            installAndroidStudioMcpServer(
                configDir = targetDir,
                serverUrl = "https://composables.com/mcp",
            )
          }

      assertThat(error.message ?: "").contains("Expected 'mcpServers'")
    }
  }

  @Test
  fun `installAntigravityMcpServer writes workspace mcp config`() {
    withTempDir { projectRoot ->
      val result = installAntigravityMcpServer(projectRoot = projectRoot)
      val configFile = File(projectRoot, ".agents/mcp_config.json")

      val content = configFile.readText()
      assertThat(result is McpInstallResult.Installed).isTrue()
      assertThat(content).contains(""""mcpServers"""")
      assertThat(content).contains(""""composables"""")
      assertThat(content).contains(""""command": "composables"""")
      assertThat(content).contains(""""mcp"""")
      assertThat(content).contains(""""start"""")
    }
  }

  @Test
  fun `installClaudeCodeMcpServer writes project mcp config`() {
    withTempDir { projectRoot ->
      val result = installClaudeCodeMcpServer(projectRoot = projectRoot)
      val configFile = File(projectRoot, ".mcp.json")

      assertThat(result is McpInstallResult.Installed).isTrue()
      assertThat(configFile).exists()
      assertThat(configFile.readText()).contains(""""command": "composables"""")
    }
  }

  @Test
  fun `installCursorMcpServer writes cursor mcp config`() {
    withTempDir { projectRoot ->
      val result = installCursorMcpServer(projectRoot = projectRoot)
      val configFile = File(projectRoot, ".cursor/mcp.json")

      assertThat(result is McpInstallResult.Installed).isTrue()
      assertThat(configFile).exists()
      assertThat(configFile.readText()).contains(""""command": "composables"""")
    }
  }

  @Test
  fun `installOpenCodeMcpServer writes opencode config`() {
    withTempDir { projectRoot ->
      val result = installOpenCodeMcpServer(projectRoot = projectRoot)
      val configFile = File(projectRoot, "opencode.json")
      val content = configFile.readText()

      assertThat(result is McpInstallResult.Installed).isTrue()
      assertThat(content).contains(""""${'$'}schema": "https://opencode.ai/config.json"""")
      assertThat(content).contains(""""mcp"""")
      assertThat(content).contains(""""type": "local"""")
      assertThat(content).contains(""""command": [""")
      assertThat(content).contains(""""enabled": true""")
    }
  }

  @Test
  fun `installFirebenderMcpServer writes project firebender config`() {
    withTempDir { projectRoot ->
      val result = installFirebenderMcpServer(projectRoot = projectRoot)
      val configFile = File(projectRoot, "firebender.json")
      val content = configFile.readText()

      assertThat(result is McpInstallResult.Installed).isTrue()
      assertThat(content).contains(""""mcpServers"""")
      assertThat(content).contains(""""composables"""")
      assertThat(content).contains(""""command": "composables"""")
      assertThat(content).contains(""""mcp"""")
      assertThat(content).contains(""""start"""")
    }
  }

  @Test
  fun `installZedMcpServer writes project zed context server config`() {
    withTempDir { projectRoot ->
      val result = installZedMcpServer(projectRoot = projectRoot)
      val configFile = File(projectRoot, ".zed/settings.json")
      val content = configFile.readText()

      assertThat(result is McpInstallResult.Installed).isTrue()
      assertThat(content).contains(""""context_servers"""")
      assertThat(content).contains(""""composables"""")
      assertThat(content).contains(""""command": "composables"""")
      assertThat(content).contains(""""args": [""")
    }
  }

  @Test
  fun `json mcp installers preserve existing servers and return already installed`() {
    withTempDir { projectRoot ->
      val configFile =
          File(projectRoot, ".cursor/mcp.json").apply {
            parentFile.mkdirs()
            writeText(
                """
                  {
                    "mcpServers": {
                      "figma": {
                        "url": "https://mcp.figma.com/mcp"
                      }
                    }
                  }
                """
                    .trimIndent(),
            )
          }

      val firstResult = installCursorMcpServer(projectRoot = projectRoot)
      val secondResult = installCursorMcpServer(projectRoot = projectRoot)
      val content = configFile.readText()

      assertThat(firstResult is McpInstallResult.Installed).isTrue()
      assertThat(secondResult is McpInstallResult.AlreadyInstalled).isTrue()
      assertThat(content).contains(""""figma"""")
      assertThat(content).contains(""""composables"""")
    }
  }

  @Test
  fun `installCodexMcpServer writes project toml config`() {
    withTempDir { projectRoot ->
      val result = installCodexMcpServer(projectRoot = projectRoot)
      val configFile = File(projectRoot, ".codex/config.toml")
      val content = configFile.readText()

      assertThat(result is McpInstallResult.Installed).isTrue()
      assertThat(content).contains("[mcp_servers.composables]")
      assertThat(content).contains("""command = "composables"""")
      assertThat(content).contains("""args = ["mcp", "start"]""")
    }
  }

  @Test
  fun `installCodexMcpServer preserves existing config and returns already installed`() {
    withTempDir { projectRoot ->
      val configFile =
          File(projectRoot, ".codex/config.toml").apply {
            parentFile.mkdirs()
            writeText(
                """
                  model = "gpt-5.1-codex"

                  [mcp_servers.other]
                  command = "other"
                """
                    .trimIndent() + "\n",
            )
          }

      val firstResult = installCodexMcpServer(projectRoot = projectRoot)
      val secondResult = installCodexMcpServer(projectRoot = projectRoot)
      val content = configFile.readText()

      assertThat(firstResult is McpInstallResult.Installed).isTrue()
      assertThat(secondResult is McpInstallResult.AlreadyInstalled).isTrue()
      assertThat(content).contains("""model = "gpt-5.1-codex"""")
      assertThat(content).contains("[mcp_servers.other]")
      assertThat(content).contains("[mcp_servers.composables]")
    }
  }

  @Test
  fun `findGradleProjectRoot walks up to settings file`() {
    withTempDir { projectRoot ->
      File(projectRoot, "settings.gradle.kts").writeText("""pluginManagement {}""")
      val nested = File(projectRoot, "feature/chat/src").apply { mkdirs() }

      assertThat(findGradleProjectRoot(nested)).isEqualTo(projectRoot.absoluteFile)
    }
  }

  @Test
  fun `findGradleProjectRoot returns null outside a Gradle project`() {
    withTempDir { targetDir -> assertThat(findGradleProjectRoot(targetDir)).isNull() }
  }

  @Test
  fun `findAndroidStudioConfigDirectory selects latest mac config directory`() {
    withTempDir { homeDir ->
      File(homeDir, "Library/Application Support/Google/AndroidStudio2025.1").mkdirs()
      val latest = File(homeDir, "Library/Application Support/Google/AndroidStudio2025.2")
      latest.mkdirs()

      withOsName("Mac OS X") {
        withUserHome(homeDir.absolutePath) {
          assertThat(findAndroidStudioConfigDirectory()).isNotNull().isEqualTo(latest)
        }
      }
    }
  }

  @Test
  fun `findAndroidStudioConfigDirectory returns null when absent`() {
    withTempDir { homeDir ->
      withOsName("Mac OS X") {
        withUserHome(homeDir.absolutePath) {
          assertThat(findAndroidStudioConfigDirectory()).isNull()
        }
      }
    }
  }

  private fun withTempDir(block: (File) -> Unit) {
    val dir = Files.createTempDirectory("composables-cli-test").toFile()
    try {
      block(dir)
    } finally {
      dir.deleteRecursively()
    }
  }

  private fun withOsName(value: String, block: () -> Unit) {
    val original = System.getProperty("os.name")
    try {
      System.setProperty("os.name", value)
      block()
    } finally {
      System.setProperty("os.name", original)
    }
  }

  private fun withDocsBaseUrl(value: String?, block: () -> Unit) {
    val original = System.getProperty("composables.docs.baseUrl")
    try {
      if (value == null) {
        System.clearProperty("composables.docs.baseUrl")
      } else {
        System.setProperty("composables.docs.baseUrl", value)
      }
      block()
    } finally {
      if (original == null) {
        System.clearProperty("composables.docs.baseUrl")
      } else {
        System.setProperty("composables.docs.baseUrl", original)
      }
    }
  }

  private fun withUserHome(value: String, block: () -> Unit) {
    val original = System.getProperty("user.home")
    try {
      System.setProperty("user.home", value)
      block()
    } finally {
      System.setProperty("user.home", original)
    }
  }

  private fun String.countOccurrences(value: String): Int = split(value).size - 1
}
