package com.composables.devtools.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.compose.reload.gradle.ComposeHotRun
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import javax.inject.Inject

abstract class ComposablesDevToolsExtension @Inject constructor(objects: ObjectFactory) {
  val appComposable: Property<String> = objects.property(String::class.java)
}

abstract class GenerateComposablesDevToolsMainTask : DefaultTask() {
  @get:Input
  abstract val appComposable: Property<String>

  @get:OutputDirectory
  abstract val outputDirectory: DirectoryProperty

  @TaskAction
  fun generate() {
    val appComposableCall = appComposable.get().trim()
    require(appComposableCall.isNotEmpty()) {
      "composablesDevTools.appComposable must point to a JVM-compatible @Composable function."
    }

    val outputDir = outputDirectory.get().asFile
    outputDir.deleteRecursively()

    val sourceFile = outputDirectory
      .file("com/composables/devtools/generated/DevToolsMain.kt")
      .get()
      .asFile

    sourceFile.parentFile.mkdirs()
    sourceFile.writeText(
      """
      package com.composables.devtools.generated

      import androidx.compose.runtime.getValue
      import androidx.compose.runtime.mutableStateOf
      import androidx.compose.runtime.remember
      import androidx.compose.runtime.setValue
      import androidx.compose.ui.unit.LayoutDirection
      import androidx.compose.ui.unit.dp
      import androidx.compose.ui.window.Window
      import androidx.compose.ui.window.application
      import androidx.compose.ui.window.rememberWindowState
      import com.composables.devtools.DevToolsDevices
      import com.composables.devtools.DevTools
      import com.composables.devtools.DevToolsOrientation
      import com.composables.devtools.DevToolsZoom
      import com.composables.devtools.canRotate
      import com.composables.devtools.deviceForDevToolsShortcut
      import com.composables.devtools.devToolsZoomForShortcut
      import com.composables.devtools.isDevToolsHotReloadAvailable
      import com.composables.devtools.isDevToolsHotReloadShortcut
      import com.composables.devtools.isDevToolsLayoutDirectionShortcut
      import com.composables.devtools.isDevToolsRotationShortcut
      import com.composables.devtools.isDevToolsScreenshotCopyShortcut
      import com.composables.devtools.isDevToolsScreenshotSaveShortcut
      import com.composables.devtools.isDevToolsToolbarShortcut
      import com.composables.devtools.oppositeDevToolsLayoutDirection
      import com.composables.devtools.requestDevToolsHotReload
      import com.composables.devtools.rotated

      fun main() = application {
        val windowState = rememberWindowState(width = 960.dp, height = 860.dp)
        var selectedDevice by remember { mutableStateOf(DevToolsDevices.Desktop) }
        var selectedOrientation by remember { mutableStateOf(DevToolsOrientation.Portrait) }
        var selectedLayoutDirection by remember { mutableStateOf(LayoutDirection.Ltr) }
        var selectedZoom by remember { mutableStateOf(DevToolsZoom.Default) }
        var toolbarVisible by remember { mutableStateOf(true) }
        var saveScreenshotRequest by remember { mutableStateOf(0) }
        var copyScreenshotRequest by remember { mutableStateOf(0) }

        Window(
          onCloseRequest = ::exitApplication,
          state = windowState,
          title = "Composables Dev Tools",
          onKeyEvent = { event ->
            val shortcutDevice = deviceForDevToolsShortcut(event)
            if (shortcutDevice != null) {
              selectedDevice = shortcutDevice
              true
            } else if (isDevToolsHotReloadShortcut(event) && isDevToolsHotReloadAvailable()) {
              runCatching { requestDevToolsHotReload() }
              true
            } else if (isDevToolsScreenshotSaveShortcut(event)) {
              saveScreenshotRequest++
              true
            } else if (isDevToolsScreenshotCopyShortcut(event)) {
              copyScreenshotRequest++
              true
            } else if (isDevToolsRotationShortcut(event) && selectedDevice.canRotate) {
              selectedOrientation = selectedOrientation.rotated()
              true
            } else if (isDevToolsLayoutDirectionShortcut(event)) {
              selectedLayoutDirection = selectedLayoutDirection.oppositeDevToolsLayoutDirection()
              true
            } else if (isDevToolsToolbarShortcut(event)) {
              toolbarVisible = !toolbarVisible
              true
            } else {
              val shortcutZoom = devToolsZoomForShortcut(event, selectedZoom)
              if (shortcutZoom != null) {
                selectedZoom = shortcutZoom
                true
              } else {
                false
              }
            }
          },
        ) {
          DevTools(
            initialDevice = DevToolsDevices.Desktop,
            selectedDevice = selectedDevice,
            onDeviceSelected = { selectedDevice = it },
            selectedOrientation = selectedOrientation,
            onOrientationSelected = { selectedOrientation = it },
            selectedLayoutDirection = selectedLayoutDirection,
            onLayoutDirectionSelected = { selectedLayoutDirection = it },
            selectedZoom = selectedZoom,
            onZoomSelected = { selectedZoom = it },
            showControls = toolbarVisible,
            saveScreenshotRequest = saveScreenshotRequest,
            copyScreenshotRequest = copyScreenshotRequest,
          ) {
            $appComposableCall()
          }
        }
      }
      """.trimIndent() + "\n",
    )
  }
}

class ComposablesDevToolsPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    val extension = project.extensions.create<ComposablesDevToolsExtension>("composablesDevTools")
    val generatedSourceDirectory = project.layout.buildDirectory.dir("generated/composablesDevTools/kotlin")

    project.pluginManager.apply("org.jetbrains.compose.hot-reload")

    val generateMain = project.tasks.register<GenerateComposablesDevToolsMainTask>("generateDevToolsMain") {
      appComposable.set(extension.appComposable)
      outputDirectory.set(generatedSourceDirectory)
    }

    project.plugins.withId("org.jetbrains.kotlin.multiplatform") {
      project.configure<KotlinMultiplatformExtension> {
        jvm()
      }

      project.afterEvaluate {
        project.configure<KotlinMultiplatformExtension> {
          val devToolsTarget = targets.filterIsInstance<KotlinJvmTarget>().first()
          val mainCompilation = devToolsTarget.compilations.named("main").get()
          val devCompilation = devToolsTarget.compilations.maybeCreate("dev")
          devCompilation.associateWith(mainCompilation)

          devCompilation.defaultSourceSet.apply {
            kotlin.srcDir(generateMain.map { it.outputDirectory })
            dependencies {
              implementation(project.dependencies.project(mapOf("path" to DEV_TOOLS_RUNTIME_PATH)))
              implementation(ComposePlugin.Dependencies(project).desktop.currentOs)
            }
          }

          project.tasks.register<ComposeHotRun>("runDevTools") {
            group = "compose dev tools"
            description = "Runs the configured Compose app composable inside the dev tools host with automatic hot reload."
            dependsOn(generateMain)
            compilation.set(devCompilation)
            mainClass.set(DEV_TOOLS_MAIN_CLASS)
            isAutoReloadEnabled.set(true)
          }

          project.tasks.register("checkDevToolsCompatibility") {
            group = "compose dev tools"
            description = "Checks that the dev tools app composable compiles for the JVM target."
            dependsOn(devCompilation.compileTaskProvider)
          }
        }
      }
    }
  }
}

private const val DEV_TOOLS_RUNTIME_PATH = ":devtools:runtime"
private const val DEV_TOOLS_MAIN_CLASS = "com.composables.devtools.generated.DevToolsMainKt"
