package com.marvelcomics.brito.data_remote.exception

class ErrorHandlingNullException : Exception()
class MarvelApiException(
    val httpCode: Int,
    val marvelCode: String,
    description: String?,
    cause: Throwable? = null
) : Exception(description, cause) {
    constructor(message: String?, cause: Throwable? = null) : this(-1, "", message, cause)
}
class MarvelMapperException(description: String?, cause: Throwable?) :
    Exception(description, cause)
class NullBodyException : Exception()
class ErrorBodyException(
    val httpCode: Int,
    val coraCode: String,
    override val message: String,
    throwable: Throwable
) : Exception(message, throwable)
class ErrorBodyParseException(exception: Exception) : Exception(exception)
