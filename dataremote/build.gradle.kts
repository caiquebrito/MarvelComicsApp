@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)
}

dependencies {
    implementation(project(":entity"))
    implementation(project(":data"))

    with(libs.kotlin) {
        implementation(stdlib)
        implementation(coroutines.core)
        testImplementation(coroutines.test)
    }

    with(libs) {
        implementation(retrofit)
        implementation(retrofit.gsonConverter)

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
