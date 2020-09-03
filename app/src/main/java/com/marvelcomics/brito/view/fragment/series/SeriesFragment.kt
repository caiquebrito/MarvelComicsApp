package com.marvelcomics.brito.view.fragment.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvelcomics.brito.R
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.domain.entity.SeriesEntity
import com.marvelcomics.brito.infrastructure.utils.AlertDialogUtils
import com.marvelcomics.brito.view.fragment.ItemOffSetDecorationHorizontal
import com.marvelcomics.brito.view.fragment.comics.ComicsFragment
import com.marvelcomics.brito.viewmodel.series.SeriesViewModel
import kotlinx.android.synthetic.main.fragment_series.view.*
import org.koin.android.ext.android.inject

class SeriesFragment : Fragment() {

    private val seriesViewModel: SeriesViewModel by inject()

    private var characterId: Int? = 0

    private var progressbarLoadingSeries: ProgressBar? = null
    private var recyclerviewSeries: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        characterId = arguments?.getInt(ARGUMENT_CHARACTER_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView = inflater.inflate(R.layout.fragment_series, null)
        progressbarLoadingSeries = inflatedView.progressbar_loading_series
        recyclerviewSeries = inflatedView.recyclerview_fragment_series
        return inflatedView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        loadSeries()
    }

    private fun initObservers() {
        seriesViewModel.series.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceModel.State.SUCCESS -> {
                    it.data?.let { listSeries ->
                        showSeries(listSeries)
                    }
                }
                ResourceModel.State.ERROR -> {
                    it.message?.let { message ->
                        showError(message)
                    }
                }
                ResourceModel.State.LOADING -> {
                    showLoading()
                }
                else -> {

                }
            }
        })
    }

    private fun loadSeries() {
        seriesViewModel.characterId.value = characterId.toString()
    }

    private fun showSeries(series: List<SeriesEntity>) {
        progressbarLoadingSeries?.visibility = View.GONE
        recyclerviewSeries?.visibility = View.VISIBLE
        createAdapter(series)
    }

    private fun showLoading() {
        progressbarLoadingSeries?.visibility = View.VISIBLE
        recyclerviewSeries?.visibility = View.GONE
    }

    private fun showError(message: String) {
        progressbarLoadingSeries?.visibility = View.GONE
        recyclerviewSeries?.visibility = View.VISIBLE
        AlertDialogUtils.showSimpleDialog("Erro", message, requireContext())
    }

    private fun createAdapter(listSeries: List<SeriesEntity>) {
        val seriesAdapter = SeriesAdapter(listSeries)
        recyclerviewSeries?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerviewSeries?.adapter = seriesAdapter
        recyclerviewSeries?.addItemDecoration(ItemOffSetDecorationHorizontal(8))
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