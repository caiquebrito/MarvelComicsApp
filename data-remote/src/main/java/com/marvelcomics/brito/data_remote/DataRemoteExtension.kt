package com.marvelcomics.brito.data_remote

import com.google.gson.Gson
import com.marvelcomics.brito.data_remote.models.ErrorBody
import com.marvelcomics.brito.domain.models.ResultWrapper
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException
import retrofit2.Response

fun <T> Response<T>.getBodyOrThrow(): T {
    try {
        if (this.isSuccessful) {
            body()?.let { body ->
                return body
            } ?: run {
                throw Exception("Body is null")
            }
        } else {
            throw Exception("Not Successful call - ${this.code()}")
        }
    } catch (exception: Exception) {
        throw exception
    }
}

suspend fun <T> handleApi(
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException,
            is UnknownHostException,
            is SocketException,
            is SocketTimeoutException -> ResultWrapper.NetworkError
            is HttpException -> {
                val code = throwable.code()
                val errorBody = throwable.response()?.errorBody()?.string()
                val gsonErrorBody = Gson().fromJson(
                    errorBody,
                    ErrorBody::class.java
                )
                val message = gsonErrorBody.message
                ResultWrapper.Error(code, message)
            }
            else -> ResultWrapper.Error(0, throwable.message)
        }
    }
}