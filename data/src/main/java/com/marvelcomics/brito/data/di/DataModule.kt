package com.marvelcomics.brito.data.di

import com.marvelcomics.brito.data.datasource.remote.mapper.CharacterMapper
import com.marvelcomics.brito.data.datasource.remote.mapper.ComicMapper
import com.marvelcomics.brito.data.datasource.remote.mapper.SeriesMapper
import com.marvelcomics.brito.data.datasource.remote.mapper.ThumbnailMapper
import com.marvelcomics.brito.data.okhttp.KeyHashInterceptor
import com.marvelcomics.brito.data.repository.characters.CharacterRepository
import com.marvelcomics.brito.data.repository.comics.ComicRepository
import com.marvelcomics.brito.data.repository.series.SeriesRepository
import com.marvelcomics.brito.data.webservice.MarvelWebService
import com.marvelcomics.brito.data.webservice.MarvelWebServiceImpl
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import com.marvelcomics.brito.domain.repository.IComicRepository
import com.marvelcomics.brito.domain.repository.ISeriesRepository
import okhttp3.Interceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DataModule {

    object Interceptors {
        const val KEY_HASH = "KeyHashInterceptor"
    }

    val interceptors = module {
        factory<Interceptor>(named(Interceptors.KEY_HASH)) { KeyHashInterceptor() }
    }

    val repositories = module {
        factory<ICharacterRepository> { CharacterRepository(get(), get()) }
        factory<IComicRepository> { ComicRepository(get(), get()) }
        factory<ISeriesRepository> { SeriesRepository(get(), get()) }
    }

    val webservices = module {
        single<MarvelWebService> {
            MarvelWebServiceImpl(
                MarvelWebService.BASE_URL, get(named(Interceptors.KEY_HASH))
            )
        }
    }

    val mappers = module {
        factory { ThumbnailMapper() }
        factory { CharacterMapper(get()) }
        factory { SeriesMapper(get()) }
        factory { ComicMapper(get()) }
    }
}