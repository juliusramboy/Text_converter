plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.textconverter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.textconverter"
        minSdk = 24
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.cast.framework)
    implementation(libs.vision.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //tamang pag add
    implementation ("com.github.dhaval2404:imagepicker:2.1")

        // To recognize Latin script
        implementation ("com.google.mlkit:text-recognition:16.0.1")

        // To recognize Chinese script
        implementation ("com.google.mlkit:text-recognition-chinese:16.0.1")

        // To recognize Devanagari script
        implementation ("com.google.mlkit:text-recognition-devanagari:16.0.1")

        // To recognize Japanese script
        implementation ("com.google.mlkit:text-recognition-japanese:16.0.1")

        // To recognize Korean script
        implementation ("com.google.mlkit:text-recognition-korean:16.0.1")

}