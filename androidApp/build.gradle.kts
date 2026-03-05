plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(projects.shared)
    debugImplementation(libs.ui.tooling.preview)
}

android {
    namespace = "io.github.mayachen350.mayascope"
    compileSdk = 36

    defaultConfig {
        applicationId = "io.github.mayachen350.mayascope"
        minSdk = 26
        targetSdk = 36
        versionCode = 6
        versionName = "2026.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            versionNameSuffix = "-DEBUG"
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}