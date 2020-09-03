package com.marvelcomics.brito.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.marvelcomics.brito.R
import com.marvelcomics.brito.addFragment
import com.marvelcomics.brito.data.datasource.remote.mapper.CharacterMapper
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.hideKeyboard
import com.marvelcomics.brito.replaceFragment
import com.marvelcomics.brito.view.fragment.character.CharacterFragment
import com.marvelcomics.brito.view.fragment.comics.ComicsFragment
import com.marvelcomics.brito.view.fragment.series.SeriesFragment
import com.marvelcomics.brito.viewmodel.character.CharacterUiState
import com.marvelcomics.brito.viewmodel.character.CharacterViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val characterViewModel: CharacterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
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
        characterViewModel.character.observe(this, Observer {
            when (it) {
                is CharacterUiState.Success -> {
                    progressbar_loading_character.visibility = View.GONE
                    val characterFragment = CharacterFragment.newInstance(it.character)
                    replaceFragment(characterFragment, fragment_home_character.id)
                    val comicsFragment = ComicsFragment.newInstance(it.character.id)
                    replaceFragment(comicsFragment, fragment_home_comics.id)
                    val seriesFragment = SeriesFragment.newInstance(it.character.id)
                    replaceFragment(seriesFragment, fragment_home_series.id)
                }
                is CharacterUiState.Error -> {
                    Toast.makeText(this, "Error: ${it.exception.message}", Toast.LENGTH_LONG).show()
                    progressbar_loading_character.visibility = View.GONE
                }
                is CharacterUiState.Loading -> {
                    progressbar_loading_character.visibility = View.VISIBLE
                }
            }
        })
        characterViewModel.characterName.value = name
    }
}
