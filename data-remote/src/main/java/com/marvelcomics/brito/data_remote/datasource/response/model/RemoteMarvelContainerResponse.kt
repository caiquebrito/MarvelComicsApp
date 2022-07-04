package com.marvelcomics.brito.data_remote.datasource.response.model

import com.google.gson.annotations.SerializedName

class RemoteMarvelContainerResponse<Type> {
    @SerializedName("code")
    private val code = 0
    @SerializedName("status")
    private val status: String? = null
    @SerializedName("copyright")
    private val copyright: String? = null
    @SerializedName("attributionText")
    private val attributionText: String? = null
    @SerializedName("attributionHTML")
    private val attributionHTML: String? = null
    @SerializedName("etag")
    private val etag: String? = null
    @SerializedName("data")
    val remoteMarvelDataResponse: RemoteMarvelDataResponse<Type>? = null
}
