package com.marvelcomics.brito.presentation.home

import com.marvelcomics.brito.domain.usecase.LoadAllCharactersIdsUseCase
import com.marvelcomics.brito.domain.usecase.LoadAllCharactersUseCase
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.presentation.CoroutineTestRule
import com.marvelcomics.brito.presentation.collectTestFlows
import com.marvelcomics.brito.presentation.fake.MarvelFakeErrorRepository
import com.marvelcomics.brito.presentation.fake.MarvelFakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    // Fake repository
    private val fakeRepository = MarvelFakeRepository()
    private val loadAllCharactersUseCase =
        LoadAllCharactersUseCase(fakeRepository, coroutineTestRule.dispatcher)
    private val loadAllCharactersIdsUseCase =
        LoadAllCharactersIdsUseCase(fakeRepository, coroutineTestRule.dispatcher)
    private val viewModel = HomeViewModel(loadAllCharactersUseCase, loadAllCharactersIdsUseCase)

    // Fake repository throws all
    private val fakeErrorRepository = MarvelFakeErrorRepository()
    private val loadAllCharactersErrorUseCase =
        LoadAllCharactersUseCase(fakeErrorRepository, coroutineTestRule.dispatcher)
    private val loadAllCharactersIdsErrorUseCase =
        LoadAllCharactersIdsUseCase(fakeErrorRepository, coroutineTestRule.dispatcher)
    private val viewModelError =
        HomeViewModel(loadAllCharactersErrorUseCase, loadAllCharactersIdsErrorUseCase)

    @Test
    fun `when the result is success and validate object`() = runTest {
        val listCharacters = listOf(CharacterEntity(1, "Character", "Description", null))
        val homeUiState = HomeUiState(showLoading = true)
        collectTestFlows(viewModel.state, viewModel.effect) { state, effect ->
            viewModel.getLocalCharacters()
            state.assertValuesInOrder(
                homeUiState,
                homeUiState.copy(showLoading = false, listCharacters = listCharacters)
            )
            effect.assertNoValues()
        }
    }

    @Test
    fun `when the result is error and throw exception`() = runTest {
        collectTestFlows(viewModelError.effect) { effect ->
            viewModelError.getLocalCharacters()
            effect.assertValuesInOrder(
                HomeUiEffect.ShowError,
                HomeUiEffect.ShowError
            )
        }
    }

    @Test
    fun `when emptyButton was clicked and receive effect`() = runTest {
        collectTestFlows(viewModel.effect) { effect ->
            viewModel.emptyButtonItemClicked()
            effect.assertValuesInOrder(
                HomeUiEffect.OpenSearchScreen(null)
            )
        }
    }

    @Test
    fun `when searchButton was clicked and receive list of ids`() = runTest {
        val listOfIds = listOf(1)
        collectTestFlows(viewModel.effect) { effect ->
            viewModel.searchButtonClicked()
            effect.assertValuesInOrder(
                HomeUiEffect.OpenSearchScreen(listOfIds)
            )
        }
    }

    @Test
    fun `when searchButton was clicked and throw list of ids`() = runTest {
        collectTestFlows(viewModelError.effect) { effect ->
            viewModelError.searchButtonClicked()
            effect.assertValuesInOrder(
                HomeUiEffect.OpenSearchScreen(null)
            )
        }
    }

    @Test
    fun `when adapterItem was clicked and call detail screen`() = runTest {
        val characterEntity = CharacterEntity(1, "name", "description", null)
        collectTestFlows(viewModel.effect) { effect ->
            viewModel.adapterItemClicked(characterEntity)
            effect.assertValuesInOrder(
                HomeUiEffect.OpenDetailScreen(characterEntity)
            )
        }
    }
}
