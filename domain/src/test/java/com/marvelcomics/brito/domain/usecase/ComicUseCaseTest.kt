package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.CoroutineTestRule
import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.repository.IComicRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class ComicUseCaseTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @RelaxedMockK
    lateinit var listComics: List<ComicEntity>

    @RelaxedMockK
    lateinit var runtimeException: RuntimeException

    @RelaxedMockK
    lateinit var unknownHostException: UnknownHostException

    @RelaxedMockK
    lateinit var iComicRepositoryMock: IComicRepository

    lateinit var comicUseCase: ComicUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        comicUseCase = ComicUseCase(iComicRepositoryMock)
    }

    @Test
    fun `when the result is sucess and validate object`() = runBlockingTest {
        coEvery { iComicRepositoryMock.getComics(any()) } returns listComics

        val emissions = mutableListOf<Any>()
        val job = launch {
            comicUseCase.getComics(99).toList(emissions)
        }

        comicUseCase.getComics(99)

        coVerify(exactly = 1) { iComicRepositoryMock.getComics(any()) }

        Assert.assertEquals(listComics, emissions[0])
        job.cancel()
    }

    @Test(expected = RuntimeException::class)
    fun `when the result is failure and return runtime error`() = runBlockingTest {
        coEvery { iComicRepositoryMock.getComics(any()) } throws runtimeException

        val emissions = mutableListOf<Any>()
        val job = launch {
            comicUseCase.getComics(99).toList(emissions)
        }

        comicUseCase.getComics(99)

        coVerify(exactly = 1) { iComicRepositoryMock.getComics(any()) }
        job.cancel()
    }

    @Test(expected = NetworkException::class)
    fun `when the result is failure and return network error`() = runBlockingTest {
        coEvery { iComicRepositoryMock.getComics(any()) } throws unknownHostException

        val emissions = mutableListOf<Any>()
        val job = launch {
            comicUseCase.getComics(99).toList(emissions)
        }

        comicUseCase.getComics(99)

        coVerify(exactly = 1) { iComicRepositoryMock.getComics(any()) }
        job.cancel()
    }
}
