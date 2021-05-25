package com.marvelcomics.brito.viewmodel.character

import com.marvelcomics.brito.domain.ResultWrapper
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import com.marvelcomics.brito.viewmodel.BaseUiState
import com.marvelcomics.brito.viewmodel.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class CharacterViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    lateinit var characterEntityMock: CharacterEntity

    @RelaxedMockK
    lateinit var resultWrapperSuccess: ResultWrapper.Success<CharacterEntity>

    @RelaxedMockK
    lateinit var resultWrapperFailure: ResultWrapper.Failure

    @RelaxedMockK
    lateinit var resultWrapperNetwork: ResultWrapper.NetworkError

    @RelaxedMockK
    lateinit var runtimeException: RuntimeException

    @RelaxedMockK
    lateinit var iCharacterRepositoryMock: ICharacterRepository

    lateinit var characterViewModel: CharacterViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        characterViewModel = CharacterViewModel(iCharacterRepositoryMock, testDispatcher)
    }

    @Test
    fun `when the result is sucess and validate object`() = mainCoroutineRule.runBlockingTest {

        coEvery { iCharacterRepositoryMock.getCharacters(any()) } returns resultWrapperSuccess
        coEvery { resultWrapperSuccess.value } returns characterEntityMock

        val emissions = mutableListOf<BaseUiState<CharacterEntity>>()
        val job = launch {
            characterViewModel.characterUiState.toList(emissions)
        }

        assertEquals(BaseUiState.Empty, emissions[0])

        characterViewModel.loadCharacter("Caique")

        assertEquals(BaseUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        emissions[2].let {
            assertTrue(
                it is BaseUiState.Success &&
                    resultWrapperSuccess.value == it.`object`
            )
        }
        job.cancel()
    }

    @Test
    fun `when the result is failure and check the exception`() = mainCoroutineRule.runBlockingTest {
        coEvery { iCharacterRepositoryMock.getCharacters(any()) } returns resultWrapperFailure
        coEvery { resultWrapperFailure.error } returns runtimeException

        val emissions = mutableListOf<BaseUiState<CharacterEntity>>()
        val job = launch {
            characterViewModel.characterUiState.toList(emissions)
        }

        assertEquals(BaseUiState.Empty, emissions[0])

        characterViewModel.loadCharacter("Caique")

        assertEquals(BaseUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        emissions[2].let {
            assertTrue(
                it is BaseUiState.Error &&
                    resultWrapperFailure.error == it.exception
            )
        }
        job.cancel()
    }

    @Test
    fun `when the result is network issue`() = mainCoroutineRule.runBlockingTest {
        coEvery { iCharacterRepositoryMock.getCharacters(any()) } returns resultWrapperNetwork

        val emissions = mutableListOf<BaseUiState<CharacterEntity>>()
        val job = launch {
            characterViewModel.characterUiState.toList(emissions)
        }

        assertEquals(BaseUiState.Empty, emissions[0])

        characterViewModel.loadCharacter("Caique")

        assertEquals(BaseUiState.Loading, emissions[1])

        advanceTimeBy(2_000)

        assertTrue(emissions[2] is BaseUiState.NetworkError)

        job.cancel()
    }
}
