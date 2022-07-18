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
import com.marvelcomics.brito.domain.usecase.Character
import com.marvelcomics.brito.domain.usecase.Comic
import com.marvelcomics.brito.domain.usecase.LoadLastCharacter
import com.marvelcomics.brito.domain.usecase.SaveCharacter
import com.marvelcomics.brito.domain.usecase.Series
import com.marvelcomics.brito.presentation.character.CharacterViewModel
import com.marvelcomics.brito.presentation.comic.ComicViewModel
import com.marvelcomics.brito.presentation.series.SeriesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

@InternalCoroutinesApi
class MarvelModules {

    object Data {
        private val pubKey = "9294302a561e7a8a489807700c2b56a9"
        private val prvKey = "cfe96dfbecfd6158c4983507a65ee0edd1f99782"

        object Interceptors {
            const val KEY_HASH = "KeyHashInterceptor"
            const val LOGGING = "LoggingInterceptor"
        }

        val interceptors = module {
            factory<Interceptor>(named(Interceptors.KEY_HASH)) {
                KeyHashInterceptor(
                    pubKey,
                    prvKey
                )
            }
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

    object Presentation {
        val viewModels = module {
            viewModel { CharacterViewModel(get(), get(), get(), null) }
            viewModel { ComicViewModel(get()) }
            viewModel { SeriesViewModel(get()) }
        }
    }

    object Domain {
        val usesCases = module {
            factory { Character(get(), Dispatchers.IO) }
            factory { Comic(get(), Dispatchers.IO) }
            factory { Series(get(), Dispatchers.IO) }
            factory { LoadLastCharacter(get(), Dispatchers.IO) }
            factory { SaveCharacter(get(), Dispatchers.IO) }
        }
    }
}