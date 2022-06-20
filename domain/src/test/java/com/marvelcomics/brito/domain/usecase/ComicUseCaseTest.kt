package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.models.ComicDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException
import kotlinx.coroutines.CoroutineDispatcher

@ExperimentalCoroutinesApi
class ComicUseCaseTest {

    @MockK
    lateinit var listComics: List<ComicDomain>

    @MockK
    lateinit var runtimeException: RuntimeException

    @MockK
    lateinit var unknownHostException: UnknownHostException

    @MockK
    lateinit var iComicRepositoryMock: IComicRepository

    @MockK
    lateinit var coroutineDispatcherMock: CoroutineDispatcher

    @InjectMockKs
    lateinit var comicUseCase: ComicUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when the result is sucess and validate object`() = runBlockingTest {
        coEvery { iComicRepositoryMock.getComics(any()) } returns listComics

        val emissions = mutableListOf<Any>()
        val job = launch {
//            comicUseCase.getComics(99).toList(emissions)
        }

//        comicUseCase.getComics(99)

        coVerify(exactly = 1) { iComicRepositoryMock.getComics(any()) }

        assertEquals(listComics, emissions[0])
        job.cancel()
    }

    @Test(expected = RuntimeException::class)
    fun `when the result is failure and return runtime error`() = runBlockingTest {
        coEvery { iComicRepositoryMock.getComics(any()) } throws runtimeException

        val emissions = mutableListOf<Any>()
        val job = launch {
//            comicUseCase.getComics(99).toList(emissions)
        }

//        comicUseCase.getComics(99)

        coVerify(exactly = 1) { iComicRepositoryMock.getComics(any()) }
        job.cancel()
    }

    @Test(expected = NetworkException::class)
    fun `when the result is failure and return network error`() = runBlockingTest {
        coEvery { iComicRepositoryMock.getComics(any()) } throws unknownHostException

        val emissions = mutableListOf<Any>()
        val job = launch {
//            comicUseCase.getComics(99).toList(emissions)
        }

//        comicUseCase.getComics(99)

        coVerify(exactly = 1) { iComicRepositoryMock.getComics(any()) }
        job.cancel()
    }
}
