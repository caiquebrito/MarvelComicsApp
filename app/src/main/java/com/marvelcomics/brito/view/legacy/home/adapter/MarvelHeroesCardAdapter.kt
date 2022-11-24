package com.marvelcomics.brito.view.legacy.home.adapter

import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvelcomics.brito.databinding.ViewMarvelCardBinding
import com.marvelcomics.brito.view.legacy.models.MarvelHeroesInfo
import com.marvelcomics.brito.view.shape.CutCustomCornerShape

class MarvelHeroesCardAdapter(private val list: List<MarvelHeroesInfo>) :
    RecyclerView.Adapter<MarvelHeroesCardAdapter.MarvelHeroesCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelHeroesCardViewHolder {
        val itemBinding = ViewMarvelCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MarvelHeroesCardViewHolder((itemBinding))
    }

    override fun onBindViewHolder(holder: MarvelHeroesCardViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MarvelHeroesCardViewHolder(val binding: ViewMarvelCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(marvelHeroesInfo: MarvelHeroesInfo) = with(binding) {
            linearlayoutMarvelCardMovieShape.background = ShapeDrawable(CutCustomCornerShape())
            textviewMarvelCardHeroName.text = marvelHeroesInfo.name
            textviewMarvelCardHeroTitle.text = marvelHeroesInfo.codeName
        }
    }
}
