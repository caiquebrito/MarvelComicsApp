@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

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
}

val debugImplementation = "debugImplementation"

dependencies {
    implementation(project(":data"))
    implementation(project(":entity"))
    implementation(project(":domain"))

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
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
        androidTestImplementation(test.espressoCore)
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
        kapt(runtime)
        implementation(material3)
        implementation(google.material)
        implementation(activity)
        implementation(navigation.core)
        ksp(navigation.ksp)
    }

    with(libs) {
        testImplementation(mockK)
        testImplementation(junit4)

        implementation(canarinho)

        implementation(glide)
        kapt(glide.compiler)

        implementation(koin.android)

        testImplementation(junit4)
        androidTestImplementation(junit4)
        androidTestImplementation(compose.test.junit4)

        debugImplementation(compose.test.manifest)
    }

    with(libs.okhttp) {
        implementation(okhttp)
        implementation(loggingInterceptor)
    }
}
