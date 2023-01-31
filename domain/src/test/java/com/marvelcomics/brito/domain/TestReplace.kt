package com.marvelcomics.brito.domain

import org.junit.Test

class TestReplace {

    @Test
    fun replaceVersions() {
        val listVersions = listOf(
            "coraAnalyticsBaseVersion = 1.4.38.ec3723a",
            "coraAuthBaseVersion = 1.3.20.da5f731",
            "coraCameraBaseVersion = 1.3.54.8a47cd6",
            "coraChatBaseVersion = 1.2.19.6ae62df",
            "coraCommonKotlinVersion = 1.1.190.e5ad32b",
            "coraCommonRestKotlinVersion = 1.0.33.75a1b8e",
            "coraCommonRestVersion = 1.4.33.75a1b8e",
            "coraCommonTestVersion = 1.0.190.e5ad32b",
            "coraCommonVersion = 1.5.201.94fcf2d",
            "coraCommonViewModelVersion = 1.1.190.e5ad32b",
            "coraDesignBaseVersion = 1.3.229.8674 c18 ",
            "coraFraudBaseVersion = 1.2.19.a1df225",
            "coraGrowthReleaseVersion = 1.1.505.dacfeca - release",
            "coraGrowthStageVersion = 1.1.505.dacfeca - stage",
            "coraNavigationReleaseVersion = 1.0.0.21 - release",
            "coraNavigationStageVersion = 1.0.0.21 - stage",
            "coraComposeVersion = 1.0.66.f8bd450",
            "agp = 7.2.1",
            "kotlin = 1.7.0",
            "gradle-ktlint = 11.0.0",
            "gradle-detekt = 1.22.0-RC2",
            "compileSdk = 32",
            "minSdk = 21",
            "targetSdk = 31",
            "javaVersion = VERSION_11",
            "javaTarget = 11",
            "compose-bom = 2022.10.00",
            "firebase-bom = 29.3.0",
            "accompanist = 0.27.0",
            "androidxlifecycle = 2.4.0",
            "androidxtest = 1.4.0",
            "coil = 2.2.2",
            "composecompiler = 1.2.0",
            "coroutines = 1.6.4",
            "okhttp = 4.10.0",
            "retrofit = 2.9.0",
            "room = 2.4.2",
            "play-services = 18.0.1",
            "koin = 3.1.4",
            "navigation = 2.5.0",
            "camera = 1.1.0",
            "glide = 4.12.0",
            "permissions-dispatcher = 4.9.1",
            "junit-jupiter = 5.6.2"
        )

        var outputVersions = ""
        listVersions.forEach { version ->
            val versionSplit = version.split("=")
            outputVersions += "version(\"${versionSplit[0].trim()}\", \"${versionSplit[1].trim()}\")\n"
        }
        print(outputVersions)
    }

    @Test
    fun replacePlugins() {
        val listPlugins = listOf(
            "android-application = { id = \"com.android.application\", version.ref = \"agp\" }",
            "android-library = { id = \"com.android.library\", version.ref = \"agp\" }",
            "android-lint = { id = \"com.android.lint\", version.ref = \"agp\" }",
            "android-test = { id = \"com.android.test\", version.ref = \"agp\" }",
            "gms-googleServices = \"com.google.gms.google-services:4.3.14\"",
            "kotlin = { id = \"org.jetbrains.kotlin.jvm\", version.ref = \"kotlin\" }",
            "kotlin-android = { id = \"org.jetbrains.kotlin.android\", version.ref = \"kotlin\" }",
            "kotlin-kapt = { id = \"org.jetbrains.kotlin.kapt\", version.ref = \"kotlin\" }",
            "kotlin-parcelize = { id = \"org.jetbrains.kotlin.plugin.parcelize\", version.ref = \"kotlin\" }",
            "navigation-safeargs = { id = \"androidx.navigation.safeargs\", version.ref = \"navigation\" }",
            "navigation-safeargs-kotlin = { id = \"androidx.navigation.safeargs.kotlin\", version.ref = \"navigation\" }",
            "firebase-perfPlugin = \"com.google.firebase.firebase-perf:1.4.1\"",
            "firebase-appdistribution = \"com.google.firebase.appdistribution:3.1.0\"",
            "firebase-crashlytics = \"com.google.firebase.crashlytics:2.9.2\"",
            "benManes-version = \"com.github.ben-manes.versions:0.41.0\"",
            "detekt = { id = \"io.gitlab.arturbosch.detekt\", version.ref = \"gradle-detekt\" }",
            "ktlint = { id = \"org.jlleitschuh.gradle.ktlint\", version.ref = \"gradle-ktlint\" }"
        )

        var outputPlugins = ""
        listPlugins.forEach { version ->
            val versionSplit = version.split("=")
            outputPlugins += if (versionSplit.size == 4)
                replacePluginFourPiece(versionSplit)
            else replacePluginTwoPiece(versionSplit)
        }
        print(outputPlugins)
    }

