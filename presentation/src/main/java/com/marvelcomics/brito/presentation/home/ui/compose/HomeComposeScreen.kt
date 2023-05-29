package com.marvelcomics.brito.presentation.home.ui.compose

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.entity.ThumbnailEntity
import com.marvelcomics.brito.presentation.destinations.DetailCharacterScreenDestination
import com.marvelcomics.brito.presentation.destinations.SearchComposeScreenDestination
import com.marvelcomics.brito.presentation.home.HomeUiEffect
import com.marvelcomics.brito.presentation.home.HomeViewModel
import com.marvelcomics.brito.presentation.navdestination.HomeNavGraph
import com.marvelcomics.brito.presentation.ui.compose.components.MarvelTransitions
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsEffect
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsStateWithLifecycle
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppPreview
import com.marvelcomics.brito.presentation.ui.models.fromEntityToBundle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

@HomeNavGraph(start = true)
@Destination(style = MarvelTransitions::class)
@Composable
fun HomeComposeScreen(
    viewModel: HomeViewModel,
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<SearchComposeScreenDestination, Boolean>
) {
    val context = LocalContext.current
    fun handleDestEffect(homeUiEffect: HomeUiEffect) {
        when (homeUiEffect) {
            is HomeUiEffect.ShowError -> {
                Toast.makeText(context, "Show Error", Toast.LENGTH_LONG).show()
            }

            is HomeUiEffect.OpenSearchScreen -> {
                homeUiEffect.ids?.let {
                    navigator.navigate(
                        SearchComposeScreenDestination(
                            listIds = it.toIntArray()
                        )
                    )
                }
            }

            is HomeUiEffect.OpenDetailScreen -> {
                navigator.navigate(
                    DetailCharacterScreenDestination(
                        characterBundle = homeUiEffect.entity.fromEntityToBundle()
                    )
                )
            }
        }
    }

    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Value -> {
                if (result.value) {
                    Toast.makeText(context, "The SharedValueWas: ${viewModel.lastAddedCharacter}", Toast.LENGTH_LONG).show()
                    viewModel.getLocalCharacters()
                }
            }

            else -> {}
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value
    viewModel.effect.collectAsEffect(::handleDestEffect)
    if (state.showLoading) {
        viewModel.getLocalCharacters()
    }
    HomeScreenConstraint(
        showLoading = state.showLoading,
        listHeroes = state.listCharacters,
        searchButtonClick = viewModel::searchButtonClicked,
        adapterItemClick = viewModel::adapterItemClicked,
        emptyAdapterItemClick = viewModel::emptyButtonItemClicked
    )
}

@Preview(showBackground = true)
@Composable
fun HomeComposeScreenPreview() {
    val listHeroes = listOf(
        CharacterEntity(
            id = 0,
            name = "Captain Marvel",
            description = "Little Description",
            thumbnailEntity = ThumbnailEntity(
                path = "https://path",
                extension = "JPEG"
            )
        ),
        CharacterEntity(
            id = 0,
            name = "Captain Marvel",
            description = "Little Description",
            thumbnailEntity = ThumbnailEntity(
                path = "https://path",
                extension = "JPEG"
            )
        )
    )
    MarvelComicsAppPreview {
        HomeScreenConstraint(false, listHeroes, {}, {}, {})
    }
}
