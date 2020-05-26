package com.marvelcomics.brito.di

import com.marvelcomics.brito.data.datasource.remote.mapper.CharacterMapper
import com.marvelcomics.brito.data.datasource.remote.mapper.ComicMapper
import com.marvelcomics.brito.data.datasource.remote.mapper.SeriesMapper
import com.marvelcomics.brito.data.datasource.remote.mapper.ThumbnailMapper
import org.koin.dsl.module

object MapperModule {
    val mappers = module {
        factory { ThumbnailMapper() }
        factory { CharacterMapper(get()) }
        factory { SeriesMapper(get()) }
        factory { ComicMapper(get()) }
    }
}