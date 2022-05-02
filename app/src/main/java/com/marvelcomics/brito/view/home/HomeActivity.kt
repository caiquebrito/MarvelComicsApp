package com.marvelcomics.brito.view.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.marvelcomics.brito.databinding.ActivityMainBinding
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.usecase.CoroutineUseCase
import com.marvelcomics.brito.hideKeyboard
import com.marvelcomics.brito.presentation.character.CharacterInteraction
import com.marvelcomics.brito.presentation.character.CharacterScreenState
import com.marvelcomics.brito.presentation.character.CharacterViewModel
import com.marvelcomics.brito.replaceFragment
import com.marvelcomics.brito.view.extensions.viewBinding
import com.marvelcomics.brito.view.home.fragment.character.CharacterFragment
import com.marvelcomics.brito.view.home.fragment.comics.ComicsFragment
import com.marvelcomics.brito.view.home.fragment.series.SeriesFragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

@InternalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private val characterViewModel: CharacterViewModel by viewModel()
    private val bindings by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindings.root)
        initObservers()
        setupViews()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                characterViewModel.characterUiState.collect { newState ->
                    when (newState) {
                        is CharacterScreenState.Idle -> {
                            setupViews()
                        }
                        is CharacterScreenState.Loading -> {
                            showLoading()
                        }
                        is CharacterScreenState.Success -> {
                            buildSuccessScreen((newState.data as CoroutineUseCase.Result.Success<CharacterEntity>).result)
                        }
                        is CharacterScreenState.Error -> {
                            showError(newState.exception as Exception)
                        }
                        is CharacterScreenState.Empty -> {
                            // do nothing
                        }
                        is CharacterScreenState.NetworkError -> {
                            showNetworkError()
                        }
                        else -> {
                            Toast.makeText(
                                this@HomeActivity,
                                "Error Not Identified - $newState",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupViews() {
        with(bindings) {
            buttonSearchMarvelCharacter.setOnClickListener {
                with(edittextMarvelCharacter.text.toString()) {
                    if (this.isNotBlank()) {
                        characterViewModel.handle(CharacterInteraction.SearchCharacter(this))
                        hideKeyboard()
                    }
                }
            }
        }
    }

    private fun showLoading() {
        with(bindings) {
            progressbarLoadingCharacter.visibility = View.VISIBLE
        }
    }

    private fun buildSuccessScreen(character: CharacterEntity) {
        with(bindings) {
            progressbarLoadingCharacter.visibility = View.GONE
            val characterFragment = CharacterFragment.newInstance(character)
            replaceFragment(characterFragment, fragmentHomeCharacter.id)
            val comicsFragment = ComicsFragment.newInstance(character.id)
            replaceFragment(comicsFragment, fragmentHomeComics.id)
            val seriesFragment = SeriesFragment.newInstance(character.id)
            replaceFragment(seriesFragment, fragmentHomeSeries.id)
        }
    }

    private fun showError(exception: Throwable) {
        with(bindings) {
            progressbarLoadingCharacter.visibility = View.GONE
        }
        Toast.makeText(
            this,
            "Error: ${exception.message}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showNetworkError() {
        with(bindings) {
            progressbarLoadingCharacter.visibility = View.GONE
        }
        Toast.makeText(
            this,
            "Error: Verifique sua conexão com a internet",
            Toast.LENGTH_LONG
        ).show()
    }
}
