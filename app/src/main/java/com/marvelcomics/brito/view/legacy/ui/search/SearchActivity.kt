package com.marvelcomics.brito.view.legacy.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marvelcomics.brito.R
import com.marvelcomics.brito.databinding.ActivitySearchBinding
import com.marvelcomics.brito.view.legacy.extensions.viewBinding

class SearchActivity : AppCompatActivity() {

    private val bindings by viewBinding(ActivitySearchBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}
