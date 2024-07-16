plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.moutamid.socketfusiontimer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moutamid.socketfusiontimer"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        setProperty("archivesBaseName", "SocketApp-$versionName")

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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.github.moisoni97:IndicatorSeekBar:3.0.0")
    implementation("com.fxn769:stash:1.3.2")
    implementation("com.github.maxwellobi:android-speech-recognition:v1.0.0-beta.1")

}