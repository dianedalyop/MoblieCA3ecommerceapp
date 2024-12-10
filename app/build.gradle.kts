


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}




android {
    namespace = "com.example.moblieca3ecommerceapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.moblieca3ecommerceapp"
        minSdk = 24
        targetSdk = 34
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
    val room_version = "2.6.1" // Or your preferred version

    // Room for local database
    implementation("androidx.room:room-runtime:$room_version") // Required
    kapt("androidx.room:room-compiler:$room_version")          // Annotation processor (Required for Room)

    // Optional: Room with Kotlin Coroutines
    implementation("androidx.room:room-ktx:$room_version")
    // Jetpack Compose
    implementation ("androidx.compose.ui:ui:1.3.0")
    implementation ("androidx.compose.material3:material3:1.0.0")
    implementation ("androidx.navigation:navigation-compose:2.5.3")
    // Retrofit for API calls
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    // Coil for image loading
    implementation ("io.coil-kt:coil-compose:2.2.2")
    // Room for local database


    // DataStore for preferences
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
// navigation
    implementation ("androidx.navigation:navigation-compose:2.8.4")
// Retrofit dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp for logging
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    // loading images
    implementation("io.coil-kt:coil-compose:2.3.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    // room DB


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.analytics.impl)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}