plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.jetbrains.compose.compiler) apply false
    alias(libs.plugins.spotless)
{{android_root_plugins}}
}

subprojects {
    apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target(
                fileTree(project.projectDir) {
                    include("src/**/*.kt")
                    exclude("src/**/resources/**/*.kt")
                }
            )
            ktfmt()
        }
        kotlinGradle {
            target("build.gradle.kts")
            ktfmt()
        }
        format("xml") {
            target("src/**/*.xml")
        }
    }
}

{{web_preload_task_wiring}}
