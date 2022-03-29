package com.marvelcomics.brito.view.home.fragment.comics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.marvelcomics.brito.R
import com.marvelcomics.brito.databinding.FragmentComicsBinding
import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.infrastructure.utils.AlertDialogUtils
import com.marvelcomics.brito.presentation.ComicUiState
import com.marvelcomics.brito.presentation.comic.ComicViewModel
import com.marvelcomics.brito.view.extensions.viewBinding
import com.marvelcomics.brito.view.home.fragment.ItemOffSetDecorationHorizontal
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject

class ComicsFragment : Fragment(R.layout.fragment_comics) {

    private val binding by viewBinding(FragmentComicsBinding::bind)
    private val comicViewModel: ComicViewModel by inject()

    private var characterId: Int? = 0
    private var uiStateJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        characterId = arguments?.getInt(ARGUMENT_CHARACTER_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadComics()
    }

    override fun onStart() {
        super.onStart()
        initObservers()
    }

    override fun onStop() {
        super.onStop()
        uiStateJob?.cancel()
    }

    private fun initObservers() {
        uiStateJob = lifecycleScope.launchWhenStarted {
            comicViewModel.comicUiState.collect {
                when (it) {
                    is ComicUiState.Success -> {
                        showComics(it.data as List<ComicEntity>)
                    }
                    is ComicUiState.Error -> {
                        it.exception.message?.let { message ->
                            showError(message)
                        }
                    }
                    else -> {
                        // do nothing
                    }
                }
            }
        }
    }

    private fun loadComics() {
        comicViewModel.loadComics(characterId ?: let { 0 })
    }

    private fun showComics(comics: List<ComicEntity>) {
        binding.progressbarLoadingComics.visibility = View.GONE
        binding.recyclerviewFragmentComic.visibility = View.VISIBLE
        createAdapter(comics)
    }

    private fun showLoading() {
        binding.progressbarLoadingComics.visibility = View.VISIBLE
        binding.recyclerviewFragmentComic.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.progressbarLoadingComics.visibility = View.GONE
        binding.recyclerviewFragmentComic.visibility = View.VISIBLE
        AlertDialogUtils.showSimpleDialog("Erro", message, requireContext())
    }

    private fun createAdapter(listComics: List<ComicEntity>) {
        val comicsAdapter = ComicsAdapter(listComics)
        binding.recyclerviewFragmentComic.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewFragmentComic.adapter = comicsAdapter
        binding.recyclerviewFragmentComic.addItemDecoration(
            ItemOffSetDecorationHorizontal(8)
        )
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
