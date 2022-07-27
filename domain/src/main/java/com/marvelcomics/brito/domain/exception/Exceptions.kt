package com.marvelcomics.brito.domain.exception

import kotlin.Exception

class EmptyInputException : Throwable()
class InvalidTransactionCodeException : Throwable()
class LockedTransactionCodeException : Throwable()
class NotFound404Exception(override val message: String? = "") : Throwable()
class UnknownException(override val message: String? = "") : Throwable()
class NetworkException : Exception()
class ErrorBodyException(
    val httpCode: Int,
    val coraCode: String,
    override val message: String,
    cause: Throwable
) : Exception(message, cause)
class ErrorHandlingNullException : Exception()