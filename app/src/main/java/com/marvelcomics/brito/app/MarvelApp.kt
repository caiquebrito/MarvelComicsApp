package com.marvelcomics.brito.app

import android.app.Application
import android.content.Context
import com.marvelcomics.brito.di.Marvel
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

@InternalCoroutinesApi
class MarvelApp : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        startKoin {
            androidContext(base)
            logger(EmptyLogger())
        }
    }

    override fun onCreate() {
        super.onCreate()
        Marvel.init("https://gateway.marvel.com/v1/public/")
    }
}
