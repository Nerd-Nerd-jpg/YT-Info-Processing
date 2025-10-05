plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Compose Compiler Gradle plugin
    alias(libs.plugins.kotlin.compose)

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")

    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "com.example.ytinfoprocessing"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ytinfoprocessing"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

// Configuration options with the Compose Compiler Gradle Plugin
// composeCompiler {
//    reportsDestination = layout.buildDirectory.dir("compose_compiler")
//    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
// }

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
//    according to gemini, it's intrinsicly used by the other libraries
//    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Dagger-Hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)

    ksp(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson) // Gson converter for JSON parsing

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    api(project(":core-sdk"))
//    implementation(project(":attribute-management-sdk"))
//    implementation(project(":video-input-sdk"))
//    implementation(project(":view-all-videos-sdk"))

    // **Crucially, manually include the KSP compiler output from the feature modules**
    // You only need the KSP/KAPT dependency, not the implementation dependency.
    ksp(project(":attribute-management-sdk"))
    ksp(project(":video-input-sdk"))
    ksp(project(":view-all-videos-sdk"))
}
