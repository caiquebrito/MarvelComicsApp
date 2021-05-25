package com.marvelcomics.brito.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marvelcomics.brito.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@InternalCoroutinesApi
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        runBlocking(Dispatchers.IO) {
            Thread.sleep(3000L)
            launch(Dispatchers.Main) {
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            }
        }
    }
}
