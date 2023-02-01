package com.marvelcomics.brito.presentation.home

import com.marvelcomics.brito.domain.usecase.LoadAllCharactersIdsUseCase
import com.marvelcomics.brito.domain.usecase.LoadAllCharactersUseCase
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.presentation.BaseViewModelTest
import com.marvelcomics.brito.presentation.fake.MarvelFakeErrorRepository
import com.marvelcomics.brito.presentation.fake.MarvelFakeRepository
import com.marvelcomics.brito.presentation.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class HomeViewModelTest : BaseViewModelTest() {

    // Fake repository
    private val fakeRepository = MarvelFakeRepository()
    private val loadAllCharactersUseCase =
        LoadAllCharactersUseCase(fakeRepository, dispatcherRule.dispatcher)
    private val loadAllCharactersIdsUseCase =
        LoadAllCharactersIdsUseCase(fakeRepository, dispatcherRule.dispatcher)
    private val viewModel = HomeViewModel(loadAllCharactersUseCase, loadAllCharactersIdsUseCase)

    // Fake repository throws all
    private val fakeErrorRepository = MarvelFakeErrorRepository()
    private val loadAllCharactersErrorUseCase =
        LoadAllCharactersUseCase(fakeErrorRepository, dispatcherRule.dispatcher)
    private val loadAllCharactersIdsErrorUseCase =
        LoadAllCharactersIdsUseCase(fakeErrorRepository, dispatcherRule.dispatcher)
    private val viewModelError =
        HomeViewModel(loadAllCharactersErrorUseCase, loadAllCharactersIdsErrorUseCase)

    @Test
    fun `when the result is success and validate object`() = runTest {
        val stateEmissions = viewModel.state.test(this)
        val effectEmissions = viewModel.effect.test(this)
        val listCharacters = listOf(CharacterEntity(1, "Character", "Description", null))

        val homeUiState = HomeUiState(showLoading = true)

        viewModel.getLocalCharacters()

        stateEmissions.assertValues(
            homeUiState,
            homeUiState.copy(showLoading = false, listCharacters = listCharacters)
        )
        effectEmissions.assertNoValues()
    }

    @Test
    fun `when the result is error and throw exception`() = runTest {
        val effectEmissions = viewModelError.effect.test(this)

        viewModelError.getLocalCharacters()

        effectEmissions.assertValues(
            HomeUiEffect.ShowError
        )
    }

    @Test
    fun `when emptyButton was clicked and receive effect`() = runTest {
        val effectEmissions = viewModel.effect.test(this)

        viewModel.emptyButtonItemClicked()

        effectEmissions.assertValues(
            HomeUiEffect.OpenSearchScreen(null)
        )
    }

    @Test
    fun `when searchButton was clicked and receive list of ids`() = runTest {
        val effectEmissions = viewModel.effect.test(this)
        val listOfIds = listOf(1)

        viewModel.searchButtonClicked()

        effectEmissions.assertValues(
            HomeUiEffect.OpenSearchScreen(listOfIds)
        )
    }

    @Test
    fun `when searchButton was clicked and throw list of ids`() = runTest {
        val effectEmissions = viewModelError.effect.test(this)

        viewModelError.searchButtonClicked()

        effectEmissions.assertValues(
            HomeUiEffect.OpenSearchScreen(null)
        )
    }

    @Test
    fun `when adapterItem was clicked and call detail screen`() = runTest {
        val effectEmissions = viewModel.effect.test(this)
        val characterEntity = CharacterEntity(1, "name", "description", null)

        viewModel.adapterItemClicked(characterEntity)

        effectEmissions.assertValues(
            HomeUiEffect.OpenDetailScreen(characterEntity)
        )
    }
}
