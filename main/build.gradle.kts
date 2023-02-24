@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "MARVEL_URL",
                "\"https://gateway.marvel.com/v1/public/\""
            )
        }

        release {
            buildConfigField(
                "String",
                "MARVEL_URL",
                "\"https://gateway.marvel.com/v1/public/\""
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
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":data-remote"))
    implementation(project(":data-local"))
    implementation(project(":presentation"))

    implementation(libs.kotlin.stdlib)

    with(libs.koin) {
        implementation(core)
        implementation(android)
        testImplementation(junit)
    }

    with(libs.androidx) {
        implementation(room.runtime)
        kapt(room.compiler)
    }

    with(libs.kotlin.coroutines) {
        implementation(core)
        testImplementation(test)
    }

    with(libs) {
        implementation(retrofit)
        implementation(okhttp.loggingInterceptor)

        testImplementation(junit4)
        testImplementation(junit.jupiter.api)
        testImplementation(mockK)
    }
}
