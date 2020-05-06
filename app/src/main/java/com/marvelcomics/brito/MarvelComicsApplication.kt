package com.marvelcomics.brito.marvelcomics

import android.app.Application
import com.marvelcomics.brito.marvelcomics.di.InterceptorModule
import com.marvelcomics.brito.marvelcomics.di.RepositoryModule
import com.marvelcomics.brito.marvelcomics.di.ViewModelModules
import com.marvelcomics.brito.marvelcomics.di.WebServiceModule
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
                    ViewModelModules.viewModels
                )
            )
            logger(EmptyLogger())
        }
    }
}