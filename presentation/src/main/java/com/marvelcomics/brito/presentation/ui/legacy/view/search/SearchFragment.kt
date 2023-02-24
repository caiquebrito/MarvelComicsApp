package com.marvelcomics.brito.presentation.ui.legacy.view.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvelcomics.brito.marvel.hideKeyboard
import com.marvelcomics.brito.presentation.R
import com.marvelcomics.brito.presentation.databinding.FragmentSearchBinding
import com.marvelcomics.brito.presentation.search.SearchUiEffect
import com.marvelcomics.brito.presentation.search.SearchUiState
import com.marvelcomics.brito.presentation.search.SearchViewModel
import com.marvelcomics.brito.presentation.ui.legacy.extensions.ItemOffSetDecorationHorizontal
import com.marvelcomics.brito.presentation.ui.legacy.extensions.dpToPx
import com.marvelcomics.brito.presentation.ui.legacy.extensions.onEffectTriggered
import com.marvelcomics.brito.presentation.ui.legacy.extensions.onStateChange
import com.marvelcomics.brito.presentation.ui.legacy.extensions.viewBinding
import com.marvelcomics.brito.presentation.ui.legacy.view.search.adapter.SearchCharacterAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModel()
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val navArgs by navArgs<SearchFragmentArgs>()

    private val actionsEnter = listOf(
        EditorInfo.IME_ACTION_NEXT,
        EditorInfo.IME_ACTION_GO,
        EditorInfo.IME_ACTION_DONE,
        EditorInfo.IME_ACTION_SEARCH
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFromExtras()
        initViews()

        onStateChange(viewModel, ::handleState)
        onEffectTriggered(viewModel, ::handleEffect)
    }

    private fun initFromExtras() {
        viewModel.setListIds(navArgs.characterListId.toList())
    }

    private fun initViews() = with(binding) {
        textinputedittextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionsEnter.contains(actionId)) {
                hideKeyboard()
                val searchName = textinputedittextSearch.text.toString()
                viewModel.searchCharacterByName(searchName)
                true
            } else {
                false
            }
        }
        recyclerviewSearch.addItemDecoration(
            ItemOffSetDecorationHorizontal(16.dpToPx(resources))
        )
        recyclerviewSearch.layoutManager = LinearLayoutManager(
            this.root.context,
            RecyclerView.HORIZONTAL,
            false
        )
    }

    private fun handleState(state: SearchUiState) = with(binding) {
        if (state.isIdle) {
            return
        }
        textviewSearchEmptyState.isVisible = false
        circularprogressindicatorSearch.isVisible = state.showLoading
        recyclerviewSearch.isVisible = state.showLoading.not()
        recyclerviewSearch.adapter = state.listCharacters?.let { list ->
            if (list.isEmpty()) {
                buildEmptyState()
                null
            } else {
                SearchCharacterAdapter(list) {
                    viewModel.addCharacterButtonClicked(it)
                }
            }
        }
    }

    private fun buildEmptyState() = with(binding) {
        textviewSearchEmptyState.isVisible = true
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
            "com.marvelcomics.brito.marvel.legacy.ui.search.INSERTED_CHARACTER"
    }
}
