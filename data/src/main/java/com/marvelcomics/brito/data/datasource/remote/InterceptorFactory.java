package com.marvelcomics.brito.data.datasource.remote;

import com.marvelcomics.brito.data.BuildConfig;
import com.marvelcomics.brito.data.datasource.remote.utils.AuthHashGenerator;
import com.marvelcomics.brito.data.datasource.remote.utils.TimeProvider;
import com.marvelcomics.brito.infrastructure.exception.MarvelApiException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

public class InterceptorFactory {

    private static final String TIMESTAMP_KEY = "ts";
    private static final String HASH_KEY = "hash";
    private static final String APIKEY_KEY = "apikey";

    public static Interceptor create() {
        return chain -> {
            String timestamp = String.valueOf(new TimeProvider().currentTimeMillis());
            String hash = null;
            //As chaves estão no BuildConfig do gradle de "data"
            String publicKey = BuildConfig.MARVEL_API_PUB_KEY;
            String privateKey = BuildConfig.MARVEL_API_PRI_KEY;
            try {
                hash = new AuthHashGenerator().generateHash(timestamp, publicKey, privateKey);
            } catch (MarvelApiException e) {
                e.printStackTrace();
            }
            Request request = chain.request();
            HttpUrl url = request.url()
                    .newBuilder()
                    .addQueryParameter(TIMESTAMP_KEY, timestamp)
                    .addQueryParameter(APIKEY_KEY, publicKey)
                    .addQueryParameter(HASH_KEY, hash)
                    .build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        };
    }
}
