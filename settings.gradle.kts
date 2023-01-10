pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./gradle/versions.toml"))
        }
    }
}

rootProject.name = "MarvelComicsApp"
include(":app",":domain",":presentation",":data",":data-remote",":data-local",":di",":entity",":marvel")
