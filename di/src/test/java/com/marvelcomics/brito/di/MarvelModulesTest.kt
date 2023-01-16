package com.marvelcomics.brito.di

import android.content.Context
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

@InternalCoroutinesApi
@OptIn(ExperimentalCoroutinesApi::class)
class MarvelModulesTest : KoinTest {

    private val context = mockk<Context>()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN Koin graph injection WHEN mocked extra modules THEN validate all items`() {
        koinApplication {
            androidContext(context)
            modules(
                listOf(
                    MarvelModules.Data.database,
                    MarvelModules.Domain.usesCases,
                    MarvelModules.Data.interceptors,
                    MarvelModules.Data.repositories,
                    MarvelModules.Data.api,
                    MarvelModules.Presentation.viewModels
                )
            )
            checkModules()
        }
    }
}
