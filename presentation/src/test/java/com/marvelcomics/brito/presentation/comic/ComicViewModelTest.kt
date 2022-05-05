package com.marvelcomics.brito.presentation.comic

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.ComicUseCase
import com.marvelcomics.brito.presentation.GlobalUiState
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
class ComicViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    lateinit var listComicsMock: List<ComicEntity>

    @RelaxedMockK
    lateinit var runtimeException: RuntimeException

    @RelaxedMockK
    lateinit var comicUseCaseMock: ComicUseCase

    lateinit var comicViewModel: ComicViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        comicViewModel = ComicViewModel(comicUseCaseMock, testDispatcher)
    }

    @Test
    fun `when the result is sucess and validate object`() = mainCoroutineRule.runBlockingTest {

        coEvery { comicUseCaseMock.getComics(any()) } returns flow {
            emit(listComicsMock)
        }

        val emissions = mutableListOf<Any>()
        val job = launch {
            comicViewModel.comicUiState.toList(emissions)
        }

        assertEquals(Character.Empty, emissions[0])

        comicViewModel.loadComics(id = 99)

        assertEquals(GlobalUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        emissions[2].let {
            assertTrue(
                it is ComicScreenState.Success &&
                    listComicsMock == it.data
            )
        }
        job.cancel()
    }

    @Test
    fun `when the result is failure and check the exception`() = mainCoroutineRule.runBlockingTest {
        coEvery { comicUseCaseMock.getComics(any()) } returns flow {
            throw runtimeException
        }

        val emissions = mutableListOf<Any>()
        val job = launch {
            comicViewModel.comicUiState.toList(emissions)
        }

        assertEquals(GlobalUiState.Empty, emissions[0])

        comicViewModel.loadComics(id = 99)

        assertEquals(GlobalUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        emissions[2].let {
            assertTrue(
                it is ComicScreenState.Error &&
                    runtimeException == it.exception
            )
        }
        job.cancel()
    }

    @Test
    fun `when the result is network issue`() = mainCoroutineRule.runBlockingTest {
        coEvery { comicUseCaseMock.getComics(any()) } returns flow {
            throw NetworkException()
        }

        val emissions = mutableListOf<Any>()
        val job = launch {
            comicViewModel.comicUiState.toList(emissions)
        }

        assertEquals(GlobalUiState.Empty, emissions[0])

        comicViewModel.loadComics(id = 99)

        assertEquals(GlobalUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        assertTrue(emissions[2] is GlobalUiState.NetworkError)

        job.cancel()
    }
}
