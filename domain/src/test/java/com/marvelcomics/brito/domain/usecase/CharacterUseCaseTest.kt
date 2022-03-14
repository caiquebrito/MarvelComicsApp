package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.CoroutineTestRule
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class CharacterUseCaseTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @RelaxedMockK
    lateinit var characterEntityMock: CharacterEntity

    @RelaxedMockK
    lateinit var listCharacterMock: List<CharacterEntity>

    @RelaxedMockK
    lateinit var runtimeException: RuntimeException

    @RelaxedMockK
    lateinit var unknownHostException: UnknownHostException

    @RelaxedMockK
    lateinit var iCharacterRepositoryMock: ICharacterRepository

    lateinit var characterUseCase: CharacterUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        characterUseCase = CharacterUseCase(iCharacterRepositoryMock)
    }

    @Test
    fun `when the result is sucess and validate object`() = runBlockingTest {
        coEvery { iCharacterRepositoryMock.getCharacters(any()) } returns listCharacterMock
        coEvery { listCharacterMock.first() } returns characterEntityMock

        val emissions = mutableListOf<Any>()
        val job = launch {
            characterUseCase.getCharacters("Caique").toList(emissions)
        }

        characterUseCase.getCharacters("Caique")

        coVerify(exactly = 1) { iCharacterRepositoryMock.getCharacters(any()) }

        assertEquals(characterEntityMock, emissions[0])
        job.cancel()
    }

    @Test(expected = RuntimeException::class)
    fun `when the result is failure and return runtime error`() = runBlockingTest {
        coEvery { iCharacterRepositoryMock.getCharacters(any()) } throws runtimeException

        val emissions = mutableListOf<Any>()
        val job = launch {
            characterUseCase.getCharacters("Caique").toList(emissions)
        }

        characterUseCase.getCharacters("Caique")

        coVerify(exactly = 1) { iCharacterRepositoryMock.getCharacters(any()) }
        job.cancel()
    }

    @Test(expected = NetworkException::class)
    fun `when the result is failure and return network error`() = runBlockingTest {
        coEvery { iCharacterRepositoryMock.getCharacters(any()) } throws unknownHostException

        val emissions = mutableListOf<Any>()
        val job = launch {
            characterUseCase.getCharacters("Caique").toList(emissions)
        }

        characterUseCase.getCharacters("Caique")

        coVerify(exactly = 1) { iCharacterRepositoryMock.getCharacters(any()) }
        job.cancel()
    }
}
