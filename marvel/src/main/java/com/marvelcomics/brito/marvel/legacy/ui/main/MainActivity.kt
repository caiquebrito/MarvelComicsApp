package com.marvelcomics.brito.marvel.legacy.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.marvelcomics.brito.marvel.R
import com.marvelcomics.brito.marvel.databinding.ActivityMainBinding
import com.marvelcomics.brito.marvel.legacy.extensions.viewBinding

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
