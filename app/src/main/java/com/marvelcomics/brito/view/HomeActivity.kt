package com.marvelcomics.brito.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.marvelcomics.brito.R
import com.marvelcomics.brito.hideKeyboard
import com.marvelcomics.brito.replaceFragment
import com.marvelcomics.brito.view.fragment.character.CharacterFragment
import com.marvelcomics.brito.view.fragment.comics.ComicsFragment
import com.marvelcomics.brito.view.fragment.series.SeriesFragment
import com.marvelcomics.brito.viewmodel.BaseUiState
import com.marvelcomics.brito.viewmodel.character.CharacterViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.android.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private val characterViewModel: CharacterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            characterViewModel.characterUiState.collect {
                when (it) {
                    is BaseUiState.Success -> {
                        progressbar_loading_character.visibility = View.GONE
                        val characterFragment = CharacterFragment.newInstance(it.`object`)
                        replaceFragment(characterFragment, fragment_home_character.id)
                        val comicsFragment = ComicsFragment.newInstance(it.`object`.id)
                        replaceFragment(comicsFragment, fragment_home_comics.id)
                        val seriesFragment = SeriesFragment.newInstance(it.`object`.id)
                        replaceFragment(seriesFragment, fragment_home_series.id)
                    }
                    is BaseUiState.Error -> {
                        Toast.makeText(
                            this@HomeActivity,
                            "Error: ${it.exception.message}",
                            Toast.LENGTH_LONG
                        ).show()
                        progressbar_loading_character.visibility = View.GONE
                    }
                    is BaseUiState.Loading -> {
                        progressbar_loading_character.visibility = View.VISIBLE
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
        button_search_marvel_character.onClick {
            edittext_marvel_character.text.toString().apply {
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
