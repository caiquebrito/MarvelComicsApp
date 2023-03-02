package com.marvelcomics.brito.presentation.details.ui.compose

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.marvelcomics.brito.presentation.R
import com.marvelcomics.brito.presentation.databinding.FragmentDetailCharacterComposeBinding
import com.marvelcomics.brito.presentation.details.DetailCharacterViewModel
import com.marvelcomics.brito.presentation.ui.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCharacterComposeFragment : Fragment(R.layout.fragment_detail_character_compose) {

    private val binding by viewBinding(FragmentDetailCharacterComposeBinding::bind)
    private val viewModel by viewModel<DetailCharacterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeContent.setContent {
        }
    }
}
