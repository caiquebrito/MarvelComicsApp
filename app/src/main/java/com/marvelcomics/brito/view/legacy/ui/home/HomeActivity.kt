package com.marvelcomics.brito.view.legacy.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvelcomics.brito.databinding.ActivityMainBinding
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.presentation.home.HomeUiEffect
import com.marvelcomics.brito.presentation.home.HomeUiState
import com.marvelcomics.brito.presentation.home.HomeViewModel
import com.marvelcomics.brito.view.legacy.extensions.ItemOffSetDecorationHorizontal
import com.marvelcomics.brito.view.legacy.extensions.animateFallRight
import com.marvelcomics.brito.view.legacy.extensions.dpToPx
import com.marvelcomics.brito.view.legacy.extensions.onEffectTriggered
import com.marvelcomics.brito.view.legacy.extensions.onStateChange
import com.marvelcomics.brito.view.legacy.extensions.viewBinding
import com.marvelcomics.brito.view.legacy.ui.details.DetailCharacterActivityArgs
import com.marvelcomics.brito.view.legacy.ui.home.adapter.HomeCardAdapter
import com.marvelcomics.brito.view.legacy.ui.search.SearchActivityArgs
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding by viewBinding(ActivityMainBinding::inflate)

    private var registerSearchActivityResult: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        initObservers()

        onStateChange(viewModel, ::handleStates)
        onEffectTriggered(viewModel, ::handleEffects)
    }

    private fun initObservers() {
        registerSearchActivityResult =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
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
                Toast.makeText(this, "Show Error", Toast.LENGTH_LONG).show()
            }
            is HomeUiEffect.OpenSearchScreen -> {
                effect.ids?.let {
                    val intent = SearchActivityArgs(it).build(this@HomeActivity)
                    registerSearchActivityResult?.launch(intent)
                }
            }
            is HomeUiEffect.OpenDetailScreen -> {
                startActivity(
                    DetailCharacterActivityArgs(effect.entity)
                        .build(this@HomeActivity)
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
