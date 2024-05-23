package com.marvelcomics.brito.presentation.home.ui.legacy

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.presentation.R
import com.marvelcomics.brito.presentation.databinding.FragmentHomeBinding
import com.marvelcomics.brito.presentation.home.HomeUiEffect
import com.marvelcomics.brito.presentation.home.HomeUiState
import com.marvelcomics.brito.presentation.home.HomeViewModel
import com.marvelcomics.brito.presentation.home.ui.legacy.adapter.HomeCardAdapter
import com.marvelcomics.brito.presentation.search.ui.legacy.SearchFragment
import com.marvelcomics.brito.presentation.ui.extensions.ItemOffSetDecorationHorizontal
import com.marvelcomics.brito.presentation.ui.extensions.animateFallRight
import com.marvelcomics.brito.presentation.ui.extensions.dpToPx
import com.marvelcomics.brito.presentation.ui.extensions.navigateTo
import com.marvelcomics.brito.presentation.ui.extensions.onEffectTriggered
import com.marvelcomics.brito.presentation.ui.extensions.onStateChange
import com.marvelcomics.brito.presentation.ui.extensions.openScreen
import com.marvelcomics.brito.presentation.ui.extensions.viewBinding
import com.marvelcomics.brito.presentation.ui.models.fromEntityToBundle
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModel()
    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNav()
        initViews()
        onStateChange(viewModel, ::handleStates)
        onEffectTriggered(viewModel, ::handleEffects)
    }

    private fun initNav() {
        findNavController().currentBackStackEntry?.savedStateHandle?.get<Boolean>(
            SearchFragment.INSERTED_CHARACTER
        )?.let {
            if (it) {
                viewModel.getLocalCharacters()
            }
        }
    }

    private fun initViews() = with(binding) {
        recyclerviewMarvelCharacters.addItemDecoration(
            ItemOffSetDecorationHorizontal(16.dpToPx(resources))
        )
        recyclerviewMarvelCharacters.layoutManager = LinearLayoutManager(
            this.root.context,
            RecyclerView.HORIZONTAL,
            false
        )
        imageviewMarvelSearch.setOnClickListener {
            viewModel.searchButtonClicked()
        }
    }

    private fun handleStates(state: HomeUiState) = with(binding) {
        circularprogressindicatorHome.isVisible = state.showLoading
        recyclerviewMarvelCharacters.isVisible = state.showLoading.not()
        if (state.showLoading) {
            viewModel.getLocalCharacters()
        }
        recyclerviewMarvelCharacters.adapter = state.listCharacters?.let { list ->
            if (list.isEmpty()) {
                getEmptyStateAdapter()
            } else {
                HomeCardAdapter(list) { entity ->
                    viewModel.adapterItemClicked(entity)
                }
            }
        } ?: getEmptyStateAdapter()
        recyclerviewMarvelCharacters.animateFallRight()
    }

    private fun handleEffects(effect: HomeUiEffect) {
        when (effect) {
            is HomeUiEffect.ShowError -> {
                Toast.makeText(requireContext(), "Show Error", Toast.LENGTH_LONG).show()
            }
            is HomeUiEffect.OpenSearchScreen -> {
                effect.ids?.let {
                    navigateTo(HomeFragmentDirections.navigateToSearchFragment(it.toIntArray()))
                }
            }
            is HomeUiEffect.OpenDetailScreen -> {
                navigateTo(
                    HomeFragmentDirections.navigateToDetailCharacterFragment(
                        effect.entity.fromEntityToBundle()
                    )
                )
            }
        }
    }

    private fun getEmptyStateAdapter(): HomeCardAdapter {
        return HomeCardAdapter(
            listOf(
                CharacterEntity(
                    id = 0,
                    name = "Click to Add",
                    description = null,
                    thumbnailEntity = null
                )
            )
        ) {
            viewModel.emptyButtonItemClicked()
        }
    }
}
