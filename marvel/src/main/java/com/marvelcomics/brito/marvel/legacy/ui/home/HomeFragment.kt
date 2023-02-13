package com.marvelcomics.brito.marvel.legacy.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.marvel.R
import com.marvelcomics.brito.marvel.databinding.FragmentHomeBinding
import com.marvelcomics.brito.marvel.legacy.extensions.ItemOffSetDecorationHorizontal
import com.marvelcomics.brito.marvel.legacy.extensions.animateFallRight
import com.marvelcomics.brito.marvel.legacy.extensions.dpToPx
import com.marvelcomics.brito.marvel.legacy.extensions.onEffectTriggered
import com.marvelcomics.brito.marvel.legacy.extensions.onStateChange
import com.marvelcomics.brito.marvel.legacy.extensions.openScreen
import com.marvelcomics.brito.marvel.legacy.extensions.viewBinding
import com.marvelcomics.brito.marvel.legacy.ui.home.adapter.HomeCardAdapter
import com.marvelcomics.brito.marvel.legacy.ui.models.fromEntityToBundle
import com.marvelcomics.brito.marvel.legacy.ui.search.SearchFragment
import com.marvelcomics.brito.presentation.home.HomeUiEffect
import com.marvelcomics.brito.presentation.home.HomeUiState
import com.marvelcomics.brito.presentation.home.HomeViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModel()
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private var registerSearchActivityResult: ActivityResultLauncher<Intent>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNav()
        initViews()
        initObservers()
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
            this.root.context, RecyclerView.HORIZONTAL, false
        )
        imageviewMarvelSearch.setOnClickListener {
            viewModel.searchButtonClicked()
        }
    }

    private fun initObservers() {
        registerSearchActivityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.getLocalCharacters()
            }
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
                    openScreen(HomeFragmentDirections.navigateToSearchFragment(it.toIntArray()))
                }
            }
            is HomeUiEffect.OpenDetailScreen -> {
                openScreen(
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
                    id = 0, name = "Click to Add", description = null, thumbnailEntity = null
                )
            )
        ) {
            viewModel.emptyButtonItemClicked()
        }
    }
}
