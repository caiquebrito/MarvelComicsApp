package com.marvelcomics.brito.presentation.search.ui.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.marvelcomics.brito.presentation.search.SearchUiEffect
import com.marvelcomics.brito.presentation.search.SearchViewModel
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsEffect
import com.marvelcomics.brito.presentation.ui.compose.extension.collectAsStateWithLifecycle
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchComposeFragment : Fragment() {

    private val viewModel by viewModel<SearchViewModel>()
    private val navArgs by navArgs<SearchComposeFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.state.collectAsStateWithLifecycle().value
                viewModel.effect.collectAsEffect(::handleEffect)
                MarvelComicsAppTheme {
                    SearchScreen(
                        showLoading = state.showLoading,
                        listCharacters = state.listCharacters,
                        searchCharacterByName = viewModel::searchCharacterByName,
                        addCharacterButtonClick = viewModel::addCharacterButtonClicked
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFromExtras()
    }

    private fun initFromExtras() {
        viewModel.setListIds(navArgs.characterListId.toList())
    }

    private fun handleEffect(effect: SearchUiEffect) {
        when (effect) {
            SearchUiEffect.ShowError -> {
                Toast.makeText(requireContext(), "Show Error", Toast.LENGTH_LONG).show()
            }
            SearchUiEffect.BackToHome -> {
                val navController = findNavController()
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    INSERTED_CHARACTER,
                    true
                )
                navController.navigateUp()
            }
            SearchUiEffect.ShowAlreadyAddedError -> {
                Toast.makeText(requireContext(), "Character Already Added", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    companion object {
        const val INSERTED_CHARACTER =
            "com.marvelcomics.brito.presentation.search.ui.compose.INSERTED_CHARACTER"
    }
}
