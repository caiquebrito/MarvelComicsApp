package com.marvelcomics.brito.presentation.search.ui.compose

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.marvelcomics.brito.presentation.R
import com.marvelcomics.brito.presentation.databinding.FragmentSearchComposeBinding
import com.marvelcomics.brito.presentation.search.SearchViewModel
import com.marvelcomics.brito.presentation.ui.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchComposeFragment : Fragment(R.layout.fragment_search_compose) {

    private val binding by viewBinding(FragmentSearchComposeBinding::bind)
    private val viewModel by viewModel<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeContent.setContent {
        }
    }
}
