package com.marvelcomics.brito.data

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> serialize(data: T): String {
    return Json {
        encodeDefaults = true
    }.encodeToString(data)
}

inline fun <reified T> deserialize(data: String): T {
    return Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }.decodeFromString(data)
}
