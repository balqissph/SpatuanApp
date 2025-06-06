plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services) // Google services plugin for Firebase
    kotlin("kapt")
}

android {
    namespace = "com.uasmobilek5.spatuanapp"
    compileSdk = 35 // Consider using the latest stable SDK for production

    defaultConfig {
        applicationId = "com.uasmobilek5.spatuanapp"
        minSdk = 24
        targetSdk = 35 // Consider using the latest stable SDK for production
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true // Recommended for release builds
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Consider adding signingConfigs for release builds
        }
        debug {
            // You might want to explicitly define debug build type settings if needed
            // e.g., applicationIdSuffix ".debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    // Optional: Enable View Binding (or Data Binding)
    // buildFeatures {
    //     viewBinding = true
    // }
}

dependencies {
    // Firebase (gunakan BOM untuk versi sinkronisasi otomatis)
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
    // Add the dependency for the Realtime Database library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-database")

    // AndroidX Core and UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material) // For Material Design components
    implementation(libs.androidx.activity) // For Activity Result APIs, etc.
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.database)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Gambar
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")

}