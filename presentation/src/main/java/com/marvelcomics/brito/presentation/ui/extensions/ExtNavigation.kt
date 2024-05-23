package com.marvelcomics.brito.presentation.ui.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.marvelcomics.brito.presentation.R

fun defaultNavOptions(decorator: (NavOptionsBuilder.() -> Unit)? = null) =
    navOptions {
        anim {
            enter = R.anim.slide_in_left
            exit = R.anim.slide_out_right

            popEnter = R.anim.slide_in_right
            popExit = R.anim.slide_out_left
        }

        decorator?.invoke(this)
    }

val Fragment.navController: NavController
    get() = findNavController()

fun AppCompatActivity.navHostFragment(@IdRes viewId: Int): NavHostFragment =
    supportFragmentManager.findFragmentById(viewId) as NavHostFragment

fun NavController.createNavGraph(@NavigationRes navGraphResId: Int, @IdRes startDestinationId: Int): NavGraph {
    val navGraph = navInflater.inflate(navGraphResId)
    navGraph.setStartDestination(startDestinationId)

    return navGraph
}

fun Fragment.navigateTo(direction: NavDirections, navOptions: NavOptions? = defaultNavOptions()) {
    findNavController().navigate(direction, navOptions)
}

fun Fragment.navigateTo(@IdRes id: Int, navOptions: NavOptions? = defaultNavOptions(), bundle: Bundle? = null) {
    findNavController().navigate(id, bundle, navOptions)
}