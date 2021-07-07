package com.marvelcomics.brito.view.fragment.series

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.marvelcomics.brito.R
import com.marvelcomics.brito.databinding.FragmentSeriesBinding
import com.marvelcomics.brito.domain.entity.SeriesEntity
import com.marvelcomics.brito.infrastructure.utils.AlertDialogUtils
import com.marvelcomics.brito.view.extensions.viewBinding
import com.marvelcomics.brito.view.fragment.ItemOffSetDecorationHorizontal
import com.marvelcomics.brito.viewmodel.GlobalUiState
import com.marvelcomics.brito.viewmodel.SeriesUiState
import com.marvelcomics.brito.viewmodel.series.SeriesViewModel
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject

class SeriesFragment : Fragment(R.layout.fragment_series) {

    private val binding by viewBinding(FragmentSeriesBinding::bind)
    private val seriesViewModel: SeriesViewModel by inject()

    private var characterId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        characterId = arguments?.getInt(ARGUMENT_CHARACTER_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        loadSeries()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            seriesViewModel.seriesUiState.collect {
                when (it) {
                    is SeriesUiState.Success -> {
                        showSeries(it.data as List<SeriesEntity>)
                    }
                    is SeriesUiState.Error -> {
                        it.exception.message?.let { message ->
                            showError(message)
                        }
                    }
                    is GlobalUiState.Loading -> {
                        showLoading()
                    }
                    is GlobalUiState.NetworkError -> {
                        // do nothing
                    }
                    else -> {
                        // do nothing
                    }
                }
            }
        }
    }

    private fun loadSeries() {
        seriesViewModel.loadSeries(characterId ?: let { 0 })
    }

    private fun showSeries(series: List<SeriesEntity>) {
        binding.progressbarLoadingSeries.visibility = View.GONE
        binding.recyclerviewFragmentSeries.visibility = View.VISIBLE
        createAdapter(series)
    }

    private fun showLoading() {
        binding.progressbarLoadingSeries.visibility = View.VISIBLE
        binding.recyclerviewFragmentSeries.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.progressbarLoadingSeries.visibility = View.GONE
        binding.recyclerviewFragmentSeries.visibility = View.VISIBLE
        AlertDialogUtils.showSimpleDialog("Erro", message, requireContext())
    }

    private fun createAdapter(listSeries: List<SeriesEntity>) {
        val seriesAdapter = SeriesAdapter(listSeries)
        binding.recyclerviewFragmentSeries.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewFragmentSeries.adapter = seriesAdapter
        binding.recyclerviewFragmentSeries.addItemDecoration(ItemOffSetDecorationHorizontal(8))
    }

    companion object {
        const val ARGUMENT_CHARACTER_ID = "character_id_args"

        fun newInstance(characterId: Int): SeriesFragment {
            val fragment = SeriesFragment()
            val args = Bundle()
            args.putInt(ARGUMENT_CHARACTER_ID, characterId)

            fragment.arguments = args
            return fragment
        }
    }
}
