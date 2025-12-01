plugins {
    id("com.google.devtools.ksp")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.brainstorm.unopad"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.brainstorm.unopad"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "21"
    }

}

dependencies {
    // CORE_ANDROID
    implementation("androidx.core:core-ktx:1.17.0")

    implementation("androidx.activity:activity-ktx:1.12.0")

    // Provides Kotlin extension functions and lifecycle-aware APIs
    implementation("androidx.activity:activity-ktx:1.12.0")

    // Provides AppCompatActivity class and some layout xml
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")

    // LAYOUTS
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.3.0")

    // Room database
    ksp("androidx.room:room-compiler:2.8.4")
    implementation("androidx.room:room-ktx:2.8.4")
    implementation("androidx.room:room-runtime:2.8.4")
}