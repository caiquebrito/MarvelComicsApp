pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        jcenter()
        gradlePluginPortal()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        jcenter()
        gradlePluginPortal()
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
    ":dataremote",
    ":datalocal",
    ":main",
    ":entity"
)
