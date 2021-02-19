package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.CoroutineTestRule
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.repository.ICharacterRepository
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
class CharacterUseCaseTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @RelaxedMockK
    lateinit var characterEntityMock: CharacterEntity
    @RelaxedMockK
    lateinit var iCharacterRepositoryMock: ICharacterRepository

    lateinit var characterUseCase: CharacterUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        characterUseCase = CharacterUseCase(iCharacterRepositoryMock)
    }

    @Test
    fun test_getCharacters() = runBlockingTest {
        coEvery { characterUseCase.getCharacters(any()) } returns characterEntityMock
        val character = characterUseCase.getCharacters("Caique")
        coVerify(exactly = 1) { iCharacterRepositoryMock.getCharacters(any()) }
        assertEquals("This should return the mock object", characterEntityMock, character)
    }
}
