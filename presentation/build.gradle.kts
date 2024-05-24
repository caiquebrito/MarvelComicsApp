@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.navigation.safeargs.kotlin)
    alias(libs.plugins.devtools.ksp)
}

kapt {
    correctErrorTypes = true
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        consumerProguardFiles("consumer-rules.pro")
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
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    namespace = "com.marvelcomics.brito.presentation"
}

val debugImplementation = "debugImplementation"

dependencies {
    implementation(project(":data"))
    implementation(project(":entity"))
    implementation(project(":domain"))

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    with(libs.kotlin) {
        implementation(stdlib)
        testImplementation(coroutines.test)
    }

    with(libs.kotlin.coroutines) {
        implementation(core)
        implementation(android)
    }

    with(libs.androidx) {
        implementation(lifecycle.runtime.ktx)
        implementation(lifecycle.common.java8)
        implementation(recyclerView)
        implementation(appcompat)
        implementation(coreKtx)
        implementation(constraintLayout)
        implementation(navigationUi)
        implementation(navigationFragment)
        testImplementation(archCoreTesting)
    }

    with(libs.androidx.lifecycle) {
        implementation(viewmodel.ktx)
        implementation(runtime.ktx)
    }

    with(libs.compose) {
        implementation(foundation.foundation)
        implementation(material.material)
        implementation(ui.tooling)
        implementation(ui.tooling.preview)
        implementation(ui.ui)
        implementation(constraintLayout)
        implementation(coil)
        kapt(runtime)
        implementation(material3)
        implementation(google.material)
        implementation(activity)
        testImplementation(test.junit4)
        debugImplementation(test.manifest)
    }

    with(libs) {
        testImplementation(mockK)
        testImplementation(junit4)

        implementation(google.accompanist.systemuicontroller)

        implementation(glide)
        kapt(glide.compiler)

        implementation(koin.android)
        implementation(koin.androidx.compose)

        testImplementation(junit4)
        androidTestImplementation(junit4)
        androidTestImplementation(compose.test.junit4)

        debugImplementation(compose.test.manifest)

        testImplementation(robolectric)
    }

    with(libs.okhttp) {
        implementation(okhttp)
        implementation(loggingInterceptor)
    }
}

tasks.withType<Test> { // Show more test execution information
    testLogging {
        events("failed", "passed", "skipped")
        exceptionFormat = TestExceptionFormat.FULL
    }
}
