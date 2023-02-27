package com.marvelcomics.brito.dataremote.okhttp

import com.marvelcomics.brito.dataremote.datasource.utils.AuthHashGenerator
import com.marvelcomics.brito.dataremote.datasource.utils.TimeProvider
import com.marvelcomics.brito.dataremote.exception.MarvelApiException
import okhttp3.Interceptor
import okhttp3.Response

class KeyHashInterceptor(
    private val publicKey: String,
    private val privateKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val timestamp = TimeProvider().currentTimeMillis().toString()
        var hash: String? = null
        try {
            hash = AuthHashGenerator().generateHash(timestamp, publicKey, privateKey)
        } catch (e: MarvelApiException) {
            e.printStackTrace()
        }
        var request = chain.request()
        val url = request.url()
            .newBuilder()
            .addQueryParameter(TIMESTAMP_KEY, timestamp)
            .addQueryParameter(APIKEY_KEY, publicKey)
            .addQueryParameter(HASH_KEY, hash)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    companion object {
        const val TIMESTAMP_KEY = "ts"
        const val HASH_KEY = "hash"
        const val APIKEY_KEY = "apikey"
    }
}
