package com.marvelcomics.brito.view.legacy.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.marvelcomics.brito.databinding.ActivitySearchBinding
import com.marvelcomics.brito.presentation.search.SearchUiEffect
import com.marvelcomics.brito.presentation.search.SearchUiState
import com.marvelcomics.brito.presentation.search.SearchViewModel
import com.marvelcomics.brito.view.hideKeyboard
import com.marvelcomics.brito.view.legacy.extensions.onEffectTriggered
import com.marvelcomics.brito.view.legacy.extensions.onStateChange
import com.marvelcomics.brito.view.legacy.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModel()
    private val binding by viewBinding(ActivitySearchBinding::inflate)

    private val actionsEnter = listOf(
        EditorInfo.IME_ACTION_NEXT,
        EditorInfo.IME_ACTION_GO,
        EditorInfo.IME_ACTION_DONE,
        EditorInfo.IME_ACTION_SEARCH
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()

        onStateChange(viewModel, ::handleState)
        onEffectTriggered(viewModel, ::handleEffect)
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
    }

    private fun handleState(state: SearchUiState) = with(binding) {
        // loading.visible = state.isLoading
        state.listCharacters?.let {
//            recyclerviewSearch = adapter
        }
    }

    private fun handleEffect(effect: SearchUiEffect) {
        when (effect) {
            SearchUiEffect.ShowError -> {
            }
        }
    }
}
