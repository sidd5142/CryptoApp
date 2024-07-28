plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.crypto_xml"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.crypto_xml"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:+")
    implementation("com.google.android.material:material:+")
    implementation("androidx.constraintlayout:constraintlayout:+")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:+")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:+")
    implementation("androidx.navigation:navigation-fragment-ktx:+")
    implementation("androidx.navigation:navigation-ui-ktx:+")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:+")
    androidTestImplementation("androidx.test.espresso:espresso-core:+")
//    implementation ("com.github.ybq:Android-SpinKit:1.4.0")
//    implementation ("com.airbnb.android:lottie:5.2.0")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("com.github.bumptech.glide:glide:4.16.0")





}