package com.marvelcomics.brito.presentation.main.ui.legacy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.marvelcomics.brito.presentation.R
import com.marvelcomics.brito.presentation.databinding.ActivityMainBinding
import com.marvelcomics.brito.presentation.ui.extensions.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
