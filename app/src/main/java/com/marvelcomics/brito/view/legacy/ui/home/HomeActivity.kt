package com.marvelcomics.brito.view.legacy.ui.home

import android.os.Bundle
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
import com.marvelcomics.brito.view.legacy.ui.home.adapter.ItemOffSetDecorationHorizontal
import com.marvelcomics.brito.view.legacy.ui.home.adapter.MarvelHeroesCardAdapter
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
        binding.recyclerviewMarvelCharacters.addItemDecoration(ItemOffSetDecorationHorizontal(8))
        binding.recyclerviewMarvelCharacters.layoutManager =
            LinearLayoutManager(this.root.context, RecyclerView.HORIZONTAL, false)
    }

    private fun handleStates(state: HomeUiState) = with(binding) {
        // loading.visible = state.isLoading
        recyclerviewMarvelCharacters.adapter = state.heroesInfo?.let {
            MarvelHeroesCardAdapter(it) {
                // send effect show details hero
            }
        }
    }

    private fun handleEffects(effect: HomeUiEffect) = when (effect) {
        is HomeUiEffect.ShowEmptyHeroes -> {
            binding.recyclerviewMarvelCharacters.adapter = getEmptyStateAdapter()
        }
        is HomeUiEffect.OpenSearchScreen -> {
            // open search activity
        }
        else -> {}
    }

    private fun getEmptyStateAdapter(): MarvelHeroesCardAdapter {
        return MarvelHeroesCardAdapter(
            listOf(
                CharacterDomain(
                    id = 0,
                    name = "Empty List",
                    description = null,
                    thumbnailDomain = null
                )
            )
        ) {
            viewModel.openSearchScreen()
        }
    }
}
