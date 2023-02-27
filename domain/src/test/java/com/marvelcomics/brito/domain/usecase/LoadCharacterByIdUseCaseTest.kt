package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.DispatcherRule
import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.fake.MarvelFakeRepository
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.domain.usecase.models.CoroutineUseCase
import com.marvelcomics.brito.domain.usecase.models.onFailure
import com.marvelcomics.brito.domain.usecase.models.onSuccess
import com.marvelcomics.brito.entity.CharacterEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class LoadCharacterByIdUseCaseTest {

    @get:Rule
    val dispatcherRule = DispatcherRule()

    private var characterEntity = CharacterEntity(1, "Character", "Description", null)
    private var marvelFakeRepository: MarvelRepository = MarvelFakeRepository()
    private var loadCharacterByIdUseCase =
        LoadCharacterByIdUseCase(marvelFakeRepository, dispatcherRule.dispatcher)

    @Test
    fun `when the result is success and validate object`() = runTest {
        val result = loadCharacterByIdUseCase.invoke(10).onSuccess {
            assertEquals(characterEntity, it)
        }
        assert(result is CoroutineUseCase.Result.Success)
    }

    @Test
    fun `when the result is failure and return EmptyInput error`() = runTest {
        val result = loadCharacterByIdUseCase.invoke(null).onFailure {
            assert(it is EmptyInputException)
        }
        assert(result is CoroutineUseCase.Result.Failure)
    }
}
