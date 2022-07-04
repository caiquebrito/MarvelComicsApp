package com.marvelcomics.brito.di

import com.marvelcomics.brito.data.local.MarvelLocalDataSource
import com.marvelcomics.brito.data.remote.MarvelRemoteDataSource
import com.marvelcomics.brito.data.repo.MarvelRepo
import com.marvelcomics.brito.data_local.MarvelLocalRepository
import com.marvelcomics.brito.data_remote.api.MarvelAPI
import com.marvelcomics.brito.data_remote.api.MarvelAPIImpl
import com.marvelcomics.brito.data_remote.datasource.mapper.CharacterMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.ComicMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.SeriesMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.ThumbnailMapper
import com.marvelcomics.brito.data_remote.okhttp.KeyHashInterceptor
import com.marvelcomics.brito.data_remote.repository.MarvelRemoteRepository
import com.marvelcomics.brito.domain.repository.MarvelRepository
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DataModule {

    object Interceptors {
        const val KEY_HASH = "KeyHashInterceptor"
        const val LOGGING = "LoggingInterceptor"
    }

    val interceptors = module {
        factory<Interceptor>(named(Interceptors.KEY_HASH)) { KeyHashInterceptor(pubKey, prvKey) }
        factory<Interceptor>(named(Interceptors.LOGGING)) {
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        }
    }

    val repositories = module {
        single<MarvelRemoteDataSource> { MarvelRemoteRepository(get(), get(), get(), get()) }
        single<MarvelLocalDataSource> { MarvelLocalRepository(get()) }
        single<MarvelRepository> { MarvelRepo(get(), get()) }
    }

    val api = module {
        single<MarvelAPI> {
            MarvelAPIImpl(
                MarvelAPI.BASE_URL,
                get(named(Interceptors.KEY_HASH)),
                get(named(Interceptors.LOGGING))
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
