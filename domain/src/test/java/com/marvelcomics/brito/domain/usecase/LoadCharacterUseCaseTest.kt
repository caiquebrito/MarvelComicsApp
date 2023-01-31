package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.DispatcherRule
import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.fake.MarvelFakeRepository
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.entity.CharacterEntity
import kotlin.test.assertFailsWith
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoadCharacterUseCaseTest {

    @get:Rule
    val dispatcherRule = DispatcherRule()

    private var listCharacter = listOf(CharacterEntity(1, "Character", "Description", null))
    private var marvelFakeRepository: MarvelRepository = MarvelFakeRepository()
    private var characterUseCase =
        LoadCharacterUseCase(marvelFakeRepository, dispatcherRule.dispatcher)

    @Test
    fun `when the result is success and validate object`() = runTest {
        val result = characterUseCase.invoke("CharName").onSuccess {
            assertEquals(listCharacter, it)
        }
        assert(result is CoroutineUseCase.Result.Success)
    }

    @Test
    fun `when the result is failure and return EmptyInput error`() = runTest {
        val result = characterUseCase.invoke(null).onFailure {
            assert(it is EmptyInputException)
        }
        assert(result is CoroutineUseCase.Result.Failure)
    }
}
