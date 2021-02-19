package com.marvelcomics.brito.data.okhttp

import com.marvelcomics.brito.domain.ResultWrapper
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is UnknownHostException, is SocketException, is SocketTimeoutException -> {
                ResultWrapper.NetworkError
            }
            is HttpException -> {
                val code = throwable.code()
                ResultWrapper.Failure(code, throwable)
            }
            else -> {
                ResultWrapper.Failure(0, Throwable("Null else branch"))
            }
        }
    }
}