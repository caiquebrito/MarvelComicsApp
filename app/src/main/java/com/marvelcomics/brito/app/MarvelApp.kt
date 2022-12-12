package com.marvelcomics.brito.app

import android.app.Application
import com.marvelcomics.brito.di.Marvel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class MarvelApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Marvel.init("https://gateway.marvel.com/v1/public/")
    }
}
