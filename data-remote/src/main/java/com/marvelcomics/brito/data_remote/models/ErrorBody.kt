package com.marvelcomics.brito.data_remote.models

import com.google.gson.annotations.SerializedName

data class ErrorBody(
    @SerializedName("http_status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: String
)
