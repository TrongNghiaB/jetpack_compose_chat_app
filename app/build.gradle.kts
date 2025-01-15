plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.crypto_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.crypto_app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
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
    implementation(libs.firebase.auth)
    val lifecycleVersion = "2.8.1"
    val navVersion = "2.7.7"
    val roomVersion = "2.6.1"
    val retrofitVersion = "2.11.0"
    val media3_version = "1.4.1"

    //Compose Navigation
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    //coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycleVersion")
    //room
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    //okhttp Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    //glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.4-beta")
    //load image from url
    implementation("io.coil-kt:coil-compose:2.6.0")
    //encrypted share preference
    implementation("androidx.security:security-crypto:1.1.0-alpha05")
    //gson
    implementation("com.google.code.gson:gson:2.11.0")
    //firebase
    implementation (platform(libs.firebase.bom))
    implementation(libs.firebase.storage.ktx)
    implementation(libs.firebase.firestore.ktx)
    //crop image
    implementation("com.vanniktech:android-image-cropper:4.6.0")
    //foundation
    implementation("androidx.compose.foundation:foundation:1.7.0-beta06")
    //play video
    implementation("androidx.media3:media3-exoplayer:$media3_version")
    implementation("androidx.media3:media3-session:$media3_version")
    implementation("androidx.media3:media3-ui:$media3_version") 

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
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


}