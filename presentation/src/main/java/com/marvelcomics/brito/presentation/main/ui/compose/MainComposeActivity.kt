package com.marvelcomics.brito.presentation.main.ui.compose

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.marvelcomics.brito.presentation.NavGraphs
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine

class MainComposeActivity : AppCompatActivity() {

//    private val binding by viewBinding(ActivityMainComposeBinding::inflate)
//    private val navController by lazy {
//        Navigation.findNavController(this, R.id.nav_host_fragment_compose)
//    }

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
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TopBar()
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        engine = rememberAnimatedNavHostEngine(),
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = "Sample Toolbar Project"
        )
    }
}
