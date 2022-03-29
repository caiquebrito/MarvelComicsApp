package com.marvelcomics.brito.presentation.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.CharacterUseCase
import com.marvelcomics.brito.presentation.HomeState
import com.marvelcomics.brito.presentation.GlobalUiState
import com.marvelcomics.brito.presentation.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @RelaxedMockK
    lateinit var characterEntityMock: CharacterEntity

    @RelaxedMockK
    lateinit var runtimeException: RuntimeException

    @RelaxedMockK
    lateinit var characterUseCaseMock: CharacterUseCase

    lateinit var characterViewModel: CharacterViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        characterViewModel = CharacterViewModel(characterUseCaseMock, testDispatcher)
    }

    @Test
    fun `when the result is sucess and validate object`() = testCoroutineRule.runBlockingTest {

        coEvery { characterUseCaseMock.getCharacters(any()) } returns flow {
            emit(characterEntityMock)
        }

        val emissions = mutableListOf<Any>()
        val job = launch {
            characterViewModel.characterUiState.toList(emissions)
        }

        assertEquals(GlobalUiState.Empty, emissions[0])

        characterViewModel.loadCharacter("Caique")

        assertEquals(GlobalUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        emissions[2].let {
            assertTrue(
                it is HomeState.Success &&
                    characterEntityMock == it.data
            )
        }
        job.cancel()
    }

    @Test
    fun `when the result is failure and check the exception`() = testCoroutineRule.runBlockingTest {
        coEvery { characterUseCaseMock.getCharacters(any()) } returns flow {
            throw runtimeException
        }

        val emissions = mutableListOf<Any>()
        val job = launch {
            characterViewModel.characterUiState.toList(emissions)
        }

        assertEquals(GlobalUiState.Empty, emissions[0])

        characterViewModel.loadCharacter("Caique")

        assertEquals(GlobalUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        emissions[2].let {
            assertTrue(
                it is HomeState.Error &&
                    runtimeException == it.exception
            )
        }
        job.cancel()
    }

    @Test
    fun `when the result is network issue`() = testCoroutineRule.runBlockingTest {
        coEvery { characterUseCaseMock.getCharacters(any()) } returns flow {
            throw NetworkException()
        }

        val emissions = mutableListOf<Any>()
        val job = launch {
            characterViewModel.characterUiState.toList(emissions)
        }

        assertEquals(GlobalUiState.Empty, emissions[0])

        characterViewModel.loadCharacter("Caique")

        assertEquals(GlobalUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        assertTrue(emissions[2] is GlobalUiState.NetworkError)

        job.cancel()
    }
}
