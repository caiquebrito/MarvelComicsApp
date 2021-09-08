package com.marvelcomics.brito.presentation.series

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marvelcomics.brito.domain.entity.SeriesEntity
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.SeriesUseCase
import com.marvelcomics.brito.presentation.GlobalUiState
import com.marvelcomics.brito.presentation.SeriesUiState
import com.marvelcomics.brito.presentation.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SeriesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    lateinit var listSeriesMock: List<SeriesEntity>

    @RelaxedMockK
    lateinit var runtimeException: RuntimeException

    @RelaxedMockK
    lateinit var seriesUseCaseMock: SeriesUseCase

    lateinit var seriesViewModel: SeriesViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        seriesViewModel = SeriesViewModel(seriesUseCaseMock, testDispatcher)
    }

    @Test
    fun `when the result is sucess and validate object`() = mainCoroutineRule.runBlockingTest {

        coEvery { seriesUseCaseMock.getSeries(any()) } returns flow {
            emit(listSeriesMock)
        }

        val emissions = mutableListOf<Any>()
        val job = launch {
            seriesViewModel.seriesUiState.toList(emissions)
        }

        assertEquals(GlobalUiState.Empty, emissions[0])

        seriesViewModel.loadSeries(id = 99)

        assertEquals(GlobalUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        emissions[2].let {
            assertTrue(
                it is SeriesUiState.Success &&
                    listSeriesMock == it.data
            )
        }
        job.cancel()
    }

    @Test
    fun `when the result is failure and check the exception`() = mainCoroutineRule.runBlockingTest {
        coEvery { seriesUseCaseMock.getSeries(any()) } returns flow {
            throw runtimeException
        }

        val emissions = mutableListOf<Any>()
        val job = launch {
            seriesViewModel.seriesUiState.toList(emissions)
        }

        assertEquals(GlobalUiState.Empty, emissions[0])

        seriesViewModel.loadSeries(id = 99)

        assertEquals(GlobalUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        emissions[2].let {
            assertTrue(
                it is SeriesUiState.Error &&
                    runtimeException == it.exception
            )
        }
        job.cancel()
    }

    @Test
    fun `when the result is network issue`() = mainCoroutineRule.runBlockingTest {
        coEvery { seriesUseCaseMock.getSeries(any()) } returns flow {
            throw NetworkException()
        }

        val emissions = mutableListOf<Any>()
        val job = launch {
            seriesViewModel.seriesUiState.toList(emissions)
        }

        assertEquals(GlobalUiState.Empty, emissions[0])

        seriesViewModel.loadSeries(id = 99)

        assertEquals(GlobalUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        assertTrue(emissions[2] is GlobalUiState.NetworkError)

        job.cancel()
    }
}
