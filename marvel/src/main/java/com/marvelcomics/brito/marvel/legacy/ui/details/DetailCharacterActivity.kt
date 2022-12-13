package com.marvelcomics.brito.marvel.legacy.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marvelcomics.brito.marvel.databinding.ActivityDetailCharacterBinding
import com.marvelcomics.brito.marvel.legacy.extensions.viewBinding
import com.marvelcomics.brito.presentation.details.DetailCharacterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCharacterActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityDetailCharacterBinding::inflate)
    private val viewModel: DetailCharacterViewModel by viewModel()
    private val args by lazy {
        DetailCharacterActivityArgs.deserializeFrom(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        args.characterEntity?.let {
            viewModel.getComicsAndSeriesById(it.id)
        }
    }
}
