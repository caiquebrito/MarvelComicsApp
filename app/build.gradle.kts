@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.marvelcomics.brito.marvelcomics"
        versionCode = 1
        versionName = "1.0.0"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".stage"
        }

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
    }

    kotlinOptions {
        jvmTarget = libs.versions.javaTarget.get()
    }

    buildFeatures {
        viewBinding = true
    }

    packagingOptions {
        packagingOptions.resources.excludes += setOf(
            "/META-INF/{AL2.0,LGPL2.1}"
        )
    }
}

dependencies {
    implementation(project(":marvel"))
    implementation(project(":di"))

    with(libs.androidx) {
        implementation(appcompat)
        androidTestImplementation(test.espressoCore)
    }

    with(libs.koin) {
        implementation(core)
        implementation(android)
    }

    with(libs) {
        implementation(kotlin.stdlib)

        androidTestImplementation(junit4)
        testImplementation(junit4)
    }
}
