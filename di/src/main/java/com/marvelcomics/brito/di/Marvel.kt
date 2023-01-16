package com.marvelcomics.brito.di

import com.marvelcomics.brito.di.MarvelModules.injectFeature
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
object Marvel {
    fun init() = injectFeature()
}
