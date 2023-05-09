package com.marvelcomics.brito.presentation.main.ui.compose

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.marvelcomics.brito.presentation.NavGraphs
import com.marvelcomics.brito.presentation.ui.compose.components.ProgressBar
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppPreview
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine

class MainComposeActivity : AppCompatActivity() {

    private var navController: NavHostController? = null
    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        when (destination.route) {
            MarvelScreenDirections.HOME_SCREEN.route ->
                currentProgressValue =
                    MarvelScreenDirections.HOME_SCREEN.progress

            MarvelScreenDirections.SEARCH_SCREEN.route ->
                currentProgressValue =
                    MarvelScreenDirections.SEARCH_SCREEN.progress

            MarvelScreenDirections.DETAIL_SCREEN.route ->
                currentProgressValue =
                    MarvelScreenDirections.DETAIL_SCREEN.progress
        }
    }
    private var currentProgressValue = 0f

    override fun onResume() {
        super.onResume()
        addNavListener()
    }

    override fun onPause() {
        navController?.removeOnDestinationChangedListener(listener)
        super.onPause()
    }

    private fun addNavListener() {
        navController?.addOnDestinationChangedListener(listener)
    }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelComicsAppTheme {
                navController = rememberAnimatedNavController()
                addNavListener()
                BackHandler {
                    navController?.navigateUp()
                }
                val progressState = remember { mutableStateOf(currentProgressValue) }
                MainComposeScreen(
                    navController = navController,
                    currentProgressValue = progressState.value
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun MainComposeScreen(navController: NavHostController?, currentProgressValue: Float) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val onBackButtonClicked: () -> Unit = { navController?.navigateUp() }
        MainComposeTabComponent(
            onBackButtonClicked = onBackButtonClicked,
            currentProgressValue = currentProgressValue
        )
        navController?.let {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                engine = rememberAnimatedNavHostEngine(),
                navController = it
            )
        }
    }
}

@Composable
fun MainComposeTabComponent(
    onBackButtonClicked: () -> Unit,
    currentProgressValue: Float
) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Red)
                .clickable { onBackButtonClicked.invoke() }
        ) {
        }
        ProgressBar(
            indicatorProgress = currentProgressValue,
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .padding(end = 16.dp, start = 16.dp)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun MainComposeScreenPreview() {
    MarvelComicsAppPreview {
        val navController = rememberAnimatedNavController()
        BackHandler {
            navController.navigateUp()
        }
        MainComposeScreen(navController, 30)
    }
}
