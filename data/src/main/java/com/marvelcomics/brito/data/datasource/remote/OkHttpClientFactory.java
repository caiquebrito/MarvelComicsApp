package com.marvelcomics.brito.data.datasource.remote;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class OkHttpClientFactory {

    public static OkHttpClient create(Interceptor interceptor) {
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }
}
