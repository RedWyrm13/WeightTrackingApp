plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.weighttracking"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weighttracking"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //Google Authentication
    //noinspection UseTomlInstead
    implementation ("androidx.credentials:credentials:1.3.0")
    //noinspection UseTomlInstead
    implementation ("androidx.credentials:credentials-play-services-auth:1.3.0")
    //noinspection UseTomlInstead
    implementation ("com.google.android.libraries.identity.googleid:googleid:1.1.1")

    //navigation
    implementation ("androidx.navigation:navigation-compose:2.7.2")

    //Graph making
    //noinspection UseTomlInstead
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    // Rooms Components
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.espresso.core)
    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    implementation(libs.androidx.room.ktx)

    //Extended Icons options
    implementation (libs.androidx.material.icons.extended)

    implementation (libs.lifecycle.viewmodel.compose)
    implementation (libs.androidx.runtime)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    //Updating this will break the CircularProgressIndicator. As of writing this, it has not been fixed. It is a known issue.
    implementation("androidx.compose.material3:material3-android:1.2.0-rc01")
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}