package com.marvelcomics.brito.data_remote.extensions

import com.marvelcomics.brito.data_remote.exception.NullBodyException
import retrofit2.HttpException
import retrofit2.Response

/**
 * A Extensions based on Retrofit [Response], it will check if is successfully and extract bodyResponse
 * if cannot extract the bodyResponse, it will throw a [NullBodyException]
 * If the response is not a 2XX response, it will throw a [HttpException]
 * If something else throws, it will return it to upper levels
 * */
inline fun <reified T> Response<T>.getBodyOrThrow(): T =
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
    } catch (throwable: Throwable) {
        throw throwable
    }

/**
 *
 * Extension used in 204 requests, returning a boolean when is successful
 *
 * Sample:
 * fun getSomethingOfApi() = handleApi {
 *     api.get204Request().orThrow()
 * }
 *
 * */
fun Response<Void>.orThrow() =
    if (this.isSuccessful)
        true
    else
        throw HttpException(this)
