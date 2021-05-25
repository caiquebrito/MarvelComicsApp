package com.marvelcomics.brito.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.marvelcomics.brito.databinding.ActivityMainBinding
import com.marvelcomics.brito.hideKeyboard
import com.marvelcomics.brito.replaceFragment
import com.marvelcomics.brito.view.extensions.viewBinding
import com.marvelcomics.brito.view.fragment.character.CharacterFragment
import com.marvelcomics.brito.view.fragment.comics.ComicsFragment
import com.marvelcomics.brito.view.fragment.series.SeriesFragment
import com.marvelcomics.brito.viewmodel.BaseUiState
import com.marvelcomics.brito.viewmodel.character.CharacterViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val characterViewModel: CharacterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUi()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            characterViewModel.characterUiState.collect {
                when (it) {
                    is BaseUiState.Success -> {
                        binding.progressbarLoadingCharacter.visibility = View.GONE
                        val characterFragment = CharacterFragment.newInstance(it.`object`)
                        replaceFragment(characterFragment, binding.fragmentHomeCharacter.id)
                        val comicsFragment = ComicsFragment.newInstance(it.`object`.id)
                        replaceFragment(comicsFragment, binding.fragmentHomeComics.id)
                        val seriesFragment = SeriesFragment.newInstance(it.`object`.id)
                        replaceFragment(seriesFragment, binding.fragmentHomeSeries.id)
                    }
                    is BaseUiState.Error -> {
                        Toast.makeText(
                            this@HomeActivity,
                            "Error: ${it.exception.message}",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.progressbarLoadingCharacter.visibility = View.GONE
                    }
                    is BaseUiState.Loading -> {
                        binding.progressbarLoadingCharacter.visibility = View.VISIBLE
                    }
                    is BaseUiState.NetworkError -> {
                        // do nothing
                    }
                    else -> {
                        // do nothing
                    }
                }
            }
        }
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

    private fun getCharacterNav(name: String) {
        characterViewModel.loadCharacter(name)
    }
}
