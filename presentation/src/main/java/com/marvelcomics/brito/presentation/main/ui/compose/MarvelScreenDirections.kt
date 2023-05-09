package com.marvelcomics.brito.presentation.main.ui.compose

import com.marvelcomics.brito.presentation.destinations.DetailCharacterDestScreenDestination
import com.marvelcomics.brito.presentation.destinations.HomeComposeDestScreenDestination
import com.marvelcomics.brito.presentation.destinations.SearchComposeDestScreenDestination

enum class MarvelScreenDirections(val route: String, val progress: Float) {
    HOME_SCREEN(HomeComposeDestScreenDestination.route, 20f),
    SEARCH_SCREEN(SearchComposeDestScreenDestination.route, 60f),
    DETAIL_SCREEN(DetailCharacterDestScreenDestination.route, 90f)
}
