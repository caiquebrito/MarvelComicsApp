package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.models.CharacterDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class CharacterTest {

    @MockK
    lateinit var characterDomainMock: CharacterDomain

    @MockK
    lateinit var listCharacterMock: List<CharacterDomain>

    @MockK
    lateinit var runtimeException: RuntimeException

    @MockK
    lateinit var unknownHostException: UnknownHostException

    @MockK
    lateinit var iCharacterRepositoryMock: ICharacterRepository

    @MockK
    lateinit var coroutineDispatcherMock: CoroutineDispatcher

    @InjectMockKs
    lateinit var character: LoadCharacterUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when the result is sucess and validate object`() = runBlockingTest {
        coEvery { iCharacterRepositoryMock.getCharacters(any()) } returns listCharacterMock
        coEvery { listCharacterMock.first() } returns characterDomainMock

        val emissions = mutableListOf<Any>()
        val job = launch {
            character.invoke("Caique").let {
                it
            }
//            characterUseCase.invoke("Caique").toList(emissions)
        }

        character.invoke("Caique")

        coVerify(exactly = 1) { iCharacterRepositoryMock.getCharacters(any()) }

        assertEquals(characterDomainMock, emissions[0])
        job.cancel()
    }

    @Test(expected = RuntimeException::class)
    fun `when the result is failure and return runtime error`() = runBlockingTest {
        coEvery { iCharacterRepositoryMock.getCharacters(any()) } throws runtimeException

        val emissions = mutableListOf<Any>()
        val job = launch {
//            characterUseCase.invoke("Caique").toList(emissions)
        }

        character.invoke("Caique")

        coVerify(exactly = 1) { iCharacterRepositoryMock.getCharacters(any()) }
        job.cancel()
    }

    @Test(expected = NetworkException::class)
    fun `when the result is failure and return network error`() = runBlockingTest {
        coEvery { iCharacterRepositoryMock.getCharacters(any()) } throws unknownHostException

        val emissions = mutableListOf<Any>()
        val job = launch {
//            characterUseCase.invoke("Caique").toList(emissions)
        }

        character.invoke("Caique")

        coVerify(exactly = 1) { iCharacterRepositoryMock.getCharacters(any()) }
        job.cancel()
    }
}
