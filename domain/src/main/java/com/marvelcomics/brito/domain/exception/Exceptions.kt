package com.marvelcomics.brito.domain.exception

class EmptyInputException : Throwable()
class InvalidTransactionCodeException : Throwable()
class LockedTransactionCodeException : Throwable()
class NotFound404Exception(override val message: String? = "") : Throwable()
class UnknownException(override val message: String? = "") : Throwable()
class NetworkException : Exception()
