package com.marvelcomics.brito.view.legacy.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvelcomics.brito.databinding.ActivityMainBinding
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.presentation.home.HomeUiEffect
import com.marvelcomics.brito.presentation.home.HomeUiState
import com.marvelcomics.brito.presentation.home.HomeViewModel
import com.marvelcomics.brito.view.legacy.extensions.onEffectTriggered
import com.marvelcomics.brito.view.legacy.extensions.onStateChange
import com.marvelcomics.brito.view.legacy.extensions.viewBinding
import com.marvelcomics.brito.view.legacy.ui.dpToPx
import com.marvelcomics.brito.view.legacy.ui.home.adapter.ItemOffSetDecorationHorizontal
import com.marvelcomics.brito.view.legacy.ui.home.adapter.MarvelHeroesCardAdapter
import com.marvelcomics.brito.view.legacy.ui.search.SearchActivity
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()

        onStateChange(viewModel, ::handleStates)
        onEffectTriggered(viewModel, ::handleEffects)
    }

    private fun initViews() = with(binding) {
        binding.recyclerviewMarvelCharacters.addItemDecoration(
            ItemOffSetDecorationHorizontal(16.dpToPx(resources))
        )
        recyclerviewMarvelCharacters.layoutManager = LinearLayoutManager(
            this.root.context, RecyclerView.HORIZONTAL, false
        )
        imageviewMarvelSearch.setOnClickListener {
            viewModel.searchButtonClicked()
        }
    }

    private fun handleStates(state: HomeUiState) = with(binding) {
        Log.i(
            "AbstractViewModel",
            "Home Handle State: ${state.showLoading} // ${state.listCharacters}"
        )
        // loading.visible = state.isLoading
        if (state.showLoading) {
            viewModel.getHeroesLocal()
        }
        recyclerviewMarvelCharacters.adapter = state.listCharacters?.let {
            MarvelHeroesCardAdapter(it) {
                // send effect show details hero
            }
        } ?: getEmptyStateAdapter()
    }

    private fun handleEffects(effect: HomeUiEffect) {
        Log.i("AbstractViewModel", "Home Handle Effects: $effect")
        when (effect) {
            is HomeUiEffect.ShowError -> {
                // show toast error
            }
            is HomeUiEffect.OpenSearchScreen -> {
                startActivity(Intent(this, SearchActivity::class.java))
            }
            else -> {}
        }
    }

    private fun getEmptyStateAdapter(): MarvelHeroesCardAdapter {
        return MarvelHeroesCardAdapter(
            listOf(
                CharacterDomain(
                    id = 0,
                    name = "Click to search",
                    description = null,
                    thumbnailDomain = null
                )
            )
        ) {
            viewModel.emptyButtonItemClicked()
        }
    }
}
