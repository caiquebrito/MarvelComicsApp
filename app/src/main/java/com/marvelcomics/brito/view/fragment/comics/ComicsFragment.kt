package com.marvelcomics.brito.view.fragment.comics

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
import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.infrastructure.utils.AlertDialogUtils
import com.marvelcomics.brito.view.fragment.ItemOffSetDecorationHorizontal
import com.marvelcomics.brito.viewmodel.comic.ComicViewModel
import kotlinx.android.synthetic.main.fragment_comics.view.*
import org.koin.android.ext.android.inject

class ComicsFragment : Fragment() {

    private val comicViewModel: ComicViewModel by inject()

    private var characterId: Int? = 0

    private var progressbarLoadingComics: ProgressBar? = null
    private var recyclerviewComics: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        characterId = arguments?.getInt(ARGUMENT_CHARACTER_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView = inflater.inflate(R.layout.fragment_comics, null)
        progressbarLoadingComics = inflatedView.progressbar_loading_comics
        recyclerviewComics = inflatedView.recyclerview_fragment_comic
        return inflatedView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        loadComics()
    }

    private fun initObservers() {
        comicViewModel.comics.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceModel.State.SUCCESS -> {
                    it.data?.let { listComics ->
                        showComics(listComics)
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

    private fun loadComics() {
        comicViewModel.characterId.value = characterId.toString()
    }

    private fun showComics(comics: List<ComicEntity>) {
        progressbarLoadingComics?.visibility = View.GONE
        recyclerviewComics?.visibility = View.VISIBLE
        createAdapter(comics)
    }

    private fun showLoading() {
        progressbarLoadingComics?.visibility = View.VISIBLE
        recyclerviewComics?.visibility = View.GONE
    }

    private fun showError(message: String) {
        progressbarLoadingComics?.visibility = View.GONE
        recyclerviewComics?.visibility = View.VISIBLE
        AlertDialogUtils.showSimpleDialog("Erro", message, requireContext())
    }

    private fun createAdapter(listComics: List<ComicEntity>) {
        val comicsAdapter = ComicsAdapter(listComics)
        recyclerviewComics?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerviewComics?.adapter = comicsAdapter
        recyclerviewComics?.addItemDecoration(ItemOffSetDecorationHorizontal(8))
    }

    companion object {
        const val ARGUMENT_CHARACTER_ID = "character_id_args"

        fun newInstance(characterId: Int): ComicsFragment {
            val fragment = ComicsFragment()
            val args = Bundle()
            args.putInt(ARGUMENT_CHARACTER_ID, characterId)

            fragment.arguments = args
            return fragment
        }
    }
}