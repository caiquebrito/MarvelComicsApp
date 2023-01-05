@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
    }

    kotlinOptions {
        jvmTarget = libs.versions.javaTarget.get()
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":entity"))
    implementation(project(":domain"))

    implementation(libs.kotlin.stdlib)

    with(libs.kotlin.coroutines) {
        implementation(core)
        implementation(android)
    }

    with(libs.androidx) {
        implementation(appcompat)
        implementation(coreKtx)
    }

    with(libs.androidx.lifecycle) {
        implementation(viewmodel.ktx)
        implementation(runtime.ktx)
    }

    testImplementation(libs.androidx.archCoreTesting)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.mockK)

    testImplementation(libs.junit4)
}
