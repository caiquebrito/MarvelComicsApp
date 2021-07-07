package com.marvelcomics.brito.data.ktor.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RemoteMarvelDataK<Type> {
    @SerialName(value = "offset")
    private val offset = 0

    @SerialName(value = "limit")
    private val limit = 0

    @SerialName(value = "total")
    private val total = 0

    @SerialName(value = "count")
    private val count = 0

    @SerialName(value = "results")
    val results: List<Type>? = null
}
