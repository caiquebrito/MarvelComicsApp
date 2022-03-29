package com.marvelcomics.brito.view.home

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.marvelcomics.brito.presentation.HomeState

abstract class HomeScreen {

    interface Delegate {
        fun openSuccessFragments()
        fun onBackPressed()
        fun getCharacterNav(name: String)
        fun trackEvent(eventName: String)
    }

    abstract fun link(host: AppCompatActivity, delegate: Delegate): View

    abstract fun updateWith(newState: HomeState)
}