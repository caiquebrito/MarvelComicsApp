package com.marvelcomics.brito.view.legacy.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marvelcomics.brito.databinding.ActivityMainBinding
import com.marvelcomics.brito.presentation.home.HomeViewModel
import com.marvelcomics.brito.view.legacy.extensions.viewBinding
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private val bindings by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindings.root)

        initEffects()
        initStates()
    }

    private fun initStates() {
        TODO("Not yet implemented")
    }

    private fun initEffects() {
        TODO("Not yet implemented")
    }
}
