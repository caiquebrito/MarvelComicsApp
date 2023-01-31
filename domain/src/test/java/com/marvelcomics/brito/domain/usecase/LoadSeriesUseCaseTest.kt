package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.DispatcherRule
import com.marvelcomics.brito.domain.fake.MarvelFakeRepository
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.entity.SeriesEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.net.UnknownHostException
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoadSeriesUseCaseTest {

    @get:Rule
    val dispatcherRule = DispatcherRule()

    private var listSeries = listOf(SeriesEntity(1, "Title", "Description", null))
    private val marvelRepository = MarvelFakeRepository()
    private val loadSeriesUseCase = LoadSeriesUseCase(marvelRepository)

    @Test
    fun `when the result is sucess and validate object`() = runTest {
        loadSeriesUseCase.invoke(10).collect {
            assertEquals(listSeries, it)
        }
    }
}
