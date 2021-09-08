package com.marvelcomics.brito.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.marvelcomics.brito.databinding.ActivityMainBinding
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.hideKeyboard
import com.marvelcomics.brito.replaceFragment
import com.marvelcomics.brito.view.extensions.viewBinding
import com.marvelcomics.brito.view.fragment.character.CharacterFragment
import com.marvelcomics.brito.view.fragment.comics.ComicsFragment
import com.marvelcomics.brito.view.fragment.series.SeriesFragment
import com.marvelcomics.brito.presentation.CharacterUiState
import com.marvelcomics.brito.presentation.GlobalUiState
import com.marvelcomics.brito.presentation.character.CharacterViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val characterViewModel: CharacterViewModel by viewModel()

    private var uiStateJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUi()
    }

    override fun onStart() {
        super.onStart()
        initObservers()
    }

    override fun onStop() {
        super.onStop()
        uiStateJob?.cancel()
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
        uiStateJob = lifecycleScope.launchWhenStarted {
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

    private fun getCharacterNav(name: String) {
        characterViewModel.loadCharacter(name)
    }
}
