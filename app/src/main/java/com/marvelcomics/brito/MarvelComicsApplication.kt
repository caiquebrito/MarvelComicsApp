package com.marvelcomics.brito

import android.app.Application
import com.marvelcomics.brito.data.di.DataModule
import com.marvelcomics.brito.presentation.di.PresentationModules
import com.marvelcomics.brito.domain.di.DomainModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarvelComicsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MarvelComicsApplication)
            modules(
                listOf(
                    DomainModules.usesCases,
                    DataModule.interceptors,
                    DataModule.mappers,
                    DataModule.repositories,
                    DataModule.webservices,
                    PresentationModules.viewModels,
                )
            )
        }
    }
}
