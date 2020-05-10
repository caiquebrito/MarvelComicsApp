package com.marvelcomics.brito.di

import com.marvelcomics.brito.data.okhttp.KeyHashInterceptor
import okhttp3.Interceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module

object InterceptorModule {
    val interceptors = module {
        factory<Interceptor>(named(KoinIndetifier.Interceptors.KEY_HASH)) { KeyHashInterceptor() }
    }
}