package com.marvelcomics.brito.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marvelcomics.brito.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.intentFor

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch(Dispatchers.IO) {
            Thread.sleep(3000L)
            launch(Dispatchers.Main) {
                startActivity(intentFor<HomeActivity>())
            }
        }
    }
}
