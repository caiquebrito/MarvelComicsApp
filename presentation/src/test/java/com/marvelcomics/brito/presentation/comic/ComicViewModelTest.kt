package com.marvelcomics.brito.presentation.comic

import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.usecase.Comic
import com.marvelcomics.brito.domain.usecase.CoroutineUseCase
import com.marvelcomics.brito.presentation.BaseViewModelTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ComicViewModelTest : BaseViewModelTest() {

    @MockK
    lateinit var listComicsMock: List<ComicDomain>

    @MockK
    lateinit var runtimeException: RuntimeException

    @MockK
    lateinit var useCaseMock: Comic

    @InjectMockKs
    lateinit var viewModel: ComicViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when the result is sucess and validate object`() {
        executeOnBlockingTestScope(viewModel.bind()) { emissions ->
            coEvery { useCaseMock.invoke(any()) } returns
                CoroutineUseCase.Result.Success(listComicsMock)

            assertEquals(ComicScreenState.Empty, emissions[0])

            viewModel.handle(ComicInteraction.LoadComicsById(0))

            assertEquals(ComicScreenState.Loading, emissions[1])

            emissions[2].let {
                assertTrue(
                    it is ComicScreenState.Success &&
                        listComicsMock == it.data
                )
            }
        }
    }

    @Test
    fun `when the result is failure and check the exception`() {
        executeOnBlockingTestScope(viewModel.bind()) { emissions ->
            coEvery { useCaseMock.invoke(any()) } returns
                CoroutineUseCase.Result.Failure(runtimeException)

            assertEquals(ComicScreenState.Empty, emissions[0])

            viewModel.handle(ComicInteraction.LoadComicsById(0))

            assertEquals(ComicScreenState.Loading, emissions[1])

            emissions[2].let {
                assertTrue(
                    it is ComicScreenState.Error &&
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

            assertEquals(ComicScreenState.Empty, emissions[0])

            viewModel.handle(ComicInteraction.LoadComicsById(0))

            assertEquals(ComicScreenState.Loading, emissions[1])

            assertTrue(emissions[2] is ComicScreenState.NetworkError)
        }
    }
}
