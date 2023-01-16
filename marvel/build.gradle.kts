@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
}

kapt {
    correctErrorTypes = true
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

//    signingConfigs {
//        debug {
//            storeFile file ("../tools/debug.keystore")
//            storePassword = "android"
//            keyAlias = "androiddebugkey"
//            keyPassword = "android"
//        }
//    }

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
    }

    kotlinOptions {
        jvmTarget = libs.versions.javaTarget.get()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    packagingOptions {
        packagingOptions.resources.excludes += setOf(
            "/META-INF/{AL2.0,LGPL2.1}"
        )
    }
}

val debugImplementation = "debugImplementation"

dependencies {
    implementation(project(":presentation"))
    implementation(project(":entity"))
    implementation(project(":domain"))
    implementation(project(":di"))

    with(libs.androidx) {
        implementation(lifecycle.runtime.ktx)
        implementation(lifecycle.common.java8)
        implementation(recyclerView)
        implementation(appcompat)
        implementation(coreKtx)
        implementation(constraintLayout)
        implementation(navigationUi)
        implementation(navigationFragment)
        androidTestImplementation(test.espressoCore)
    }

    with(libs.compose) {
        implementation(foundation.foundation)
        implementation(material.material)
        implementation(ui.tooling)
        implementation(ui.tooling.preview)
        implementation(ui.ui)
        implementation(constraintLayout)
        kapt(runtime)
        implementation(material3)
        implementation(google.material)
        implementation(activity)
    }

    with(libs.kotlin) {
        implementation(stdlib)
        implementation(coroutines.core)
    }

    with(libs.okhttp) {
        implementation(okhttp)
        implementation(loggingInterceptor)
    }

    with(libs) {
        implementation(canarinho)

        implementation(glide)
        kapt(glide.compiler)

        implementation(koin.android)

        testImplementation(junit4)
        androidTestImplementation(junit4)
        androidTestImplementation(compose.test.junit4)

        debugImplementation(compose.test.manifest)
    }
}
