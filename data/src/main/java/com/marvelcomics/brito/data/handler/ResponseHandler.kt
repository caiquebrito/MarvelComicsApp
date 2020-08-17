package com.marvelcomics.brito.data.handler

import retrofit2.HttpException
import java.net.SocketTimeoutException

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): ResourceModel<T> {
        return ResourceModel.success(data)
    }

    fun <T : Any> handleException(e: Exception): ResourceModel<T> {
        return when (e) {
            is HttpException -> ResourceModel.error(e.code(), getErrorMessage(e.code()), null)
            is SocketTimeoutException -> ResourceModel.error(
                SOCKET_TIMEOUT,
                getErrorMessage(SOCKET_TIMEOUT),
                null
            )
            else -> ResourceModel.error(0, getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            SOCKET_TIMEOUT -> "Timeout"
            401 -> "Unauthorised"
            404 -> "Not found"
            else -> "Something went wrong"
        }
    }

    companion object {
        const val SOCKET_TIMEOUT = 999
    }
}