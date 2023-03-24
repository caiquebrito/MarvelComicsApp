package com.marvelcomics.brito.presentation.main.ui.compose

import com.marvelcomics.brito.presentation.destinations.DetailCharacterDestScreenDestination
import com.marvelcomics.brito.presentation.destinations.HomeComposeDestScreenDestination
import com.marvelcomics.brito.presentation.destinations.SearchComposeDestScreenDestination

enum class MarvelScreenDirections(val route: String, val progress: Int) {
    HOME_SCREEN(HomeComposeDestScreenDestination.route, 20),
    SEARCH_SCREEN(SearchComposeDestScreenDestination.route, 60),
    DETAIL_SCREEN(DetailCharacterDestScreenDestination.route, 90)
}
