package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.BaseUseCaseTest
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.models.SeriesDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
class SeriesUseCaseTest : BaseUseCaseTest() {

    @MockK
    lateinit var listSeries: List<SeriesDomain>

    @MockK
    lateinit var runtimeException: RuntimeException

    @MockK
    lateinit var unknownHostException: UnknownHostException

    @MockK
    lateinit var iSeriesRepositoryMock: ISeriesRepository

    private val dispatcherIO = Dispatchers.IO

    lateinit var seriesUseCase: SeriesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        seriesUseCase = SeriesUseCase(iSeriesRepositoryMock, dispatcherIO)
    }

    @Test
    fun `when the result is sucess and validate object`() = runTest {
        coEvery { iSeriesRepositoryMock.getSeries(any()) } returns listSeries

        val result = seriesUseCase.invoke(10)

//        coVerify(exactly = 1) { iSeriesRepositoryMock.getSeries(any()) }

        assertEquals(listSeries, result)
    }

    @Test(expected = RuntimeException::class)
    fun `when the result is failure and return runtime error`() = runBlockingTest {
        coEvery { iSeriesRepositoryMock.getSeries(any()) } throws runtimeException

        val emissions = mutableListOf<Any>()
        val job = launch {
//            seriesUseCase.invoke(99).toList(emissions)
        }

        coVerify(exactly = 1) { iSeriesRepositoryMock.getSeries(any()) }
        job.cancel()
    }

    @Test(expected = NetworkException::class)
    fun `when the result is failure and return network error`() = runBlockingTest {
        coEvery { iSeriesRepositoryMock.getSeries(any()) } throws unknownHostException

        val emissions = mutableListOf<Any>()
        val job = launch {
//            seriesUseCase.getSeries(99).toList(emissions)
        }

//        seriesUseCase.getSeries(99)

        coVerify(exactly = 1) { iSeriesRepositoryMock.getSeries(any()) }
        job.cancel()
    }
}
