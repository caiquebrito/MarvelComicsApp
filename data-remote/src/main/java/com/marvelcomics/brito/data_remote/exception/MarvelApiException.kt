package com.marvelcomics.brito.data_remote.exception

class MarvelApiException(
    val httpCode: Int,
    val marvelCode: String,
    description: String?,
    cause: Throwable? = null
) : Exception(description, cause) {
    constructor(message: String?, cause: Throwable? = null) : this(-1, "", message, cause)
}
