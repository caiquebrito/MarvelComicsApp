package com.marvelcomics.brito.data_remote

import com.google.gson.Gson
import com.marvelcomics.brito.data.models.RemoteWrapper
import com.marvelcomics.brito.data_remote.models.ErrorBody
import com.marvelcomics.brito.domain.exception.ErrorBodyException
import com.marvelcomics.brito.domain.exception.ErrorHandlingNullException
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.exception.UnknownException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlinx.coroutines.flow.Flow

suspend fun <T> handleApi(
    errorHandling: (suspend (errorBodyException: Exception) -> T)? = null,
    callHandling: suspend () -> T,
): T {
    return try {
        callHandling.invoke()
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException,
            is UnknownHostException,
            is SocketException,
            is SocketTimeoutException -> errorHandling?.invoke(NetworkException())
            is HttpException -> {
                val httpCode = throwable.code()
                val errorBody = throwable.response()?.errorBody()?.string()
                val gsonErrorBody = Gson().fromJson(
                    errorBody,
                    ErrorBody::class.java
                )
                val coraCode = gsonErrorBody.code
                val message = gsonErrorBody.message
                errorHandling?.invoke(ErrorBodyException(httpCode, coraCode, message, throwable))
            }
            else -> errorHandling?.invoke(Exception(throwable))
        }
    } ?: throw ErrorHandlingNullException()
}

fun <T> Response<T>.getBodyOrThrow(): T {
    try {
        if (this.isSuccessful) {
            body()?.let { body ->
                return body
            } ?: run {
                throw Exception("Body is null")
            }
        } else {
            throw HttpException(this)
        }
    } catch (exception: Exception) {
        throw exception
    }
}

suspend fun <T> Exception.treatByCode(
    mapCode: HashMap<String, Exception>
): T {
    with(this) {
        throw when (this) {
            is ErrorBodyException -> {
                throw if (mapCode.containsKey(coraCode)) {
                    mapCode[coraCode]!!
                } else {
                    Exception()
                }
            }
            else -> this
        }
    }
}

suspend fun <T> handleFlowApi(
    errorHandling: (suspend (errorBodyException: Exception) -> Flow<T>)? = null,
    callHandling: suspend () -> Flow<T>,
): Flow<T> {
    return try {
        callHandling.invoke()
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException,
            is UnknownHostException,
            is SocketException,
            is SocketTimeoutException -> errorHandling?.invoke(NetworkException())
            is HttpException -> {
                val httpCode = throwable.code()
                val errorBody = throwable.response()?.errorBody()?.string()
                val gsonErrorBody = Gson().fromJson(
                    errorBody,
                    ErrorBody::class.java
                )
                val coraCode = gsonErrorBody.code
                val message = gsonErrorBody.message
                errorHandling?.invoke(ErrorBodyException(httpCode, coraCode, message, throwable))
            }
            else -> errorHandling?.invoke(Exception(throwable))
        }
    } ?: throw ErrorHandlingNullException()
}