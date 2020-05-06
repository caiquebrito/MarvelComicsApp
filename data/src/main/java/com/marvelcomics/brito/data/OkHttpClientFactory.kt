package com.marvelcomics.brito.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientFactory {
    fun createHttpClient(vararg interceptors: Interceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        val logginInterceptor = HttpLoggingInterceptor()
        logginInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(logginInterceptor)

        for (interceptor in interceptors) {
            okHttpClient.addInterceptor(interceptor)
        }

        return okHttpClient.build()
    }
}