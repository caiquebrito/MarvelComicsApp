package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.CoroutineTestRule
import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.domain.repository.IComicRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ComicUseCaseTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @RelaxedMockK
    lateinit var listComics: List<ComicEntity>

    @RelaxedMockK
    lateinit var iComicRepository: IComicRepository

    lateinit var comicUseCase: ComicUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        comicUseCase = ComicUseCase(iComicRepository)
    }

    @Test
    fun test_getComics() = runBlockingTest {
        coEvery { comicUseCase.getComics(any()) } returns listComics
        val list = comicUseCase.getComics(0)
        coVerify(exactly = 1) { iComicRepository.getComics(any()) }
        assertEquals("This should return the mock object", listComics, list)
    }
}