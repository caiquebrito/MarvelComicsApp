package com.marvelcomics.brito.data_remote.exception

import com.google.gson.annotations.SerializedName

data class ErrorBodyResponse(
    @SerializedName("http_status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: String
)
