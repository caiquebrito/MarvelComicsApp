package com.marvelcomics.brito.data_remote

import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkHttpClientFactory {
    fun createHttpClient(vararg interceptors: Interceptor): OkHttpClient {
        val certificatePinner = CertificatePinner.Builder().add(
            "marvel.com",
            "sha256/79127babc1d3625ae552c54b34fdb53217137cefbce66f506bc9831cac6b3c4f"
        ).build()

        val okHttpClient = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        for (interceptor in interceptors) {
            okHttpClient.addInterceptor(interceptor)
        }

        return okHttpClient.build()
    }
}
