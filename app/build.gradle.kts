import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    id("com.google.dagger.hilt.android") version "2.57" apply false
}

fun getProperty(filename: String, propertyName: String): String {
    val properties = Properties()
    properties.load(project.rootProject.file(filename).inputStream())
    return properties.getProperty(propertyName)
}
android {
    namespace = "ru.kulishov.openweatherapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "ru.kulishov.openweatherapp"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "OW_API_KEY", "${getProperty("local.properties", "openWeatherApiKey")}")
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.sqlite.bundled)
    implementation(libs.room.runtime)
    //implementation(libs.gson)

    implementation (libs.converter.gson)
    implementation (libs.retrofit)

    ksp(libs.room.compiler)

    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

//    implementation(libs.hilt.android)
//    ksp(libs.hilt.android.compiler)
//    implementation(libs.androidx.hilt.navigation.compose)
}

room {
    schemaDirectory("$projectDir/schemas")
}

