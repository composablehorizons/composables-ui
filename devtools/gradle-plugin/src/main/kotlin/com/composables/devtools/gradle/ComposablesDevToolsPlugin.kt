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
      import androidx.compose.runtime.rememberCoroutineScope
      import androidx.compose.runtime.setValue
      import androidx.compose.ui.unit.LayoutDirection
      import androidx.compose.ui.unit.dp
      import androidx.compose.ui.window.Window
      import androidx.compose.ui.window.application
      import androidx.compose.ui.window.rememberWindowState
      import androidx.datastore.core.DataStore
      import androidx.datastore.preferences.core.Preferences
      import androidx.datastore.preferences.core.edit
      import androidx.datastore.preferences.core.floatPreferencesKey
      import androidx.datastore.preferences.core.stringPreferencesKey
      import androidx.datastore.preferences.core.PreferenceDataStoreFactory
      import com.composables.devtools.DevTools
      import com.composables.devtools.DevToolsColorScheme
      import com.composables.devtools.DevToolsDevice
      import com.composables.devtools.DevToolsDevices
      import com.composables.devtools.DevToolsOrientation
      import com.composables.devtools.DevToolsZoom
      import com.composables.devtools.DevToolsZoomLevels
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
      import java.io.File
      import kotlin.math.abs
      import kotlinx.coroutines.flow.first
      import kotlinx.coroutines.launch
      import kotlinx.coroutines.runBlocking
      import okio.Path.Companion.toPath

      fun main() = application {
        val windowState = rememberWindowState(width = 960.dp, height = 860.dp)
        val displaySettingsScope = rememberCoroutineScope()
        val displaySettings = remember { loadDevToolsDisplaySettings() }
        var selectedDevice by remember { mutableStateOf(displaySettings.device) }
        var selectedOrientation by remember { mutableStateOf(displaySettings.orientation) }
        var selectedLayoutDirection by remember { mutableStateOf(displaySettings.layoutDirection) }
        var selectedColorScheme by remember { mutableStateOf(displaySettings.colorScheme) }
        var selectedZoom by remember { mutableStateOf(displaySettings.zoom) }
        var toolbarVisible by remember { mutableStateOf(true) }
        var saveScreenshotRequest by remember { mutableStateOf(0) }
        var copyScreenshotRequest by remember { mutableStateOf(0) }
        fun persistDisplaySettings() {
          val settings =
            DevToolsDisplaySettings(
                device = selectedDevice,
                orientation = selectedOrientation,
                layoutDirection = selectedLayoutDirection,
                colorScheme = selectedColorScheme,
                zoom = selectedZoom,
            )
          displaySettingsScope.launch { saveDevToolsDisplaySettings(settings) }
        }

        Window(
          onCloseRequest = ::exitApplication,
          state = windowState,
          title = "Composables Dev Tools",
          onKeyEvent = { event ->
            val shortcutDevice = deviceForDevToolsShortcut(event)
            if (shortcutDevice != null) {
              selectedDevice = shortcutDevice
              persistDisplaySettings()
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
              persistDisplaySettings()
              true
            } else if (isDevToolsLayoutDirectionShortcut(event)) {
              selectedLayoutDirection = selectedLayoutDirection.oppositeDevToolsLayoutDirection()
              persistDisplaySettings()
              true
            } else if (isDevToolsToolbarShortcut(event)) {
              toolbarVisible = !toolbarVisible
              true
            } else {
              val shortcutZoom = devToolsZoomForShortcut(event, selectedZoom)
              if (shortcutZoom != null) {
                selectedZoom = shortcutZoom
                persistDisplaySettings()
                true
              } else {
                false
              }
            }
          },
        ) {
          DevTools(
            initialDevice = DevToolsDevices.Mobile,
            selectedDevice = selectedDevice,
            onDeviceSelected = {
              selectedDevice = it
              persistDisplaySettings()
            },
            selectedOrientation = selectedOrientation,
            onOrientationSelected = {
              selectedOrientation = it
              persistDisplaySettings()
            },
            selectedLayoutDirection = selectedLayoutDirection,
            onLayoutDirectionSelected = {
              selectedLayoutDirection = it
              persistDisplaySettings()
            },
            selectedColorScheme = selectedColorScheme,
            onColorSchemeSelected = {
              selectedColorScheme = it
              persistDisplaySettings()
            },
            selectedZoom = selectedZoom,
            onZoomSelected = {
              selectedZoom = it
              persistDisplaySettings()
            },
            showControls = toolbarVisible,
            saveScreenshotRequest = saveScreenshotRequest,
            copyScreenshotRequest = copyScreenshotRequest,
          ) {
            $appComposableCall()
          }
        }
      }

      private data class DevToolsDisplaySettings(
        val device: DevToolsDevice = DevToolsDevices.Mobile,
        val orientation: DevToolsOrientation = DevToolsOrientation.Portrait,
        val layoutDirection: LayoutDirection = LayoutDirection.Ltr,
        val colorScheme: DevToolsColorScheme = DevToolsColorScheme.Light,
        val zoom: DevToolsZoom = DevToolsZoom.Default,
      )

      private val devToolsDisplayDataStore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.createWithPath {
          File(
              System.getProperty("user.home"),
              "Library/Application Support/Composables Dev Tools/display.preferences_pb",
          )
              .apply { parentFile.mkdirs() }
              .absolutePath
              .toPath()
        }
      }

      private fun loadDevToolsDisplaySettings(): DevToolsDisplaySettings =
        runBlocking {
          val preferences = devToolsDisplayDataStore.data.first()
          DevToolsDisplaySettings(
            device = devToolsDeviceForId(preferences[DevicePreferenceKey] ?: DevToolsDevices.Mobile.id),
            orientation = devToolsOrientationForId(preferences[OrientationPreferenceKey] ?: "portrait"),
            layoutDirection = devToolsLayoutDirectionForId(preferences[LayoutDirectionPreferenceKey] ?: "ltr"),
            colorScheme = devToolsColorSchemeForId(preferences[ColorSchemePreferenceKey] ?: "light"),
            zoom = devToolsZoomForScale(preferences[ZoomPreferenceKey] ?: DevToolsZoom.Default.scale),
          )
        }

      private suspend fun saveDevToolsDisplaySettings(settings: DevToolsDisplaySettings) {
        devToolsDisplayDataStore.edit { preferences ->
          preferences[DevicePreferenceKey] = settings.device.id
          preferences[OrientationPreferenceKey] = settings.orientation.id
          preferences[LayoutDirectionPreferenceKey] = settings.layoutDirection.id
          preferences[ColorSchemePreferenceKey] = settings.colorScheme.id
          preferences[ZoomPreferenceKey] = settings.zoom.scale
        }
      }

      private fun devToolsDeviceForId(id: String): DevToolsDevice =
        DevToolsDevices.Default.firstOrNull { it.id == id } ?: DevToolsDevices.Mobile

      private fun devToolsOrientationForId(id: String): DevToolsOrientation =
        if (id == "landscape") DevToolsOrientation.Landscape else DevToolsOrientation.Portrait

      private val DevToolsOrientation.id: String
        get() = if (this == DevToolsOrientation.Landscape) "landscape" else "portrait"

      private fun devToolsLayoutDirectionForId(id: String): LayoutDirection =
        if (id == "rtl") LayoutDirection.Rtl else LayoutDirection.Ltr

      private val LayoutDirection.id: String
        get() = if (this == LayoutDirection.Rtl) "rtl" else "ltr"

      private fun devToolsColorSchemeForId(id: String): DevToolsColorScheme =
        if (id == "dark") DevToolsColorScheme.Dark else DevToolsColorScheme.Light

      private val DevToolsColorScheme.id: String
        get() = if (this == DevToolsColorScheme.Dark) "dark" else "light"

      private fun devToolsZoomForScale(scale: Float): DevToolsZoom =
        DevToolsZoomLevels.Default.minBy { abs(it.scale - scale) }

      private val DevicePreferenceKey = stringPreferencesKey("display.device")
      private val OrientationPreferenceKey = stringPreferencesKey("display.orientation")
      private val LayoutDirectionPreferenceKey = stringPreferencesKey("display.layoutDirection")
      private val ColorSchemePreferenceKey = stringPreferencesKey("display.colorScheme")
      private val ZoomPreferenceKey = floatPreferencesKey("display.zoom")
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
              val runtimeDependency =
                  if (project.findProject(DEV_TOOLS_RUNTIME_PATH) != null) {
                    project.dependencies.project(mapOf("path" to DEV_TOOLS_RUNTIME_PATH))
                  } else {
                    DEV_TOOLS_RUNTIME_COORDINATES
              }
              implementation(runtimeDependency)
              implementation(DEV_TOOLS_DATASTORE_PREFERENCES_COORDINATES)
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
private const val DEV_TOOLS_RUNTIME_COORDINATES = "com.composables:runtime:0.1.0"
private const val DEV_TOOLS_DATASTORE_PREFERENCES_COORDINATES =
    "androidx.datastore:datastore-preferences:1.2.1"
private const val DEV_TOOLS_MAIN_CLASS = "com.composables.devtools.generated.DevToolsMainKt"
