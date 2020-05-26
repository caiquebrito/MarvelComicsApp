package com.marvelcomics.brito.infrastructure.exception

class MarvelApiException(
    val httpCode: Int,
    val marvelCode: String,
    description: String?,
    cause: Throwable?
) : Exception(description, cause) {
    constructor(message: String?, cause: Throwable? = null) : this(-1, "", message, cause) {}
}