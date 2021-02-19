package com.marvelcomics.brito.data

import com.marvelcomics.brito.data.webservice.MarvelWebService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebServiceFactory {
    internal fun createWebService(httpClient: OkHttpClient, baseUrl: String): MarvelWebService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelWebService::class.java)
    }
}
