package com.marvelcomics.brito.marvel.legacy.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.marvelcomics.brito.marvel.R
import com.marvelcomics.brito.marvel.databinding.FragmentDetailCharacterBinding
import com.marvelcomics.brito.marvel.legacy.extensions.viewBinding
import com.marvelcomics.brito.presentation.details.DetailCharacterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCharacterFragment : Fragment(R.layout.fragment_detail_character) {

    private val binding by viewBinding(FragmentDetailCharacterBinding::bind)
    private val viewModel: DetailCharacterViewModel by viewModel()
    private val args by navArgs<DetailCharacterFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getComicsAndSeriesById(args.characterBundle.id)
    }
}
