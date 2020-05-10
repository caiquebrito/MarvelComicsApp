package com.marvelcomics.brito.di

import com.marvelcomics.brito.data.repository.characters.CharacterRepository
import com.marvelcomics.brito.data.repository.characters.CharacterRepositoryImpl
import com.marvelcomics.brito.data.repository.comics.ComicRepository
import com.marvelcomics.brito.data.repository.comics.ComicRepositoryImpl
import com.marvelcomics.brito.data.repository.series.SeriesRepository
import com.marvelcomics.brito.data.repository.series.SeriesRepositoryImpl
import org.koin.dsl.module

object RepositoryModule {
    val repositories = module {
        factory<CharacterRepository> { CharacterRepositoryImpl(get()) }
        factory<ComicRepository> { ComicRepositoryImpl(get()) }
        factory<SeriesRepository> { SeriesRepositoryImpl(get()) }
    }
}