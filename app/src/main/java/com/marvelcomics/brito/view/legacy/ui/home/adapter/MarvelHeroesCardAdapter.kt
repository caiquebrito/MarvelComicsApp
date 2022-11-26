package com.marvelcomics.brito.view.legacy.ui.home.adapter

import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marvelcomics.brito.databinding.ViewMarvelCardBinding
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.view.legacy.models.MarvelThumbnailAspectRatio
import com.marvelcomics.brito.view.shape.CutCustomCornerShape

class MarvelHeroesCardAdapter(
    private val list: List<CharacterDomain>,
    private var adapterClickCallback: ((characterDomain: CharacterDomain) -> Unit)
) :
    RecyclerView.Adapter<MarvelHeroesCardAdapter.MarvelHeroesCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelHeroesCardViewHolder {
        val itemBinding = ViewMarvelCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MarvelHeroesCardViewHolder((itemBinding))
    }

    override fun onBindViewHolder(holder: MarvelHeroesCardViewHolder, position: Int) {
        holder.bind(list[position], adapterClickCallback)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MarvelHeroesCardViewHolder(val binding: ViewMarvelCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            character: CharacterDomain,
            adapterClickCallback: ((characterDomain: CharacterDomain) -> Unit)
        ) = with(binding) {
            linearlayoutMarvelCardMovieShape.background = ShapeDrawable(CutCustomCornerShape())
            Glide.with(binding.root).load(
                character.thumbnailDomain?.getFullUrlThumbnailWithAspect(
                    MarvelThumbnailAspectRatio.Portrait.SMALL
                )
            ).fitCenter().into(binding.imageviewMarvelCardHeroProfile)
            textviewMarvelCardHeroName.text = "Building..."
            textviewMarvelCardHeroTitle.text = character.name
            root.setOnClickListener {
                adapterClickCallback.invoke(character)
            }
        }
    }
}
