package com.marvelcomics.brito

import android.app.Application
import com.marvelcomics.brito.di.DataModule
import com.marvelcomics.brito.di.DomainModules
import com.marvelcomics.brito.presentation.di.PresentationModules
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

@InternalCoroutinesApi
class MarvelComicsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MarvelComicsApplication)
            modules(
                listOf(
                    com.marvelcomics.brito.di.DomainModules.usesCases,
                    com.marvelcomics.brito.di.DataModule.interceptors,
                    com.marvelcomics.brito.di.DataModule.mappers,
                    com.marvelcomics.brito.di.DataModule.repositories,
                    com.marvelcomics.brito.di.DataModule.webservices,
                    PresentationModules.viewModels,
                )
            )
            logger(EmptyLogger())
        }
    }
}
