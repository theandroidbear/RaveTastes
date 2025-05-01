plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.ravemaster.recipeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ravemaster.recipeapp"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

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
    buildFeatures {
        viewBinding = true
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

    implementation(libs.retrofit)
    implementation(libs.okhttp3)
    implementation(libs.logging.interceptor)
    implementation(libs.gsonConverter)

    implementation(libs.glide)

    implementation(libs.shimmer.android)

    implementation(libs.chart.dependency)

    implementation(libs.exoplayer)

    implementation(libs.media3)
    implementation(libs.media3UI)
    implementation(libs.media3HLS)

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    implementation(libs.splashscreen)

    implementation(libs.lottie)
    implementation(libs.viewmodel)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}