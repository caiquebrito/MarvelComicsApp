package com.marvelcomics.brito.presentation.details.ui.compose

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.marvelcomics.brito.presentation.details.DetailCharacterUiEffect
import com.marvelcomics.brito.presentation.details.DetailCharacterViewModel
import com.marvelcomics.brito.presentation.navdestination.HomeNavGraph
import com.marvelcomics.brito.presentation.ui.compose.components.MarvelTransitions
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsEffect
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsStateWithLifecycle
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppPreview
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppTheme
import com.marvelcomics.brito.presentation.ui.models.CharacterDataBundle
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@HomeNavGraph
@Destination(style = MarvelTransitions::class)
@Composable
fun AnimatedVisibilityScope.DetailCharacterScreen(
    viewModel: DetailCharacterViewModel = koinViewModel(),
    characterBundle: CharacterDataBundle
) {
    val context = LocalContext.current
    fun handleDestEffect(effect: DetailCharacterUiEffect) {
        when (effect) {
            DetailCharacterUiEffect.ShowComicsError -> {
                Toast.makeText(context, "Show Comics Error", Toast.LENGTH_SHORT).show()
            }
            DetailCharacterUiEffect.ShowSeriesError -> {
                Toast.makeText(context, "Show Series Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value
    viewModel.effect.collectAsEffect(::handleDestEffect)
    MarvelComicsAppTheme {
        if (state.isIdle) {
            viewModel.getComicsAndSeriesById(characterBundle.id)
        }
        DetailCharacterScreenConstraint(
            characterBundle = characterBundle,
            showComicsLoading = state.showComicsLoading,
            showSeriesLoading = state.showSeriesLoading,
            listComics = state.listComics,
            listSeries = state.listSeries
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailCharacterScreenPreview() {
    val characterBundle = CharacterDataBundle(
        0,
        null,
        null,
        null
    )
    MarvelComicsAppPreview {
        DetailCharacterScreenConstraint(
            characterBundle = characterBundle,
            showComicsLoading = false,
            showSeriesLoading = false,
            listComics = null,
            listSeries = null
        )
    }
}
