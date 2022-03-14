package com.marvelcomics.brito.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marvelcomics.brito.databinding.ActivitySplashBinding
import com.marvelcomics.brito.view.extensions.viewBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class SplashActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        GlobalScope.launch {
            Thread.sleep(3000L)
            startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            finish()
        }
    }
}
