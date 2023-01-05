@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin)
}

dependencies {
    implementation(project(":entity"))
    implementation(project(":data"))

    with(libs.kotlin) {
        implementation(stdlib)
        implementation(coroutines.core)
    }

    with(libs) {
        implementation(retrofit)
        implementation(retrofit.gsonConverter)

        testImplementation(junit4)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}