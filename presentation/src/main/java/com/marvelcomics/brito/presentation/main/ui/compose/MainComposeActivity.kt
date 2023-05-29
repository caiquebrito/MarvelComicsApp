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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.marvelcomics.brito.presentation.NavGraphs
import com.marvelcomics.brito.presentation.home.HomeViewModel
import com.marvelcomics.brito.presentation.ui.compose.components.ProgressBar
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppPreview
import com.marvelcomics.brito.presentation.ui.compose.theme.MarvelComicsAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.scope.ScopeActivity

class MainComposeActivity : ScopeActivity() {

    private var navController: NavHostController? = null
    private var currentProgressValue = 0
    private val listener = MarvelScreenDirections.getNavDestinationListener {
        currentProgressValue = it
    }

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
                    handleBackWithNav(this, navController)
                }
                MainComposeScreen(
                    activity = this,
                    navController = navController,
                    currentProgressValue = currentProgressValue
                )
            }
        }
    }
}

private fun handleBackWithNav(activity: AppCompatActivity, navController: NavHostController?) {
    navController?.apply {
        if (backQueue.isEmpty()) {
            activity.onBackPressed()
        } else {
            navigateUp()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun MainComposeScreen(
    activity: AppCompatActivity,
    navController: NavHostController?,
    currentProgressValue: Int
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val onBackButtonClicked: () -> Unit = {
            handleBackWithNav(activity, navController)
        }
        MainComposeTabComponent(
            onBackButtonClicked = onBackButtonClicked,
            currentProgressValue = currentProgressValue
        )
        navController?.let {
            val homeViewModel = koinViewModel<HomeViewModel>()
            DestinationsNavHost(
                navGraph = NavGraphs.home,
                engine = rememberAnimatedNavHostEngine(),
                navController = it,
                dependenciesContainerBuilder = {
                    dependency(NavGraphs.home) {
                        homeViewModel
                    }
                }
            )
        }
    }
}

@Composable
fun MainComposeTabComponent(
    onBackButtonClicked: () -> Unit,
    currentProgressValue: Int
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
            indicatorProgress = currentProgressValue.toFloat(),
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
        val activity = AppCompatActivity()
        val navController = rememberAnimatedNavController()
        BackHandler {
            handleBackWithNav(activity, navController)
        }
        MainComposeScreen(
            activity,
            navController,
            30
        )
    }
}
