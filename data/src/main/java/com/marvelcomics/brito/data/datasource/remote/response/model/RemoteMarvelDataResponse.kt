package com.marvelcomics.brito.data.datasource.remote.response.model

import com.google.gson.annotations.SerializedName

class RemoteMarvelDataResponse<Type> {
    @SerializedName("offset")
    private val offset = 0
    @SerializedName("limit")
    private val limit = 0
    @SerializedName("total")
    private val total = 0
    @SerializedName("count")
    private val count = 0
    @SerializedName("results")
    val results: List<Type>? = null
}
