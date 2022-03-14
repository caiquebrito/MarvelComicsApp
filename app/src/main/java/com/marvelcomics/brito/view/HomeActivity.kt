package com.marvelcomics.brito.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.marvelcomics.brito.databinding.ActivityMainBinding
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.hideKeyboard
import com.marvelcomics.brito.presentation.CharacterUiState
import com.marvelcomics.brito.presentation.GlobalUiState
import com.marvelcomics.brito.presentation.character.CharacterViewModel
import com.marvelcomics.brito.replaceFragment
import com.marvelcomics.brito.view.extensions.viewBinding
import com.marvelcomics.brito.view.fragment.character.CharacterFragment
import com.marvelcomics.brito.view.fragment.comics.ComicsFragment
import com.marvelcomics.brito.view.fragment.series.SeriesFragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val characterViewModel: CharacterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUi()
    }

    override fun onStart() {
        super.onStart()
        initObservers()
    }

    private fun initUi() {
        binding.buttonSearchMarvelCharacter.setOnClickListener {
            binding.edittextMarvelCharacter.text.toString().apply {
                if (this.isNotBlank()) {
                    getCharacterNav(this)
                    hideKeyboard()
                }
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                characterViewModel.characterUiState.collect {
                    when (it) {
                        is GlobalUiState.Loading -> {
                            binding.progressbarLoadingCharacter.visibility = View.VISIBLE
                        }
                        is GlobalUiState.NetworkError -> {
                            // do nothing
                        }
                        is CharacterUiState.Success -> {
                            binding.progressbarLoadingCharacter.visibility = View.GONE
                            val character = it.data as CharacterEntity
                            val characterFragment = CharacterFragment.newInstance(character)
                            replaceFragment(characterFragment, binding.fragmentHomeCharacter.id)
                            val comicsFragment = ComicsFragment.newInstance(character.id)
                            replaceFragment(comicsFragment, binding.fragmentHomeComics.id)
                            val seriesFragment = SeriesFragment.newInstance(character.id)
                            replaceFragment(seriesFragment, binding.fragmentHomeSeries.id)
                        }
                        is CharacterUiState.Error -> {
                            Toast.makeText(
                                this@HomeActivity,
                                "Error: ${it.exception.message}",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.progressbarLoadingCharacter.visibility = View.GONE
                        }
                        else -> {
                            // do nothing
                        }
                    }
                }
            }
        }
    }

    private fun getCharacterNav(name: String) {
        characterViewModel.loadCharacter(name)
    }
}
