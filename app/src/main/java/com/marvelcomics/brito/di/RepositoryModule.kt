package com.marvelcomics.brito.di

import com.marvelcomics.brito.data.repository.characters.CharacterRepository
import com.marvelcomics.brito.data.ktor.ComicKRepository
import com.marvelcomics.brito.data.repository.comics.ComicRepository
import com.marvelcomics.brito.data.repository.series.SeriesRepository
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import com.marvelcomics.brito.domain.ktor.IComicKRepository
import com.marvelcomics.brito.domain.repository.IComicRepository
import com.marvelcomics.brito.domain.repository.ISeriesRepository
import org.koin.dsl.module

object RepositoryModule {
    val repositories = module {
        factory<ICharacterRepository> { CharacterRepository(get(), get()) }
        factory<IComicRepository> { ComicRepository(get(), get()) }
        factory<IComicKRepository> { ComicKRepository(get(), get(), get()) }
        factory<ISeriesRepository> { SeriesRepository(get(), get()) }
    }
}
