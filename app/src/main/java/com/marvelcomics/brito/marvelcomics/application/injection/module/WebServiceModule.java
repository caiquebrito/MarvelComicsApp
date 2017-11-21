package com.marvelcomics.brito.marvelcomics.application.injection.module;

import com.marvelcomics.brito.data.datasource.remote.InterceptorFactory;
import com.marvelcomics.brito.data.datasource.remote.MarvelWebService;
import com.marvelcomics.brito.data.datasource.remote.OkHttpClientFactory;
import com.marvelcomics.brito.data.datasource.remote.WebServiceFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@Module
public class WebServiceModule {

    @Provides
    @Singleton
    static MarvelWebService provideWebService(OkHttpClient httpClient) {
        return WebServiceFactory.create(httpClient);
    }

    @Provides
    @Singleton
    static OkHttpClient provideHttpClient(Interceptor interceptor) {
        return OkHttpClientFactory.create(interceptor);
    }

    @Provides
    @Singleton
    static Interceptor provideInterceptor() {
        return InterceptorFactory.create();
    }
}
