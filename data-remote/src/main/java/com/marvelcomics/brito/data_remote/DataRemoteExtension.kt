package com.marvelcomics.brito.data_remote

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import retrofit2.Response

inline fun <reified T> handleApi(
    noinline errorHandling: ((throwable: Throwable) -> T)? = null,
    callHandling: () -> T,
): T {
    return try {
        callHandling.invoke()
    } catch (throwable: Throwable) {
        errorHandling?.invoke(throwable) ?: throwable.handledByCommon()
    } ?: throw ErrorHandlingNullException()
}

inline fun <reified T> Response<T>.getBodyOrThrow(): T {
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

inline fun <reified T> Throwable.handleByCode(
    mapCode: HashMap<String, Exception>
): T {
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

inline fun <reified T> Throwable.handledByCommon(): T {
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
