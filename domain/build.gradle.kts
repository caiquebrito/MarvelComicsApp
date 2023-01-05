@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin)
}

dependencies {
    implementation(project(":entity"))

    //Kotlin
    implementation(libs.kotlin.stdlib)

    //Coroutines
    implementation(libs.kotlin.coroutines.core)

    testImplementation(libs.androidx.archCoreTesting)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.mockK)

    testImplementation(libs.junit4)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}