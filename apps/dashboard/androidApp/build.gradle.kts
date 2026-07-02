plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.composables.ui.dashboard"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "com.composables.ui.dashboard"
        minSdk = libs.versions.android.min.sdk.get().toInt()
        targetSdk = libs.versions.android.compile.sdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":apps:dashboard:shared"))
    implementation(libs.androidx.activity.compose)
}
