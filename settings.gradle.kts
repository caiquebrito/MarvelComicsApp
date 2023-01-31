pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        jcenter()
        mavenLocal()
    }
    versionCatalogs {
        create("libs") {
            from(files("./gradle/versions.toml"))
        }
    }
}

rootProject.name = "MarvelComicsApp"
include(
    ":app",
    ":domain",
    ":presentation",
    ":data",
    ":data-remote",
    ":data-local",
    ":di",
    ":entity",
    ":marvel"
)
