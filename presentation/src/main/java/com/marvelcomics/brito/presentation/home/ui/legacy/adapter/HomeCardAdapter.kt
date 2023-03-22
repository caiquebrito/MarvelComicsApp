package com.marvelcomics.brito.presentation.home.ui.legacy.adapter

import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.presentation.databinding.ViewHomeCardItemBinding
import com.marvelcomics.brito.presentation.ui.compose.shape.CutCustomCornerShape
import com.marvelcomics.brito.presentation.ui.models.MarvelThumbnailAspectRatio

class HomeCardAdapter(
    private val list: List<CharacterEntity>,
    private var adapterClickCallback: (characterDomain: CharacterEntity) -> Unit
) : RecyclerView.Adapter<HomeCardAdapter.HomeCardCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCardCardViewHolder {
        val itemBinding = ViewHomeCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeCardCardViewHolder((itemBinding))
    }

    override fun onBindViewHolder(holder: HomeCardCardViewHolder, position: Int) {
        holder.bind(list[position], adapterClickCallback)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class HomeCardCardViewHolder(val binding: ViewHomeCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            character: CharacterEntity,
            adapterClickCallback: (characterDomain: CharacterEntity) -> Unit
        ) = with(binding) {
            linearlayoutMarvelCardMovieShape.background = ShapeDrawable(CutCustomCornerShape())
            character.thumbnailEntity?.let {
                Glide.with(binding.root).load(
                    it.getFullUrlThumbnailWithAspect(
                        MarvelThumbnailAspectRatio.Standard.MEDIUM
                    )
                ).centerCrop().into(imageviewMarvelCardHeroProfile)
            }
            textviewMarvelCardHeroName.text = "Building..."
            textviewMarvelCardHeroTitle.text = character.name
            root.setOnClickListener {
                adapterClickCallback.invoke(character)
            }
        }
    }
}
