package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.CoroutineTestRule
import com.marvelcomics.brito.domain.entity.SeriesEntity
import com.marvelcomics.brito.domain.repository.ISeriesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SeriesUseCaseTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @RelaxedMockK
    lateinit var listSeries: List<SeriesEntity>
    @RelaxedMockK
    lateinit var iSeriesRepository: ISeriesRepository

    lateinit var seriesUseCase: SeriesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        seriesUseCase = SeriesUseCase(iSeriesRepository)
    }

    @Test
    fun test_getSeries() = runBlockingTest {
        coEvery { seriesUseCase.getSeries(any()) } returns listSeries
        val list = seriesUseCase.getSeries(0)
        coVerify(exactly = 1) { iSeriesRepository.getSeries(any()) }
        assertEquals("This should return the mock object", listSeries, list)
    }
}