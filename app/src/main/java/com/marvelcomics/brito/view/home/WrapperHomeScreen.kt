package com.marvelcomics.brito.view.home

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.marvelcomics.brito.databinding.ActivityMainBinding
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.hideKeyboard
import com.marvelcomics.brito.presentation.HomeState
import com.marvelcomics.brito.replaceFragment
import com.marvelcomics.brito.view.home.fragment.character.CharacterFragment
import com.marvelcomics.brito.view.home.fragment.comics.ComicsFragment
import com.marvelcomics.brito.view.home.fragment.series.SeriesFragment

class WrapperHomeScreen : HomeScreen() {

    private lateinit var hostActivity: AppCompatActivity
    private lateinit var bindings: ActivityMainBinding
    private lateinit var screenDelegate: Delegate

    override fun link(host: AppCompatActivity, delegate: Delegate): View {
        hostActivity = host
        screenDelegate = delegate
        bindings = ActivityMainBinding.inflate(hostActivity.layoutInflater)
        return bindings.root
    }

    override fun updateWith(newState: HomeState) {
        when (newState) {
            is HomeState.Idle -> {
                setupViews()
            }
            is HomeState.Loading -> {
                showLoading()
            }
            is HomeState.Success -> {
                buildSuccessScreen(newState.data as CharacterEntity)
            }
            is HomeState.Error -> {
                showError(newState.exception)
            }
            is HomeState.Empty -> {
                //do nothing
            }
            is HomeState.NetworkError -> {
                showNetworkError()
            }
            else -> {

            }
        }
    }

    private fun setupViews() {
        bindings.apply {
            buttonSearchMarvelCharacter.setOnClickListener {
                edittextMarvelCharacter.text.toString().apply {
                    if (this.isNotBlank()) {
                        screenDelegate.getCharacterNav(this)
                        hostActivity.hideKeyboard()
                    }
                }
            }
        }
    }

    private fun showLoading() {
        bindings.apply {
            progressbarLoadingCharacter.visibility = View.VISIBLE
        }
    }

    private fun buildSuccessScreen(character: CharacterEntity) {
        bindings.apply {
            progressbarLoadingCharacter.visibility = View.GONE
            val characterFragment = CharacterFragment.newInstance(character)
            hostActivity.replaceFragment(characterFragment, fragmentHomeCharacter.id)
            val comicsFragment = ComicsFragment.newInstance(character.id)
            hostActivity.replaceFragment(comicsFragment, fragmentHomeComics.id)
            val seriesFragment = SeriesFragment.newInstance(character.id)
            hostActivity.replaceFragment(seriesFragment, fragmentHomeSeries.id)
        }
    }

    private fun showError(exception: Throwable) {
        bindings.apply {
            progressbarLoadingCharacter.visibility = View.GONE
        }
        Toast.makeText(
            hostActivity,
            "Error: ${exception.message}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showNetworkError() {
        bindings.apply {
            progressbarLoadingCharacter.visibility = View.GONE
        }
        Toast.makeText(
            hostActivity,
            "Error: Verifique sua conexão com a internet",
            Toast.LENGTH_LONG
        ).show()
    }
}