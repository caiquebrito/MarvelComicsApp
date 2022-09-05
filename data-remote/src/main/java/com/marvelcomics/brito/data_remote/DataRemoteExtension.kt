package com.marvelcomics.brito.data_remote

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> handleApi(
    errorHandling: ((errorBodyException: Exception) -> T)? = null,
    callHandling: suspend () -> T,
): T {
    return try {
        callHandling.invoke()
    } catch (throwable: Throwable) {
        errorHandling?.invoke(Exception(throwable)) ?: throwable.handledByCommon()
    } ?: throw ErrorHandlingNullException()
}

fun <T> Response<T>.getBodyOrThrow(): T {
    try {
        if (this.isSuccessful) {
            body()?.let { body ->
                return body
            } ?: run {
                throw NullBodyException()
            }
        } else {
            throw HttpException(this)
        }
    } catch (exception: Exception) {
        throw exception
    }
}

fun <T> Exception.treatByCode(
    mapCode: HashMap<String, Exception>
): T {
    with(this) {
        throw when (this) {
            is ErrorBodyException -> {
                throw if (mapCode.containsKey(mappedCode)) {
                    mapCode[mappedCode]!!
                } else {
                    this
                }
            }
            else -> this
        }
    }
}

fun <T> Throwable.handledByCommon(): T {
    throw when (this) {
        is HttpException -> {
            val httpCode = this.code()
            val errorBody = this.response()?.errorBody()?.string()
            val gsonErrorBody = Gson().fromJson(
                errorBody,
                ErrorBody::class.java
            )
            val coraCode = gsonErrorBody.code
            val message = gsonErrorBody.message
            ErrorBodyException(httpCode, coraCode, message, this)
        }
        else -> {
            this
        }
    }
}

class NullBodyException : Exception()
class ErrorHandlingNullException : Exception()
class ErrorBodyException(
    val httpCode: Int,
    val mappedCode: String,
    override val message: String,
    cause: Throwable
) : Exception(message, cause)

data class ErrorBody(
    @SerializedName("http_status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: String
)