    private fun replacePluginFourPiece(splitted: List<String>): String {
        return "plugin(\"${splitted[0].trim()}\"," +
            "${splitted[2].trim().replace(", version.ref", "")}).versionRef(" +
            "${splitted[3].trim().replace(" }", "")})\n"
    }

    private fun replacePluginTwoPiece(splitted: List<String>): String {
        val splliterGroupArtifact = divideGroupAndArtifactPlugin(splitted[1].trim())
        return "plugin(\"${splitted[0].trim()}\", $splliterGroupArtifact)\n"
    }

    private fun divideGroupAndArtifactPlugin(mergedGroupArtifact: String): String {
        val split = mergedGroupArtifact.split(":")
        return "${split[0]}\").version(\"${split[1]}"
    }

    @Test
    fun replaceLibraries() {
        val listOfLib = listOf(
            "cora-address = { module = \"br.com.cora:address\", version.ref = \"coraAddressBaseVersion\" }",
            "cora-analytics = { module = \"br.com.cora:analytics\", version.ref = \"coraAnalyticsBaseVersion\" }",
            "cora-auth = { module = \"br.com.cora:auth\", version.ref = \"coraAuthBaseVersion\" }",
            "cora-camera = { module = \"br.com.cora:camera\", version.ref = \"coraCameraBaseVersion\" }",
            "cora-chat = { module = \"br.com.cora:chat\", version.ref = \"coraChatBaseVersion\" }",
            "cora-common = { module = \"br.com.cora:common\", version.ref = \"coraCommonVersion\" }",
            "cora-common-kotlin = { module = \"br.com.cora.common:kotlin\", version.ref = \"coraCommonKotlinVersion\" }",
            "cora-common-rest = { module = \"br.com.cora:common-rest\", version.ref = \"coraCommonRestVersion\" }",
            "cora-common-rest-kotlin = { module = \"br.com.cora.common-rest:kotlin\", version.ref = \"coraCommonRestKotlinVersion\" }",
            "cora-common-test = { module = \"br.com.cora.common:common-test\", version.ref = \"coraCommonTestVersion\" }",
            "cora-common-viewmodel = { module = \"br.com.cora.common:viewmodel\", version.ref = \"coraCommonViewModelVersion\" }",
            "cora-design-compose = { module = \"br.com.cora:design-compose\", version.ref = \"coraComposeVersion\" }",
            "cora-design = { module = \"br.com.cora:design\", version.ref = \"coraDesignBaseVersion\" }",
            "cora-fraud = { module = \"br.com.cora:fraud\", version.ref = \"coraFraudBaseVersion\" }",
            "cora-growth-release = { module = \"br.com.cora:growth\", version.ref = \"coraGrowthReleaseVersion\" }",
            "cora-growth-stage = { module = \"br.com.cora:growth\", version.ref = \"coraGrowthStageVersion\" }",
            "cora-navigation-release = { module = \"br.com.cora:navigation\", version.ref = \"coraNavigationReleaseVersion\" }",
            "cora-navigation-stage = { module = \"br.com.cora:navigation\", version.ref = \"coraNavigationStageVersion\" }",
            "koin-android = { module = \"io.insert-koin:koin-android\", version.ref = \"koin\" }",
            "koin-junit = { module = \"io.insert-koin:koin-test-junit5\", version.ref = \"koin\" }",
            "koin-test = { module = \"io.insert-koin:koin-test\", version.ref = \"koin\" }",
            "kotlin-stdlib = { module = \"org.jetbrains.kotlin:kotlin-stdlib-jdk8\", version.ref = \"kotlin\" }",
            "androidx-activity-compose = \"androidx.activity:activity-compose:1.6.1\"",
            "androidx-annotation = \"androidx.annotation:annotation:1.2.0\"",
            "androidx-appcompat = \"androidx.appcompat:appcompat:1.4.1\"",
            "androidx-archCoreTesting = \"androidx.arch.core:core-testing:2.1.0\"",
            "androidx-browser = \"androidx.browser:browser:1.4.0\"",
            "androidx-camera2 = { module = \"androidx.camera:camera-camera2\", version.ref = \"camera\" }",
            "androidx-cameraCore = { module = \"androidx.camera:camera-core\", version.ref = \"camera\" }",
            "androidx-cardView = \"androidx.cardview:cardview:1.0.0\"",
            "androidx-collection = \"androidx.collection:collection-ktx:1.2.0\"",
            "androidx-constraintLayout = \"androidx.constraintlayout:constraintlayout:2.1.1\"",
            "androidx-core = \"androidx.core:core-ktx:1.7.0\"",
            "androidx-installReferrer = \"com.android.installreferrer:installreferrer:2.2\"",
            "androidx-navigationFragment = { module = \"androidx.navigation:navigation-fragment-ktx\", version.ref = \"navigation\" }",
            "androidx-navigationUi = { module = \"androidx.navigation:navigation-ui-ktx\", version.ref = \"navigation\" }",
            "navigation-safeArgs-gradlePlugin = { module = \"androidx.navigation:navigation-safe-args-gradle-plugin\", version.ref = \"navigation\" }",
            "androidx-paging = \"androidx.paging:paging-runtime:3.1.1\"",
            "androidx-recyclerView = \"androidx.recyclerview:recyclerview:1.2.0\"",
            "androidx-test-espressoCore = \"androidx.test.espresso:espresso-core:3.2.0\"",
            "androidx-viewpager2 = \"androidx.viewpager2:viewpager2:1.0.0\"",
            "androidx-exifinterface = \"androidx.exifinterface:exifinterface:1.3.2\"",
            "androidx-dynamic-animation = \"androidx.dynamicanimation:dynamicanimation:1.0.0\"",
            "androidx-lifecycle-livedata-ktx = { module = \"androidx.lifecycle:lifecycle-livedata-ktx\", version.ref = \"androidxlifecycle\" }",
            "androidx-lifecycle-runtime-compose = { module = \"androidx.lifecycle:lifecycle-runtime-compose\", version.ref = \"androidxlifecycle\" }",
            "androidx-lifecycle-runtime-ktx = { module = \"androidx.lifecycle:lifecycle-runtime-ktx\", version.ref = \"androidxlifecycle\" }",
            "androidx-lifecycle-viewmodel-compose = { module = \"androidx.lifecycle:lifecycle-viewmodel-compose\", version.ref = \"androidxlifecycle\" }",
            "androidx-lifecycle-viewmodel-ktx = { module = \"androidx.lifecycle:lifecycle-viewmodel-ktx\", version.ref = \"androidxlifecycle\" }",
            "androidx-room-common = { module = \"androidx.room:room-common\", version.ref = \"room\" }",
            "androidx-room-compiler = { module = \"androidx.room:room-compiler\", version.ref = \"room\" }",
            "androidx-room-ktx = { module = \"androidx.room:room-ktx\", version.ref = \"room\" }",
            "androidx-room-paging = { module = \"androidx.room:room-paging\", version.ref = \"room\" }",
            "androidx-room-runtime = { module = \"androidx.room:room-runtime\", version.ref = \"room\" }",
            "androidx-room-testing = { module = \"androidx.room:room-testing\", version.ref = \"room\" }",
            "androidx-test-core = { module = \"androidx.test:core\", version.ref = \"androidxtest\" }",
            "androidx-test-junit = \"androidx.test.ext:junit-ktx:1.1.3\"",
            "androidx-test-rules = { module = \"androidx.test:rules\", version.ref = \"androidxtest\" }",
            "androidx-test-runner = { module = \"androidx.test:runner\", version.ref = \"androidxtest\" }",
            "compose-bom = { module = \"androidx.compose:compose-bom\", version.ref = \"compose-bom\" }",
            "compose-animation-animation = { module = \"androidx.compose.animation:animation\", version.ref = \"composecompiler\" }",
            "compose-foundation-foundation = { module = \"androidx.compose.foundation:foundation\", version.ref = \"composecompiler\" }",
            "compose-foundation-layout = { module = \"androidx.compose.foundation:foundation-layout\", version.ref = \"composecompiler\" }",
            "compose-material-iconsext = { module = \"androidx.compose.material:material-icons-extended\", version.ref = \"composecompiler\" }",
            "compose-material-material = { module = \"androidx.compose.material:material\", version.ref = \"composecompiler\" }",
            "compose-ui-test = { module = \"androidx.compose.ui:ui-test-junit4\", version.ref = \"composecompiler\" }",
            "compose-ui-tooling = { module = \"androidx.compose.ui:ui-tooling\", version.ref = \"composecompiler\" }",
            "compose-ui-ui = { module = \"androidx.compose.ui:ui\", version.ref = \"composecompiler\" }",
            "compose-ui-uitextfonts = { module = \"androidx.compose.ui:ui-text-google-fonts\", version.ref = \"composecompiler\" }",
            "compose-ui-util = { module = \"androidx.compose.ui:ui-util\", version.ref = \"composecompiler\" }",
            "compose-ui-viewbinding = { module = \"androidx.compose.ui:ui-viewbinding\", version.ref = \"composecompiler\" }",
            "compose-runtime = { module = \"androidx.compose.runtime:runtime\", version.ref = \"composecompiler\" }",
            "compose-activity = \"androidx.activity:activity-compose:1.3.1\"",
            "firebase-bom = { module = \"com.google.firebase:firebase-bom\", version.ref = \"firebase-bom\" }",
            "firebase-analytics = { module = \"com.google.firebase:firebase-analytics-ktx\" }",
            "firebase-app-check = \"com.google.firebase:firebase-appcheck-safetynet:16.0.0-beta05\"",
            "firebase-core = { module = \"com.google.firebase:firebase-core\" }",
            "firebase-crashlytics = { module = \"com.google.firebase:firebase-crashlytics-ktx\" }",
            "firebase-database = { module = \"com.google.firebase:firebase-database-ktx\" }",
            "firebase-messaging = { module = \"com.google.firebase:firebase-messaging-ktx\" }",
            "firebase-performance = { module = \"com.google.firebase:firebase-perf-ktx\" }",
            "kotlin-coroutines-android = { module = \"org.jetbrains.kotlinx:kotlinx-coroutines-android\", version.ref = \"coroutines\" }",
            "kotlin-coroutines-core = { module = \"org.jetbrains.kotlinx:kotlinx-coroutines-core\", version.ref = \"coroutines\" }",
            "kotlin-coroutines-test = { module = \"org.jetbrains.kotlinx:kotlinx-coroutines-test\", version.ref = \"coroutines\" }",
            "google-play-services-base = { module = \"com.google.android.gms:play-services-base\", version.ref = \"play-services\" }",
            "google-play-services-safetynet = { module = \"com.google.android.gms:play-services-safetynet\", version.ref = \"play-services\" }",
            "google-play-services-vision = \"com.google.android.gms:play-services-vision:20.1.2\"",
            "google-gson = \"com.google.code.gson:gson:2.8.6\"",
            "google-material = \"com.google.android.material:material:1.5.0\"",
            "google-play-core = \"com.google.android.play:core:1.10.2\"",
            "google-play-core-ktx = \"com.google.android.play:core-ktx:1.8.1\"",
            "google-truth = \"com.google.truth:truth:1.1\"",
            "google-owasp-sanitizer = \"com.googlecode.owasp-java-html-sanitizer:owasp-java-html-sanitizer:20200713.1\"",
            "balloon = \"com.github.skydoves:balloon:1.0.9\"",
            "barcode-reader = \"com.github.wpsaputra:Barcode-Reader:master-SNAPSHOT\"",
            "canarinho = \"com.github.concretesolutions:canarinho:2.0.2\"",
            "coil-coil = { module = \"io.coil-kt:coil\", version.ref = \"coil\" }",
            "coil-compose = { module = \"io.coil-kt:coil-compose\", version.ref = \"coil\" }",
            "facebook = \"com.facebook.android:facebook-core:12.1.0\"",
            "glide = { module = \"com.github.bumptech.glide:glide\", version.ref = \"glide\" }",
            "glide-compiler = { module = \"com.github.bumptech.glide:compiler\", version.ref = \"glide\" }",
            "junit4 = \"junit:junit:4.12\"",
            "junit-jupiter-api = { module = \"org.junit.jupiter:junit-jupiter-api\", version.ref = \"junit-jupiter\" }",
            "junit-jupiter-engine = { module = \"org.junit.jupiter:junit-jupiter-engine\", version.ref = \"junit-jupiter\" }",
            "keyboardvisibilityevent = \"net.yslibrary.keyboardvisibilityevent:keyboardvisibilityevent:2.3.0\"",
            "lottie = \"com.airbnb.android:lottie:5.2.0\"",
            "mixpanel = \"com.mixpanel.android:mixpanel-android:6.0.0\"",
            "mockK = \"io.mockk:mockk:1.13.2\"",
            "mockK-agent-jvm = \"io.mockk:mockk-agent-jvm:1.12.3\"",
            "okhttp-loggingInterceptor = { module = \"com.squareup.okhttp3:logging-interceptor\", version.ref = \"okhttp\" }",
            "okhttp-okhttp = { module = \"com.squareup.okhttp3:okhttp\", version.ref = \"okhttp\" }",
            "okio = \"com.squareup.okio:okio:2.10.0\"",
            "permissions-dispatcher = { module = \"com.github.permissions-dispatcher:permissionsdispatcher\", version.ref = \"permissions-dispatcher\" }",
            "permissions-dispatcher-processor = { module = \"com.github.permissions-dispatcher:permissionsdispatcher-processor\", version.ref = \"permissions-dispatcher\" }",
            "retrofit = { module = \"com.squareup.retrofit2:retrofit\", version.ref = \"retrofit\" }",
            "retrofit-gsonConverter = { module = \"com.squareup.retrofit2:converter-gson\", version.ref = \"retrofit\" }",
            "retrofit-mockWebServer = \"com.squareup.okhttp3:mockwebserver:4.10.0\"",
            "rx-android = \"io.reactivex.rxjava2:rxandroid:2.1.1\"",
            "rx-java = \"io.reactivex.rxjava2:rxjava:2.2.9\"",
            "timber = \"com.jakewharton.timber:timber:5.0.1\"",
            "viewpagerdotsindicator = \"com.tbuonomo.andrui:viewpagerdotsindicator:4.1.2\"",
            "zxing = \"com.google.zxing:core:3.3.3\"",
            "zxing-android-embedded = \"com.journeyapps:zxing-android-embedded:4.2.0\"",
            "turbine = \"app.cash.turbine:turbine:0.9.0\"",
        )

        val listOther = listOf(
            "google-accompanist-systemuicontroller = { module = \"com.google.accompanist:accompanist-systemuicontroller\", version.ref = \"accompanist\" }",
            "google-accompanist-insets = { module = \"com.google.accompanist:accompanist-insets\", version.ref = \"accompanist\" }"
        )

        var outputLibs = ""
        listOther.forEach { version ->
            val versionSplit = version.split("=")
            outputLibs += when (versionSplit.size) {
                4 -> replaceLibraryFourPiece(versionSplit)
                3 -> replaceLibraryThreePiece(versionSplit)
                else -> replaceLibraryTwoPiece(versionSplit)
            }
        }
        print(outputLibs)
    }

    private fun replaceLibraryFourPiece(splitted: List<String>): String {
        val splittedGroupArtifact =
            divideGroupAndArtifactLibrary(splitted[2].trim().replace(", version.ref", ""), false)
        return "library(\"${splitted[0].trim()}\"," +
            "$splittedGroupArtifact).versionRef(" +
            "${splitted[3].trim().replace(" }", "")})\n"
    }

    private fun replaceLibraryThreePiece(splitted: List<String>): String {
        val splittedGroupArtifact =
            divideGroupAndArtifactLibrary(splitted[2].trim().replace(" }", ""), true)
        return "library(\"${splitted[0].trim()}\"," +
            "$splittedGroupArtifact)\n"
    }

    private fun replaceLibraryTwoPiece(splitted: List<String>): String {
        val splittedGroupArtifact = divideGroupAndArtifactLibrary(splitted[1].trim(), false)
        return "library(\"${splitted[0].trim()}\",$splittedGroupArtifact)\n"
    }

    private fun divideGroupAndArtifactLibrary(
        mergedGroupArtifact: String,
        fromThreePiece: Boolean
    ): String {
        val split = mergedGroupArtifact.split(":")
        return if (split.size == 3) {
            "${split[0]}\",\"${split[1]}\").version(\"${split[2]}"
        } else if (fromThreePiece) {
            "${split[0]}\",\"${split[1]}).withoutVersion("
        } else {
            "${split[0]}\",\"${split[1]}"
        }
    }
}
