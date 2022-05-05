package com.marvelcomics.brito.presentation.character

import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.CharacterUseCase
import com.marvelcomics.brito.domain.usecase.CoroutineUseCase
import com.marvelcomics.brito.presentation.BaseViewModelTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class CharacterViewModelTest : BaseViewModelTest() {

    @MockK
    lateinit var characterEntityMock: CharacterEntity

    @MockK
    lateinit var runtimeExceptionMock: RuntimeException

    @MockK
    lateinit var useCaseMock: CharacterUseCase

    lateinit var viewModel: CharacterViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = CharacterViewModel(useCaseMock)
    }

    @Test
    fun `when the result is sucess and validate object`() {
        executeOnBlockingTestScope(viewModel.bind()) { emissions ->
            coEvery { useCaseMock.invoke(any()) } returns
                CoroutineUseCase.Result.Success(characterEntityMock)

            assertEquals(CharacterScreenState.Empty, emissions[0])

            viewModel.handle(CharacterInteraction.SearchCharacter(""))

            assertEquals(CharacterScreenState.Loading, emissions[1])

            emissions[2].let {
                assertTrue(
                    "Object are not same",
                    it is CharacterScreenState.Success &&
                        characterEntityMock == CoroutineUseCase.castSuccess(it.data)
                )
            }
        }
    }

    @Test
    fun `when the result is failure and check the exception`() {
        executeOnBlockingTestScope(viewModel.bind()) { emissions ->
            coEvery { useCaseMock.invoke(any()) } returns
                CoroutineUseCase.Result.Failure(runtimeExceptionMock)

            assertEquals(CharacterScreenState.Empty, emissions[0])

            viewModel.handle(CharacterInteraction.SearchCharacter("Caique"))

            assertEquals(CharacterScreenState.Loading, emissions[1])

            emissions[2].let {
                assertTrue(
                    it is CharacterScreenState.Error &&
                        runtimeExceptionMock == it.exception
                )
            }
        }
    }

    @Test
    fun `when the result is network issue`() {
        executeOnBlockingTestScope(viewModel.bind()) { emissions ->
            coEvery { useCaseMock.invoke(any()) } returns
                CoroutineUseCase.Result.Failure(NetworkException())

            assertEquals(CharacterScreenState.Empty, emissions[0])

            viewModel.handle(CharacterInteraction.SearchCharacter("Caique"))

            assertEquals(CharacterScreenState.Loading, emissions[1])

            assertTrue(emissions[2] is CharacterScreenState.NetworkError)
        }
    }
}
