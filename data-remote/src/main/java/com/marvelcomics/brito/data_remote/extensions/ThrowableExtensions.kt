package com.marvelcomics.brito.data_remote.extensions

import com.google.gson.Gson
import com.marvelcomics.brito.data_remote.exception.ErrorBodyException
import com.marvelcomics.brito.data_remote.exception.ErrorBodyParseException
import com.marvelcomics.brito.data_remote.exception.ErrorBodyResponse
import com.marvelcomics.brito.data_remote.exception.MappingCodeNotFound
import retrofit2.HttpException

/**
 * A extensions to receive a [Throwable] and check if the exception is a [HttpException]
 * after that it will try to parse to a custom Exception [ErrorBodyException]
 * if the throwable is not a typed [HttpException] it will thrown itself
 * if the parser does not succeeded, it will throw a [ErrorBodyParseException] containing inside the exception throws
 */
fun Throwable.parseHttpExceptionOrThrow(): Throwable {
    return when (this) {
        is HttpException -> {
            try {
                val httpCode = code()
                val errorBody = response()?.errorBody()?.string()

                val gsonErrorBodyResponse = Gson().fromJson(errorBody, ErrorBodyResponse::class.java)
                val coraCode = gsonErrorBodyResponse.code
                val message = gsonErrorBodyResponse.message

                ErrorBodyException(httpCode, coraCode, message, this)
            } catch (exception: Exception) {
                ErrorBodyParseException(exception)
            }
        }
        else -> this
    }
}

/**
 * Maps a given CoraHttpErrorCode (e.g. TEM-0001) to a custom Exception
 * to be handled in upper levels (e.g. ViewModel or UseCase).
 * If no mapping is passed it will use the [defaultMapping]
 * If the code mapping is not found, it will throw a [MappingCodeNotFound]
 * If the [Throwable] is not a [ErrorBodyException] it will thrown itself
 */
fun Throwable.mapCoraExceptionOrThrow(mapCode: Map<String, Throwable>? = null): Throwable {
    return when (this) {
        is ErrorBodyException -> {
            throw try {
                (
                    mapCode?.let {
                        it[coraCode]
                    } ?: defaultMapping[coraCode]
                    ) as Throwable
            } catch (throwable: Throwable) {
                MappingCodeNotFound()
            }
        }
        else -> this
    }
}

val defaultMapping = mapOf(
    "SMB-0012" to NullPointerException()
)
