package com.marvelcomics.brito.data.ktor.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RemoteMarvelContainerK<Type> {
    @SerialName(value = "code")
    private val code = 0

    @SerialName(value = "status")
    private val status: String? = null

    @SerialName(value = "copyright")
    private val copyright: String? = null

    @SerialName(value = "attributionText")
    private val attributionText: String? = null

    @SerialName(value = "attributionHTML")
    private val attributionHTML: String? = null

    @SerialName(value = "etag")
    private val etag: String? = null

    @SerialName(value = "data")
    val remoteMarvelData: RemoteMarvelDataK<Type>? = null
}