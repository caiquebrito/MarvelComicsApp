@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":entity"))

    with(libs) {
        implementation(kotlin.stdlib)
        implementation(kotlin.coroutines.core)

        testImplementation(junit4)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}