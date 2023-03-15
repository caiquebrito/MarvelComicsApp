package com.marvelcomics.brito.presentation.main.ui.compose

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.Navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.marvelcomics.brito.presentation.NavGraphs
import com.marvelcomics.brito.presentation.R
import com.marvelcomics.brito.presentation.databinding.ActivityMainComposeBinding
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppTheme
import com.marvelcomics.brito.presentation.ui.extensions.viewBinding
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine

class MainComposeActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainComposeBinding::inflate)
    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment_compose)
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
        setContent {
            MarvelComicsAppTheme {
                val navController = rememberAnimatedNavController()
                BackHandler {
                    navController.navigateUp()
                }
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    engine = rememberAnimatedNavHostEngine(),
                    navController = navController
                )
            }
        }
    }
}
