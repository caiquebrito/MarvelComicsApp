package com.marvelcomics.brito.viewmodel.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import com.marvelcomics.brito.viewmodel.MainCoroutineRule
import com.marvelcomics.brito.viewmodel.getOrAwaitValue
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

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
    private val testDispatcher = TestCoroutineDispatcher()
    private val observer: Observer<CharacterUiState> = mockk()
    private val slot = slot<CharacterUiState>()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        characterViewModel = CharacterViewModel(iCharacterRepositoryMock, testDispatcher)
        characterViewModel.character.observeForever(observer)
    }

    @Test
    fun test_checkCharacterSuccess() {
        coEvery { iCharacterRepositoryMock.getCharacters(any()) } returns characterEntityMock

        characterViewModel.characterName.postValue("Caique")

        verify { observer.onChanged(capture(slot)) }

        assertEquals(
            "This value should be mocked value",
            CharacterUiState.Loading,
            slot.captured
        )

        verify { observer.onChanged(capture(slot)) }

        assertEquals(
            "This value should be mocked value",
            CharacterUiState.Success(characterEntityMock),
            (characterViewModel.character.value as CharacterUiState.Success).character
        )
    }
}