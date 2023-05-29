package com.marvelcomics.brito.presentation.main.ui.compose

import androidx.navigation.NavController
import com.marvelcomics.brito.presentation.destinations.DetailCharacterScreenDestination
import com.marvelcomics.brito.presentation.destinations.HomeComposeScreenDestination
import com.marvelcomics.brito.presentation.destinations.SearchComposeScreenDestination

enum class MarvelScreenDirections(val route: String, val progress: Int) {
    HOME_SCREEN(HomeComposeScreenDestination.route, 20),
    SEARCH_SCREEN(SearchComposeScreenDestination.route, 60),
    DETAIL_SCREEN(DetailCharacterScreenDestination.route, 90);

    companion object {
        fun getNavDestinationListener(callbackProgress: (progress: Int) -> Unit) =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                callbackProgress(
                    when (destination.route) {
                        HOME_SCREEN.route -> HOME_SCREEN.progress
                        SEARCH_SCREEN.route -> SEARCH_SCREEN.progress
                        DETAIL_SCREEN.route -> DETAIL_SCREEN.progress
                        else -> {
                            0
                        }
                    }
                )
            }
    }
}
