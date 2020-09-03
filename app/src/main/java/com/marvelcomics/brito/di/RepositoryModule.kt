package com.marvelcomics.brito.di

import com.marvelcomics.brito.data.repository.characters.CharacterRepository
import com.marvelcomics.brito.data.repository.comics.ComicRepository
import com.marvelcomics.brito.data.repository.series.SeriesRepository
import org.koin.dsl.module

object RepositoryModule {
    val repositories = module {
        factory<CharacterRepository> { CharacterRepository(get(), get()) }
        factory<ComicRepository> { ComicRepository(get(), get()) }
        factory<SeriesRepository> { SeriesRepository(get(), get()) }
    }
}