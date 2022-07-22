package com.marvelcomics.brito.data_remote

import com.google.gson.Gson
import com.marvelcomics.brito.data.models.RemoteWrapper
import com.marvelcomics.brito.data_remote.models.ErrorBody
import com.marvelcomics.brito.domain.exception.NetworkException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Response<T>.getBodyOrThrow(): T {
    try {
        if (this.isSuccessful) {
            body()?.let { body ->
                return body
            } ?: run {
                throw Exception("Body is null")
            }
        } else {
            throw FailureResponseException(this)
        }
    } catch (exception: Exception) {
        throw exception
    }
}

class FailureResponseException(response: Response<*>) : HttpException(response)

suspend fun <T> handleApi(
    apiCall: suspend () -> T
): RemoteWrapper<T> {
    return try {
        RemoteWrapper.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException,
            is UnknownHostException,
            is SocketException,
            is SocketTimeoutException -> RemoteWrapper.NetworkError
            is HttpException -> {
                val code = throwable.code()
                val errorBody = throwable.response()?.errorBody()?.string()
                val gsonErrorBody = Gson().fromJson(
                    errorBody,
                    ErrorBody::class.java
                )
                val message = gsonErrorBody.message
                RemoteWrapper.Error(code, message)
            }
            else -> RemoteWrapper.Error(0, throwable.message)
        }
    }
}

fun <T> RemoteWrapper<T>.extractFromWrapper(): T {
    return try {
        when (this) {
            is RemoteWrapper.Success -> {
                this.data
            }
            is RemoteWrapper.NetworkError -> {
                throw NetworkException()
            }
            is RemoteWrapper.Error -> {
                throw Exception(this.message)
            }
        }
    } catch (exception: Exception) {
        throw exception
    }
}
