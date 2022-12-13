package com.marvelcomics.brito.marvel.legacy.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvelcomics.brito.marvel.databinding.ActivitySearchBinding
import com.marvelcomics.brito.marvel.hideKeyboard
import com.marvelcomics.brito.marvel.legacy.extensions.ItemOffSetDecorationHorizontal
import com.marvelcomics.brito.marvel.legacy.extensions.dpToPx
import com.marvelcomics.brito.marvel.legacy.extensions.onEffectTriggered
import com.marvelcomics.brito.marvel.legacy.extensions.onStateChange
import com.marvelcomics.brito.marvel.legacy.extensions.viewBinding
import com.marvelcomics.brito.marvel.legacy.ui.search.adapter.SearchCharacterAdapter
import com.marvelcomics.brito.presentation.search.SearchUiEffect
import com.marvelcomics.brito.presentation.search.SearchUiState
import com.marvelcomics.brito.presentation.search.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModel()
    private val binding by viewBinding(ActivitySearchBinding::inflate)

    private val args: SearchActivityArgs by lazy {
        SearchActivityArgs.deserializeFrom(intent)
    }

    private val actionsEnter = listOf(
        EditorInfo.IME_ACTION_NEXT,
        EditorInfo.IME_ACTION_GO,
        EditorInfo.IME_ACTION_DONE,
        EditorInfo.IME_ACTION_SEARCH
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initFromExtras()
        initViews()

        onStateChange(viewModel, ::handleState)
        onEffectTriggered(viewModel, ::handleEffect)
    }

    private fun initFromExtras() {
        viewModel.setListIds(args.listIds)
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
            this.root.context, RecyclerView.HORIZONTAL, false
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
                Toast.makeText(this, "Show Error", Toast.LENGTH_LONG).show()
            }
            SearchUiEffect.BackToHome -> {
                setResult(RESULT_OK)
                onBackPressed()
            }
            SearchUiEffect.ShowAlreadyAddedError -> {
                Toast.makeText(this, "Character Already Added", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}
