@file:Suppress("UnstableApiUsage")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.nio.charset.StandardCharsets
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.testing.Test

plugins {
  application
  alias(libs.plugins.jvm)
  alias(libs.plugins.shadow)
  alias(libs.plugins.buildconfig)
}

val mainClassName = "com.composables.cli.CliKt"
val cliName = "composables"

group = "com.composables"

version = libs.versions.composables.cli.get()

buildConfig { buildConfigField("Version", libs.versions.composables.cli.get()) }

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
}

kotlin {
  jvmToolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
}

application {
  applicationName = cliName
  mainClass.set(mainClassName)
}

sourceSets {
  named("main") {
    java.setSrcDirs(listOf("src/jvmMain/kotlin"))
    resources.setSrcDirs(listOf("src/jvmMain/resources"))
  }
  named("test") {
    java.setSrcDirs(listOf("src/jvmTest/kotlin"))
    resources.setSrcDirs(emptyList<String>())
  }
  create("integrationTest") {
    java.setSrcDirs(listOf("src/integrationTest/kotlin"))
    resources.setSrcDirs(emptyList<String>())
    compileClasspath += sourceSets.main.get().output + configurations.testRuntimeClasspath.get()
    runtimeClasspath += output + compileClasspath
  }
  create("npmTest") {
    java.setSrcDirs(listOf("src/npmTest/kotlin"))
    resources.setSrcDirs(emptyList<String>())
    compileClasspath += sourceSets.main.get().output + configurations.testRuntimeClasspath.get()
    runtimeClasspath += output + compileClasspath
  }
}

val integrationTestSourceSet = sourceSets.named("integrationTest").get()
val npmTestSourceSet = sourceSets.named("npmTest").get()

configurations[integrationTestSourceSet.implementationConfigurationName].extendsFrom(
    configurations.testImplementation.get())

configurations[integrationTestSourceSet.runtimeOnlyConfigurationName].extendsFrom(
    configurations.testRuntimeOnly.get())

configurations[npmTestSourceSet.implementationConfigurationName].extendsFrom(
    configurations.testImplementation.get())

configurations[npmTestSourceSet.runtimeOnlyConfigurationName].extendsFrom(
    configurations.testRuntimeOnly.get())

dependencies {
  implementation(libs.debugln)
  implementation(libs.clikt)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.mcp.kotlin.server)
  runtimeOnly(libs.slf4j.nop)

  testImplementation(libs.assertk)
  testImplementation(kotlin("test"))
}

tasks.test { useJUnitPlatform() }

val integrationTest =
    tasks.register<Test>("integrationTest") {
      description = "Runs end-to-end integration tests for the CLI."
      group = "verification"

      testClassesDirs = integrationTestSourceSet.output.classesDirs
      classpath = integrationTestSourceSet.runtimeClasspath
      shouldRunAfter(tasks.test)
      dependsOn(tasks.named("installDist"))
      dependsOn(tasks.named("shadowJar"))
      useJUnitPlatform()
    }

val npmPackageCheck =
    tasks.register<Test>("npmPackageCheck") {
      description = "Verifies the published npm package installs and launches correctly."
      group = "verification"

      testClassesDirs = npmTestSourceSet.output.classesDirs
      classpath = npmTestSourceSet.runtimeClasspath
      shouldRunAfter(tasks.named("integrationTest"))
      dependsOn(tasks.named("npmPack"))
      useJUnitPlatform()
    }

tasks.named<ShadowJar>("shadowJar") {
  group = "build"
  from(sourceSets.main.get().output)
  configurations = listOf(project.configurations.runtimeClasspath.get())

  archiveFileName.set("$cliName.jar")

  manifest { attributes("Main-Class" to mainClassName) }
  mergeServiceFiles()
}

val npmPackageDir = layout.buildDirectory.dir("npm/package")
val npmTarballDir = layout.buildDirectory.dir("npm/tarball")

val assembleNpmPackage =
    tasks.register<Sync>("assembleNpmPackage") {
      description = "Assembles the npm package for Composables CLI."
      group = "distribution"

      dependsOn(tasks.named("shadowJar"))

      from("package.json")
      from("src/npm") {
        includeEmptyDirs = false
        filteringCharset = StandardCharsets.UTF_8.name()
      }
      from(rootProject.file("README.md"))
      from(tasks.named<ShadowJar>("shadowJar")) { rename { "composables.jar" } }
      into(npmPackageDir)
    }

val npmPack =
    tasks.register<Exec>("npmPack") {
      description = "Creates a tarball for the npm package."
      group = "distribution"

      dependsOn(assembleNpmPackage)

      inputs.dir(npmPackageDir)
      outputs.dir(npmTarballDir)

      workingDir(npmPackageDir)
      doFirst {
        delete(npmTarballDir)
        npmTarballDir.get().asFile.mkdirs()
      }
      commandLine(
          npmCommand(), "pack", "--pack-destination", npmTarballDir.get().asFile.absolutePath)
    }

tasks.jar { finalizedBy(tasks.named("shadowJar")) }

val devTemplateOutputDir = layout.buildDirectory.dir("dev-template")

tasks.register<JavaExec>("renderTemplate") {
  group = "application"
  description =
      "Renders the bundled project template into build/dev-template/app for local JVM template development."

  dependsOn(tasks.jar)

  mainClass.set("com.composables.cli.DevTemplateKt")
  classpath(sourceSets.main.get().runtimeClasspath)

  systemProperty("composables.template.outputRoot", devTemplateOutputDir.get().asFile.absolutePath)
  systemProperty("composables.template.projectDir", "app")
  systemProperty("composables.template.targets", "jvm")
}

tasks.register<Exec>("runTemplate") {
  group = "application"
  description = "Renders the bundled project template and runs its JVM target locally."

  dependsOn("renderTemplate")

  val renderedProjectDir = devTemplateOutputDir.map { it.dir("app") }
  workingDir(renderedProjectDir)
  commandLine("./gradlew", "run")
}

fun npmCommand(): String =
    if (System.getProperty("os.name").startsWith("Windows")) "npm.cmd" else "npm"
