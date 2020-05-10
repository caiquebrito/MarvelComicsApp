package com.marvelcomics.brito.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.marvelcomics.brito.R
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.hideKeyboard
import com.marvelcomics.brito.viewmodel.character.CharacterViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val characterViewModel: CharacterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
        initObservers()
    }

    private fun initUi() {
        button_search_marvel_character.onClick {
            edittext_marvel_character.text.toString().apply {
                if (this.isNotBlank()) {
                    characterViewModel.getCharacter(this)
                    hideKeyboard()
                }
            }
        }
    }

    private fun initObservers() {
        characterViewModel.character.observe(this, Observer {
            when (it.status) {
                ResourceModel.State.SUCCESS -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                    progressbar_loading_character.visibility = View.GONE
                }
                ResourceModel.State.ERROR -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    progressbar_loading_character.visibility = View.GONE
                }
                ResourceModel.State.LOADING -> {
                    progressbar_loading_character.visibility = View.VISIBLE
                }
                else -> {
                }
            }
        })
    }
}
