pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

fun readProperties(propertiesFile: File) = java.util.Properties().apply {
    try {
        propertiesFile.inputStream().use { fis ->
            load(fis)
        }
    } catch (ignored: Exception) {
    }
}

val repo = readProperties(File("./build-system/repo.properties"))
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://repos.cora.tools/repository/maven-proxy")
            credentials {
                username = System.getenv("NEXUS_USER") ?: repo["USERNAME"] as String?
                password = System.getenv("NEXUS_PASS") ?: repo["TOKEN"] as String?
            }
        }
        maven { url = uri("https://jitpack.io") }
        jcenter()
    }
    versionCatalogs {
        create("libs") {
            from(files("./gradle/versions.toml"))
        }
    }
}

rootProject.name = "MarvelComicsApp"
include(":app", ":domain", ":presentation", ":data", ":data-remote", ":data-local", ":di", ":entity", ":marvel")
