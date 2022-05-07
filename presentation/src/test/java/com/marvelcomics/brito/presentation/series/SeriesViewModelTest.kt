package com.marvelcomics.brito.presentation.series

import com.marvelcomics.brito.domain.entity.SeriesEntity
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.CoroutineUseCase
import com.marvelcomics.brito.domain.usecase.SeriesUseCase
import com.marvelcomics.brito.presentation.BaseViewModelTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class SeriesViewModelTest : BaseViewModelTest() {

    @RelaxedMockK
    lateinit var listSeriesMock: List<SeriesEntity>

    @RelaxedMockK
    lateinit var runtimeException: RuntimeException

    @RelaxedMockK
    lateinit var useCaseMock: SeriesUseCase

    @InjectMockKs
    lateinit var viewModel: SeriesViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = SeriesViewModel(useCaseMock)
    }

    @Test
    fun `when the result is sucess and validate object`() {
        executeOnBlockingTestScope(viewModel.bind()) { emissions ->
            coEvery { useCaseMock.invoke(any()) } returns
                CoroutineUseCase.Result.Success(listSeriesMock)

            assertEquals(SeriesScreenState.Empty, emissions[0])

            viewModel.handle(SeriesInteraction.LoadSeriesById(0))

            assertEquals(SeriesScreenState.Loading, emissions[1])

            emissions[2].let {
                assertTrue(
                    it is SeriesScreenState.Success &&
                        listSeriesMock == it.data
                )
            }
        }
    }

    @Test
    fun `when the result is failure and check the exception`() {
        executeOnBlockingTestScope(viewModel.bind()) { emissions ->
            coEvery { useCaseMock.invoke(any()) } returns
                CoroutineUseCase.Result.Failure(runtimeException)

            assertEquals(SeriesScreenState.Empty, emissions[0])

            viewModel.handle(SeriesInteraction.LoadSeriesById(0))

            assertEquals(SeriesScreenState.Loading, emissions[1])

            emissions[2].let {
                assertTrue(
                    it is SeriesScreenState.Error &&
                        runtimeException == it.exception
                )
            }
        }
    }

    @Test
    fun `when the result is network issue`() {
        executeOnBlockingTestScope(viewModel.bind()) { emissions ->
            coEvery { useCaseMock.invoke(any()) } returns
                CoroutineUseCase.Result.Failure(NetworkException())

            assertEquals(SeriesScreenState.Empty, emissions[0])

            viewModel.handle(SeriesInteraction.LoadSeriesById(0))

            assertEquals(SeriesScreenState.Loading, emissions[1])

            assertTrue(emissions[2] is SeriesScreenState.NetworkError)
        }
    }
}
