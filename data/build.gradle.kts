@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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
    sourceCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
    targetCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}
