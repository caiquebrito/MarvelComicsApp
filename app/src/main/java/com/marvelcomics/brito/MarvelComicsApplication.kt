package com.marvelcomics.brito

import android.app.Application
import com.marvelcomics.brito.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

class MarvelComicsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MarvelComicsApplication)
            modules(
                listOf(
                    RepositoryModule.repositories,
                    InterceptorModule.interceptors,
                    WebServiceModule.webservices,
                    ViewModelModules.viewModels,
                    MapperModule.mappers
                )
            )
            logger(EmptyLogger())
        }
    }
}
