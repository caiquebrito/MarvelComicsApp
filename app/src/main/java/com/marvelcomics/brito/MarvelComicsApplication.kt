package com.marvelcomics.brito

import android.app.Application
import com.marvelcomics.brito.di.MarvelModules
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
                    MarvelModules.Data.database,
                    MarvelModules.Domain.usesCases,
                    MarvelModules.Data.interceptors,
                    MarvelModules.Data.mappers,
                    MarvelModules.Data.repositories,
                    MarvelModules.Data.api,
                    MarvelModules.Presentation.viewModels,
                )
            )
            logger(EmptyLogger())
        }
    }
}
