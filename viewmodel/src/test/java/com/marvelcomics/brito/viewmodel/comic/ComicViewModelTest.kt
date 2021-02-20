package com.marvelcomics.brito.viewmodel.comic

import com.marvelcomics.brito.domain.ResultWrapper
import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.domain.entity.SeriesEntity
import com.marvelcomics.brito.domain.repository.IComicRepository
import com.marvelcomics.brito.viewmodel.BaseUiState
import com.marvelcomics.brito.viewmodel.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import java.lang.RuntimeException
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ComicViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    lateinit var listComicsMock: List<ComicEntity>

    @RelaxedMockK
    lateinit var resultWrapperSuccess: ResultWrapper.Success<List<ComicEntity>>

    @RelaxedMockK
    lateinit var resultWrapperFailure: ResultWrapper.Failure

    @RelaxedMockK
    lateinit var resultWrapperNetwork: ResultWrapper.NetworkError

    @RelaxedMockK
    lateinit var runtimeException: RuntimeException

    @RelaxedMockK
    lateinit var iComicRepository: IComicRepository

    lateinit var comicViewModel: ComicViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        comicViewModel = ComicViewModel(iComicRepository, testDispatcher)
    }

    @Test
    fun `when the result is sucess and validate object`() = mainCoroutineRule.runBlockingTest {

        coEvery { iComicRepository.getComics(any()) } returns resultWrapperSuccess
        coEvery { resultWrapperSuccess.value } returns listComicsMock

        val emissions = mutableListOf<BaseUiState<List<ComicEntity>>>()
        val job = launch {
            comicViewModel.comicUiState.toList(emissions)
        }

        assertEquals(BaseUiState.Empty, emissions[0])

        comicViewModel.loadComics(id = 99)

        assertEquals(BaseUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        emissions[2].let {
            assertTrue(
                it is BaseUiState.Success &&
                        resultWrapperSuccess.value == it.`object`
            )
        }
        job.cancel()
    }

    @Test
    fun `when the result is failure and check the exception`() = mainCoroutineRule.runBlockingTest {
        coEvery { iComicRepository.getComics(any()) } returns resultWrapperFailure
        coEvery { resultWrapperFailure.error } returns runtimeException

        val emissions = mutableListOf<BaseUiState<List<ComicEntity>>>()
        val job = launch {
            comicViewModel.comicUiState.toList(emissions)
        }

        assertEquals(BaseUiState.Empty, emissions[0])

        comicViewModel.loadComics(id = 99)

        assertEquals(BaseUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        emissions[2].let {
            assertTrue(
                it is BaseUiState.Error &&
                        resultWrapperFailure.error == it.exception
            )
        }
        job.cancel()
    }

    @Test
    fun `when the result is network issue`() = mainCoroutineRule.runBlockingTest {
        coEvery { iComicRepository.getComics(any()) } returns resultWrapperNetwork

        val emissions = mutableListOf<BaseUiState<List<ComicEntity>>>()
        val job = launch {
            comicViewModel.comicUiState.toList(emissions)
        }

        assertEquals(BaseUiState.Empty, emissions[0])

        comicViewModel.loadComics(id = 99)

        assertEquals(BaseUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        assertTrue(emissions[2] is BaseUiState.NetworkError)

        job.cancel()
    }
}