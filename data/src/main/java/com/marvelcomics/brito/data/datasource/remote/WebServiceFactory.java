package com.marvelcomics.brito.data.datasource.remote;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceFactory {

    public static MarvelWebService create(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(MarvelWebService.BASE_URL)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarvelWebService.class);
    }
}
