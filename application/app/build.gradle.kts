plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint")
    id("kotlinx-serialization")
    id("kotlin-kapt")
    id("com.google.devtools.ksp") version "1.7.20-1.0.8"
}
kotlin {
    sourceSets {
        named("debug") {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        named("release") {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}
android {
    compileSdk = 33

    defaultConfig {
        applicationId = "hr.dice.luka_kurtovic.weatherapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), ("proguard-rules.pro")))
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.26.0-alpha")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Compose
    implementation("androidx.activity:activity-compose:1.6.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.3.2")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.2")
    implementation("androidx.compose.ui:ui:1.3.2")

    // Location
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    // Koin
    implementation("io.insert-koin:koin-androidx-compose:3.1.6")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")

    // Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.26.2-beta")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.1")

    // Room
    implementation("androidx.room:room-runtime:2.4.3")
    implementation("androidx.room:room-ktx:2.4.3")
    kapt("androidx.room:room-compiler:2.4.3")

    // Button toggle group
    implementation("com.robertlevonyan.compose:buttontogglegroup:1.1.0")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // ViewPager
    implementation("com.google.accompanist:accompanist-pager:0.27.1")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.27.1")

    // Compose Destinations
    ksp("io.github.raamcosta.compose-destinations:ksp:1.7.30-beta")
    implementation("io.github.raamcosta.compose-destinations:core:1.7.30-beta")

    // Appcompat
    implementation("androidx.appcompat:appcompat:1.7.0-alpha01")
    implementation("androidx.appcompat:appcompat-resources:1.7.0-alpha01")

    // Local unit tests
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("io.mockk:mockk:1.13.3")
    testImplementation("app.cash.turbine:turbine:0.12.1")
}
