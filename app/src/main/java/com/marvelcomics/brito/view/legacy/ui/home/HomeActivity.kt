package com.marvelcomics.brito.view.legacy.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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
import com.marvelcomics.brito.view.legacy.ui.home.adapter.HomeCardAdapter
import com.marvelcomics.brito.view.legacy.ui.search.SearchActivity
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
        Log.i(
            "AbstractViewModel",
            "Home Handle State: ${state.showLoading} // ${state.listCharacters}"
        )
        // loading.visible = state.isLoading
        if (state.showLoading) {
            viewModel.getLocalCharacters()
        }
        recyclerviewMarvelCharacters.adapter = state.listCharacters?.let {
            HomeCardAdapter(it) {
                Toast.makeText(this@HomeActivity, "Load Details Screen", Toast.LENGTH_LONG).show()
            }
        } ?: getEmptyStateAdapter()
        recyclerviewMarvelCharacters.animateFallRight()
    }

    private fun handleEffects(effect: HomeUiEffect) {
        Log.i("AbstractViewModel", "Home Handle Effects: $effect")
        when (effect) {
            is HomeUiEffect.ShowError -> {
                Toast.makeText(this, "Show Error", Toast.LENGTH_LONG).show()
            }
            is HomeUiEffect.OpenSearchScreen -> {
                registerSearchActivityResult?.launch(
                    Intent(this, SearchActivity::class.java)
                )
            }
            else -> {}
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
