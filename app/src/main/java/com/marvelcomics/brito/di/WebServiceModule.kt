package com.marvelcomics.brito.di

import com.marvelcomics.brito.data.webservice.MarvelWebService
import com.marvelcomics.brito.data.webservice.MarvelWebServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

object WebServiceModule {
    val webservices = module {
        single<MarvelWebService> {
            MarvelWebServiceImpl(
                MarvelWebService.BASE_URL, get(named(KoinIndetifier.Interceptors.KEY_HASH))
            )
        }
    }
}
