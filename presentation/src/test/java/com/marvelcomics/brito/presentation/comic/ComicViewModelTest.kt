package com.marvelcomics.brito.presentation.comic

import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.domain.usecase.ComicUseCase
import com.marvelcomics.brito.presentation.BaseViewModelTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ComicViewModelTest : BaseViewModelTest() {

    @MockK
    lateinit var listComicsMock: List<ComicEntity>

    @MockK
    lateinit var runtimeException: RuntimeException

    @MockK
    lateinit var comicUseCaseMock: ComicUseCase

    lateinit var viewModel: ComicViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = ComicViewModel(comicUseCaseMock)
    }

//    @Test
//    fun `when the result is sucess and validate object`() = mainCoroutineRule.runBlockingTest {
//
//        coEvery { comicUseCaseMock.getComics(any()) } returns flow {
//            emit(listComicsMock)
//        }
//
//        val emissions = mutableListOf<Any>()
//        val job = launch {
//            viewModel.comicUiState.toList(emissions)
//        }
//
//        assertEquals(Character.Empty, emissions[0])
//
//        viewModel.loadComics(id = 99)
//
//        assertEquals(GlobalUiState.Loading, emissions[1])
//
//        advanceTimeBy(2_000)
//
//        emissions[2].let {
//            assertTrue(
//                it is ComicScreenState.Success &&
//                    listComicsMock == it.data
//            )
//        }
//        job.cancel()
//    }
//
//    @Test
//    fun `when the result is failure and check the exception`() = mainCoroutineRule.runBlockingTest {
//        coEvery { comicUseCaseMock.getComics(any()) } returns flow {
//            throw runtimeException
//        }
//
//        val emissions = mutableListOf<Any>()
//        val job = launch {
//            viewModel.comicUiState.toList(emissions)
//        }
//
//        assertEquals(GlobalUiState.Empty, emissions[0])
//
//        viewModel.loadComics(id = 99)
//
//        assertEquals(GlobalUiState.Loading, emissions[1])
//
//        advanceTimeBy(2_000)
//
//        emissions[2].let {
//            assertTrue(
//                it is ComicScreenState.Error &&
//                    runtimeException == it.exception
//            )
//        }
//        job.cancel()
//    }
//
//    @Test
//    fun `when the result is network issue`() = mainCoroutineRule.runBlockingTest {
//        coEvery { comicUseCaseMock.getComics(any()) } returns flow {
//            throw NetworkException()
//        }
//
//        val emissions = mutableListOf<Any>()
//        val job = launch {
//            viewModel.comicUiState.toList(emissions)
//        }
//
//        assertEquals(GlobalUiState.Empty, emissions[0])
//
//        viewModel.loadComics(id = 99)
//
//        assertEquals(GlobalUiState.Loading, emissions[1])
//
//        advanceTimeBy(2_000)
//
//        assertTrue(emissions[2] is GlobalUiState.NetworkError)
//
//        job.cancel()
//    }
}
