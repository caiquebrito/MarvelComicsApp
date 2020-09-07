package com.marvelcomics.brito.viewmodel.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import com.marvelcomics.brito.viewmodel.MainCoroutineRule
import com.marvelcomics.brito.viewmodel.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @RelaxedMockK
    lateinit var characterEntityMock: CharacterEntity

    @RelaxedMockK
    lateinit var iCharacterRepositoryMock: ICharacterRepository

    lateinit var characterViewModel: CharacterViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        characterViewModel = CharacterViewModel(iCharacterRepositoryMock)
    }

    @Test
    fun test_checkCharacterSuccess() {
        coEvery { iCharacterRepositoryMock.getCharacters(any()) } returns characterEntityMock

        characterViewModel.character.observeForever {
            //shit dont work for now
        }

        characterViewModel.characterName.postValue("Caique")
        assertEquals(
            "This value should be mocked value",
            characterViewModel.character.getOrAwaitValue(),
            CharacterUiState.Success(characterEntityMock)
        )
    }
}