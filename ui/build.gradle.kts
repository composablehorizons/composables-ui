/*
 * Copyright (c) 2026 Composable Horizons
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.maven.publish)
}

val publishGroupId = "com.composables"
val publishVersion = libs.versions.ui.get()
val projectUrl = "https://composables.com/ui"
val githubUrl = "github.com/composablehorizons/ui"
val pomArtifactId = "ui"
val pomName = "Composables UI Components"
val pomDescription = "Modern accessible components for Jetpack Compose and Compose Multiplatform."

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }

    jvm()

    js {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    android {
        namespace = "com.composables.ui"
        compileSdk = libs.versions.android.compile.sdk.get().toInt()
        minSdk = libs.versions.android.min.sdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposablesUI"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.compose.foundation)
            implementation(libs.composables.unstyled)
            api(libs.composables.unstyled.stack)
            api(libs.composables.unstyled.theming)
            implementation(libs.composables.unstyled.build.modifier)
            implementation(libs.composables.interaction.capabilities)
            implementation(libs.composables.ripple.indication)
        }
    }
}

group = publishGroupId
version = publishVersion

mavenPublishing {
    publishToMavenCentral(automaticRelease = true, validateDeployment = false)

    if (providers.gradleProperty("signingInMemoryKey").orNull?.isNotBlank() == true) {
        signAllPublications()
    }

    coordinates(
        groupId = publishGroupId,
        artifactId = pomArtifactId,
        version = publishVersion,
    )

    pom {
        name.set(pomName)
        description.set(pomDescription)
        url.set(projectUrl)

        licenses {
            license {
                name.set("MIT License")
                url.set("https://$githubUrl/blob/main/LICENSE")
            }
        }

        issueManagement {
            system.set("GitHub Issues")
            url.set("https://$githubUrl/issues")
        }

        developers {
            developer {
                id.set("composablehorizons")
                name.set("Composable Horizons")
                email.set("1665273+alexstyl@users.noreply.github.com")
                organization.set("Composable Horizons")
                organizationUrl.set("https://composables.com")
            }
        }

        scm {
            connection.set("scm:git:https://$githubUrl.git")
            developerConnection.set("scm:git:ssh://git@$githubUrl.git")
            url.set("https://$githubUrl/tree/main")
        }
    }
}
