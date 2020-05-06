package com.marvelcomics.brito.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.marvelcomics.brito.R
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.viewmodel.character.CharacterViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val characterViewModel: CharacterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterViewModel.character.observe(this, Observer {
            when (it.status) {
                ResourceModel.State.SUCCESS -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                }
                ResourceModel.State.ERROR -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                }
                ResourceModel.State.LOADING -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        })

        characterViewModel.getCharacter("Hulk")
    }
}
