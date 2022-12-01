package com.marvelcomics.brito.view.legacy.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvelcomics.brito.databinding.ActivitySearchBinding
import com.marvelcomics.brito.presentation.search.SearchUiEffect
import com.marvelcomics.brito.presentation.search.SearchUiState
import com.marvelcomics.brito.presentation.search.SearchViewModel
import com.marvelcomics.brito.view.hideKeyboard
import com.marvelcomics.brito.view.legacy.extensions.ItemOffSetDecorationHorizontal
import com.marvelcomics.brito.view.legacy.extensions.dpToPx
import com.marvelcomics.brito.view.legacy.extensions.onEffectTriggered
import com.marvelcomics.brito.view.legacy.extensions.onStateChange
import com.marvelcomics.brito.view.legacy.extensions.viewBinding
import com.marvelcomics.brito.view.legacy.ui.search.adapter.SearchCharacterAdapter
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
        recyclerviewSearch.addItemDecoration(
            ItemOffSetDecorationHorizontal(16.dpToPx(resources))
        )
        recyclerviewSearch.layoutManager = LinearLayoutManager(
            this.root.context, RecyclerView.HORIZONTAL, false
        )
    }

    private fun handleState(state: SearchUiState) = with(binding) {
        // loading.visible = state.isLoading
        state.listCharacters?.let {
            recyclerviewSearch.adapter = SearchCharacterAdapter(it) {
                viewModel.addCharacterButtonClicked(it)
            }
        } ?: buildEmptyState()
    }

    private fun buildEmptyState() {
        Toast.makeText(this, "Empty State Screen", Toast.LENGTH_LONG).show()
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
        }
    }
}
