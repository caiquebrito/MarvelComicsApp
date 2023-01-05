@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin)
}

dependencies {
    implementation(project(":entity"))

    with(libs.kotlin) {
        implementation(stdlib)
        implementation(coroutines.core)
    }

    with(libs) {
        testImplementation(androidx.archCoreTesting)
        testImplementation(kotlin.coroutines.test)
        testImplementation(mockK)

        testImplementation(junit4)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}