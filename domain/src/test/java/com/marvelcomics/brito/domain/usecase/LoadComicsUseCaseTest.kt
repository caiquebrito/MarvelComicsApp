package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.DispatcherRule
import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.fake.MarvelFakeRepository
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.entity.ComicEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoadComicsUseCaseTest {

    @get:Rule
    val dispatcherRule = DispatcherRule()

    private var listComics = listOf(ComicEntity(1, "Title", "Description", null))
    private var marvelFakeRepository: MarvelRepository = MarvelFakeRepository()
    private var comicsUseCase =
        LoadComicsUseCase(marvelFakeRepository, dispatcherRule.dispatcher)

    @Test
    fun `when the result is success and validate object`() = runTest {
        val result = comicsUseCase.invoke(10).onSuccess {
            Assert.assertEquals(listComics, it)
        }
        assert(result is CoroutineUseCase.Result.Success)
    }

    @Test
    fun `when the result is failure and return EmptyInput error`() = runTest {
        val result = comicsUseCase.invoke(null).onFailure {
            assert(it is EmptyInputException)
        }
        assert(result is CoroutineUseCase.Result.Failure)
    }
}
