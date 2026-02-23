plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.ringkhang.myapplication"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.ringkhang.myapplication"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.cardview)
    implementation(libs.viewpager2)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
// OkHttp logging (helpful for debugging API calls)
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
// Gson
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
}