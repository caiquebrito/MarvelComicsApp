package com.marvelcomics.brito.presentation.main.ui.compose

import android.os.Bundle
import com.marvelcomics.brito.presentation.R
import com.marvelcomics.brito.presentation.databinding.ActivityMainComposeBinding
import com.marvelcomics.brito.presentation.ui.extensions.navHostFragment
import com.marvelcomics.brito.presentation.ui.extensions.viewBinding
import org.koin.androidx.scope.ScopeActivity

class MainComposeActivity : ScopeActivity() {

    private val binding by viewBinding(ActivityMainComposeBinding::inflate)
    private val navController by lazy {
        navHostFragment(R.id.nav_host_fragment_compose).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
