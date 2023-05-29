@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

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
        testImplementation(kotlin.test)
        testImplementation(mockK)

        testImplementation(junit4)
    }
}

java {
    sourceCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
    targetCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
}
