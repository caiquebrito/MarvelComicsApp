package com.marvelcomics.brito.presentation.details.ui.legacy

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marvelcomics.brito.presentation.R
import com.marvelcomics.brito.presentation.databinding.FragmentDetailCharacterBinding
import com.marvelcomics.brito.presentation.details.DetailCharacterUiEffect
import com.marvelcomics.brito.presentation.details.DetailCharacterUiState
import com.marvelcomics.brito.presentation.details.DetailCharacterViewModel
import com.marvelcomics.brito.presentation.details.ui.legacy.adapter.SeriesAndComicsAdapter
import com.marvelcomics.brito.presentation.ui.extensions.ItemOffSetDecorationVertical
import com.marvelcomics.brito.presentation.ui.extensions.dpToPx
import com.marvelcomics.brito.presentation.ui.extensions.onEffectTriggered
import com.marvelcomics.brito.presentation.ui.extensions.onStateChange
import com.marvelcomics.brito.presentation.ui.extensions.viewBinding
import com.marvelcomics.brito.presentation.ui.models.MarvelThumbnailAspectRatio
import com.marvelcomics.brito.presentation.ui.models.fromBundleToEntity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCharacterFragment : Fragment(R.layout.fragment_detail_character) {

    private val binding by viewBinding(FragmentDetailCharacterBinding::bind)
    private val viewModel: DetailCharacterViewModel by viewModel()
    private val args by navArgs<DetailCharacterFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        onStateChange(viewModel, ::handleState)
        onEffectTriggered(viewModel, ::handleEffect)
    }

    private fun initViews() = with(binding) {
        val characterEntity = args.characterBundle.fromBundleToEntity()
        viewModel.getComicsAndSeriesById(characterEntity.id)
        Glide.with(root)
            .load(
                characterEntity.thumbnailEntity?.getFullUrlThumbnailWithAspect(
                    MarvelThumbnailAspectRatio.Standard.LARGE
                )
            )
            .centerCrop()
            .into(imageviewDetailCharacter)
        textviewDetailCharacterName.text = characterEntity.name
        textviewDetailCharacterDescription.text = characterEntity.description
        textviewDetailCharacterDescription.movementMethod = ScrollingMovementMethod()

        recyclerviewDetailCharacterComics.addItemDecoration(
            ItemOffSetDecorationVertical(8.dpToPx(resources))
        )
        recyclerviewDetailCharacterComics.layoutManager = LinearLayoutManager(
            this.root.context,
            RecyclerView.VERTICAL,
            false
        )
        recyclerviewDetailCharacterSeries.addItemDecoration(
            ItemOffSetDecorationVertical(8.dpToPx(resources))
        )
        recyclerviewDetailCharacterSeries.layoutManager = LinearLayoutManager(
            this.root.context,
            RecyclerView.VERTICAL,
            false
        )
    }

    private fun handleState(state: DetailCharacterUiState) = with(binding) {
        if (state.isIdle) {
            return
        }
        circularprogressindicatorDetailCharacterComics.isVisible = state.showComicsLoading
        recyclerviewDetailCharacterComics.isVisible = state.showComicsLoading.not()
        recyclerviewDetailCharacterComics.adapter = state.listComics?.let { list ->
            if (list.isEmpty()) {
                buildComicsEmptyState()
                null
            } else {
                SeriesAndComicsAdapter(list)
            }
        }
        circularprogressindicatorDetailCharacterSeries.isVisible = state.showSeriesLoading
        recyclerviewDetailCharacterSeries.isVisible = state.showSeriesLoading.not()
        recyclerviewDetailCharacterSeries.adapter = state.listSeries?.let { list ->
            if (list.isEmpty()) {
                buildSeriesEmptyState()
                null
            } else {
                SeriesAndComicsAdapter(list)
            }
        }
    }

    private fun buildComicsEmptyState() {
        Toast.makeText(requireContext(), "Empty Comics State", Toast.LENGTH_SHORT).show()
    }

    private fun buildSeriesEmptyState() {
        Toast.makeText(requireContext(), "Empty Series State", Toast.LENGTH_SHORT).show()
    }

    private fun handleEffect(effect: DetailCharacterUiEffect) {
        when (effect) {
            DetailCharacterUiEffect.ShowComicsError -> {
                Toast.makeText(requireContext(), "Show Comics Error", Toast.LENGTH_SHORT).show()
            }
            DetailCharacterUiEffect.ShowSeriesError -> {
                Toast.makeText(requireContext(), "Show Series Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
