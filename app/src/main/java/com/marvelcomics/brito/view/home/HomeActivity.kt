package com.marvelcomics.brito.view.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.marvelcomics.brito.databinding.ActivityMainBinding
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.hideKeyboard
import com.marvelcomics.brito.presentation.character.CharacterInteraction
import com.marvelcomics.brito.presentation.character.CharacterScreenState
import com.marvelcomics.brito.presentation.character.CharacterViewModel
import com.marvelcomics.brito.replaceFragment
import com.marvelcomics.brito.view.extensions.viewBinding
import com.marvelcomics.brito.view.home.fragment.character.CharacterFragment
import com.marvelcomics.brito.view.home.fragment.comics.ComicsFragment
import com.marvelcomics.brito.view.home.fragment.series.SeriesFragment
import com.marvelcomics.brito.view.mapper.CharacterViewMapper
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
                characterViewModel.bind().collect { newState ->
                    when (newState) {
                        is CharacterScreenState.Idle -> {
                            setupViews()
                        }
                        is CharacterScreenState.Loading -> {
                            showLoading()
                        }
                        is CharacterScreenState.Success -> {
                            buildSuccessScreen(newState.data as CharacterDomain)
                        }
                        is CharacterScreenState.Error -> {
                            showError(newState.exception as Exception, newState.message)
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
                buttonLoadLastCharacter.isVisible = false
                with(edittextMarvelCharacter.text.toString()) {
                    if (this.isNotBlank()) {
                        characterViewModel.handle(CharacterInteraction.SearchCharacter(this))
                        hideKeyboard()
                    }
                }
            }
            buttonLoadLastCharacter.setOnClickListener {
                buttonLoadLastCharacter.isVisible = false
                characterViewModel.handle(CharacterInteraction.LoadLastCharacter)
                hideKeyboard()
            }
        }
    }

    private fun showLoading() {
        with(bindings) {
            progressbarLoadingCharacter.visibility = View.VISIBLE
        }
    }

    private fun buildSuccessScreen(characterDomain: CharacterDomain) {
        with(bindings) {
            progressbarLoadingCharacter.visibility = View.GONE

            val characterSerializable = CharacterViewMapper().fromDomain(characterDomain)
            val characterFragment = CharacterFragment.newInstance(characterSerializable)

            replaceFragment(characterFragment, fragmentHomeCharacter.id)
            val comicsFragment = ComicsFragment.newInstance(characterDomain.id)
            replaceFragment(comicsFragment, fragmentHomeComics.id)
            val seriesFragment = SeriesFragment.newInstance(characterDomain.id)
            replaceFragment(seriesFragment, fragmentHomeSeries.id)
        }
    }

    private fun showError(exception: Throwable, message: String) {
        with(bindings) {
            progressbarLoadingCharacter.visibility = View.GONE
        }
        Toast.makeText(
            this,
            "Error: $message == ${exception.message}",
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
