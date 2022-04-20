package com.marvelcomics.brito.domain.di

import com.marvelcomics.brito.domain.usecase.CharacterUseCase
import com.marvelcomics.brito.domain.usecase.ComicUseCase
import com.marvelcomics.brito.domain.usecase.SeriesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

object DomainModules {
    val usesCases = module {
        factory { CharacterUseCase(get(), Dispatchers.IO) }
        factory { ComicUseCase(get()) }
        factory { SeriesUseCase(get()) }
    }
}
