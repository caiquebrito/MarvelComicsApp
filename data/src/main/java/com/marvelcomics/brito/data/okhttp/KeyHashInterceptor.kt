package com.marvelcomics.brito.data.okhttp

import com.marvelcomics.brito.data.BuildConfig
import com.marvelcomics.brito.data.datasource.remote.utils.AuthHashGenerator
import com.marvelcomics.brito.data.datasource.remote.utils.TimeProvider
import com.marvelcomics.brito.infrastructure.exception.MarvelApiException
import okhttp3.Interceptor
import okhttp3.Response

class KeyHashInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val timestamp = TimeProvider().currentTimeMillis().toString()
        var hash: String? = null
        val publicKey: String = BuildConfig.MARVEL_API_PUB_KEY
        val privateKey: String = BuildConfig.MARVEL_API_PRI_KEY
        try {
            hash = AuthHashGenerator().generateHash(timestamp, publicKey, privateKey)
        } catch (e: MarvelApiException) {
            e.printStackTrace()
        }
        var request = chain.request()
        val url = request.url
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