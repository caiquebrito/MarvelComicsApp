package com.marvelcomics.brito.di

import com.marvelcomics.brito.data_remote.datasource.mapper.CharacterMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.ComicMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.SeriesMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.ThumbnailMapper
import com.marvelcomics.brito.data_remote.okhttp.KeyHashInterceptor
import com.marvelcomics.brito.data_remote.repository.characters.CharacterRepository
import com.marvelcomics.brito.data_remote.repository.comics.ComicRepository
import com.marvelcomics.brito.data_remote.repository.series.SeriesRepository
import com.marvelcomics.brito.data_remote.api.MarvelAPI
import com.marvelcomics.brito.data_remote.webservice.MarvelAPIImpl
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
        factory<Interceptor>(named(Interceptors.KEY_HASH)) { KeyHashInterceptor(pubKey, prvKey) }
    }

    val repositories = module {
        factory<ICharacterRepository> { CharacterRepository(get(), get()) }
        factory<IComicRepository> { ComicRepository(get(), get()) }
        factory<ISeriesRepository> { SeriesRepository(get(), get()) }
    }

    val webservices = module {
        single<MarvelAPI> {
            MarvelAPIImpl(
                MarvelAPI.BASE_URL, get(named(Interceptors.KEY_HASH))
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

private const val pubKey = "e2d714ad0eec8dd9251fb6e340d1d20d"
private const val prvKey = "a8265201786d1e2ba7888cc2a3f7aaae22efd3ea"
