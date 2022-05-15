package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.models.SeriesDomain
import com.marvelcomics.brito.domain.repository.ISeriesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class SeriesUseCaseTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @RelaxedMockK
    lateinit var listSeries: List<SeriesDomain>

    @RelaxedMockK
    lateinit var runtimeException: RuntimeException

    @RelaxedMockK
    lateinit var unknownHostException: UnknownHostException

    @RelaxedMockK
    lateinit var iSeriesRepositoryMock: ISeriesRepository

    lateinit var seriesUseCase: SeriesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        seriesUseCase = SeriesUseCase(iSeriesRepositoryMock)
    }

    @Test
    fun `when the result is sucess and validate object`() = runBlockingTest {
        coEvery { iSeriesRepositoryMock.getSeries(any()) } returns listSeries

        val emissions = mutableListOf<Any>()
        val job = launch {
            seriesUseCase.getSeries(99).toList(emissions)
        }

        seriesUseCase.getSeries(99)

        coVerify(exactly = 1) { iSeriesRepositoryMock.getSeries(any()) }

        assertEquals(listSeries, emissions[0])
        job.cancel()
    }

    @Test(expected = RuntimeException::class)
    fun `when the result is failure and return runtime error`() = runBlockingTest {
        coEvery { iSeriesRepositoryMock.getSeries(any()) } throws runtimeException

        val emissions = mutableListOf<Any>()
        val job = launch {
            seriesUseCase.invoke(99).toList(emissions)
        }

        coVerify(exactly = 1) { iSeriesRepositoryMock.getSeries(any()) }
        job.cancel()
    }

    @Test(expected = NetworkException::class)
    fun `when the result is failure and return network error`() = runBlockingTest {
        coEvery { iSeriesRepositoryMock.getSeries(any()) } throws unknownHostException

        val emissions = mutableListOf<Any>()
        val job = launch {
            seriesUseCase.getSeries(99).toList(emissions)
        }

        seriesUseCase.getSeries(99)

        coVerify(exactly = 1) { iSeriesRepositoryMock.getSeries(any()) }
        job.cancel()
    }
}
