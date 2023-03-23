package com.marvelcomics.brito.presentation.search.ui.compose

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.marvelcomics.brito.presentation.search.SearchUiEffect
import com.marvelcomics.brito.presentation.search.SearchViewModel
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsEffect
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsStateWithLifecycle
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppPreview
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun SearchComposeDestScreen(
    viewModel: SearchViewModel = koinViewModel(),
    listIds: IntArray,
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val context = LocalContext.current
    fun handleDestEffect(searchUiEffect: SearchUiEffect) {
        when (searchUiEffect) {
            SearchUiEffect.ShowError -> {
                Toast.makeText(context, "Show Error", Toast.LENGTH_LONG).show()
            }
            SearchUiEffect.BackToHome -> {
                resultNavigator.navigateBack(result = true)
            }
            SearchUiEffect.ShowAlreadyAddedError -> {
                Toast.makeText(context, "Character Already Added", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value
    viewModel.effect.collectAsEffect(::handleDestEffect)
    MarvelComicsAppTheme {
        if (state.isIdle) {
            viewModel.setListIds(listIds.toList())
        }
        SearchScreenConstraint(
            showLoading = state.showLoading,
            listCharacters = state.listCharacters,
            searchCharacterByName = viewModel::searchCharacterByName,
            addCharacterButtonClick = viewModel::addCharacterButtonClicked
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenConstraintDestPreview() {
    MarvelComicsAppPreview {
        SearchScreenConstraint(
            showLoading = false,
            listCharacters = emptyList(),
            searchCharacterByName = { },
            addCharacterButtonClick = { }
        )
    }
}
