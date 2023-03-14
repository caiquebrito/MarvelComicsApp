package com.marvelcomics.brito.presentation.main.ui.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.marvelcomics.brito.presentation.R
import com.marvelcomics.brito.presentation.databinding.ActivityMainComposeBinding
import com.marvelcomics.brito.presentation.ui.extensions.viewBinding

class MainComposeActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainComposeBinding::inflate)
    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment_compose)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /*
        * setContent {
            MarvelComicsAppTheme {
                val navController = rememberAnimatedNavController()
                BackHandler {
                    navController.navigateUp()
                }
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    engine = rememberAnimatedNavHostEngine(),
                    navController = navController,
                )
            }
        }
        * */
    }
}
