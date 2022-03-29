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
import com.marvelcomics.brito.hideKeyboard
import com.marvelcomics.brito.presentation.HomeState
import com.marvelcomics.brito.presentation.character.CharacterViewModel
import com.marvelcomics.brito.replaceFragment
import com.marvelcomics.brito.view.extensions.viewBinding
import com.marvelcomics.brito.view.home.fragment.character.CharacterFragment
import com.marvelcomics.brito.view.home.fragment.comics.ComicsFragment
import com.marvelcomics.brito.view.home.fragment.series.SeriesFragment
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private val characterViewModel: CharacterViewModel by viewModel()
    private val screen = WrapperHomeScreen()

    private val delegate by lazy {
        object : HomeScreen.Delegate {
            override fun openSuccessFragments() {
                TODO("Not yet implemented")
            }

            override fun onBackPressed() {
                onBackPressed()
            }

            override fun getCharacterNav(name: String) {
                characterViewModel.loadCharacter(name)
            }

            override fun trackEvent(eventName: String) {
                TODO("Not yet implemented")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(screen.link(this, delegate))
        initUi()
    }

    override fun onStart() {
        super.onStart()
        initObservers()
    }

    private fun initUi() {

    }

    private fun initObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                characterViewModel.characterUiState.collect {
                    when (it) {

                    }
                }
            }
        }
    }
}
