package com.marvelcomics.brito.di

import androidx.room.Room
import com.marvelcomics.brito.data.local.MarvelLocalDataSource
import com.marvelcomics.brito.data.remote.MarvelRemoteDataSource
import com.marvelcomics.brito.data.repo.MarvelRepo
import com.marvelcomics.brito.datalocal.MarvelLocalRepository
import com.marvelcomics.brito.datalocal.room.AppDatabase
import com.marvelcomics.brito.dataremote.OkHttpClientFactory
import com.marvelcomics.brito.dataremote.WebServiceFactory
import com.marvelcomics.brito.dataremote.api.MarvelAPI
import com.marvelcomics.brito.dataremote.api.MarvelAPIImpl
import com.marvelcomics.brito.dataremote.okhttp.KeyHashInterceptor
import com.marvelcomics.brito.dataremote.repository.MarvelRemoteRepository
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.domain.usecase.LoadAllCharactersIdsUseCase
import com.marvelcomics.brito.domain.usecase.LoadAllCharactersUseCase
import com.marvelcomics.brito.domain.usecase.LoadCharacterByIdUseCase
import com.marvelcomics.brito.domain.usecase.LoadCharacterUseCase
import com.marvelcomics.brito.domain.usecase.LoadComicsUseCase
import com.marvelcomics.brito.domain.usecase.LoadSeriesUseCase
import com.marvelcomics.brito.domain.usecase.SaveCharacterUseCase
import com.marvelcomics.brito.presentation.details.DetailCharacterViewModel
import com.marvelcomics.brito.presentation.home.HomeViewModel
import com.marvelcomics.brito.presentation.search.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

@InternalCoroutinesApi
object MarvelModules {

    fun injectFeature() {
        loadFeature
    }

    private val loadFeature by lazy {
        loadKoinModules(
            listOf(
                Data.database,
                Domain.usesCases,
                Data.interceptors,
                Data.repositories,
                Data.api,
                Presentation.viewModels
            )
        )
    }

    object Data {
        private val pubKey = "9294302a561e7a8a489807700c2b56a9"
        private val prvKey = "cfe96dfbecfd6158c4983507a65ee0edd1f99782"
        private val databaseName = "marvel-database"

        object Interceptors {
            const val KEY_HASH = "KeyHashInterceptor"
            const val LOGGING = "LoggingInterceptor"
        }

        object Api {
            const val WEBSERVICE = "WebService"
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

        val database = module {
            single {
                Room.databaseBuilder(
                    androidContext(),
                    AppDatabase::class.java,
                    databaseName
                ).build()
            }
        }

        val api = module {
            factory {
                OkHttpClientFactory.createHttpClient(
                    get(named(Interceptors.KEY_HASH)),
                    get(named(Interceptors.LOGGING))
                )
            }
            factory(named(Api.WEBSERVICE)) {
                WebServiceFactory.createWebService(get(), BuildConfig.MARVEL_URL)
            }
            single<MarvelAPI> {
                MarvelAPIImpl(
                    marvelWebService = get(named(Api.WEBSERVICE))
                )
            }
        }

        val repositories = module {
            factory<MarvelRemoteDataSource> { MarvelRemoteRepository(get()) }
            factory<MarvelLocalDataSource> { MarvelLocalRepository(get()) }
            factory<MarvelRepository> { MarvelRepo(get(), get()) }
        }
    }

    object Presentation {
        val viewModels = module {
            viewModel { HomeViewModel(get(), get()) }
            viewModel { SearchViewModel(get(), get()) }
            viewModel { DetailCharacterViewModel(get(), get(), Dispatchers.IO) }
        }
    }

    object Domain {
        val usesCases = module {
            factory { LoadAllCharactersIdsUseCase(get(), Dispatchers.IO) }
            factory { LoadAllCharactersUseCase(get(), Dispatchers.IO) }
            factory { LoadCharacterByIdUseCase(get(), Dispatchers.IO) }
            factory { LoadCharacterUseCase(get(), Dispatchers.IO) }
            factory { LoadComicsUseCase(get(), Dispatchers.IO) }
            factory { LoadSeriesUseCase(get()) }
            factory { SaveCharacterUseCase(get(), Dispatchers.IO) }
        }
    }
}
