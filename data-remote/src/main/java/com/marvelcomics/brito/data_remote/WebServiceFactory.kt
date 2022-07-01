package com.marvelcomics.brito.data_remote

import com.marvelcomics.brito.data_remote.api.MarvelAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebServiceFactory {
    internal fun createWebService(httpClient: OkHttpClient, baseUrl: String): MarvelAPI {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelAPI::class.java)
    }
}
