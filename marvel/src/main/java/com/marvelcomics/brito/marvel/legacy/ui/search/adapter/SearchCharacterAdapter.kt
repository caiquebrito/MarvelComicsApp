package com.marvelcomics.brito.marvel.legacy.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.marvel.databinding.ViewSearchCharacterItemBinding
import com.marvelcomics.brito.marvel.legacy.models.MarvelThumbnailAspectRatio

class SearchCharacterAdapter(
    private val list: List<CharacterEntity>,
    private val callback: (characterEntity: CharacterEntity) -> Unit
) : RecyclerView.Adapter<SearchCharacterAdapter.SearchCharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCharacterViewHolder {
        val itemBinding = ViewSearchCharacterItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchCharacterViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SearchCharacterViewHolder, position: Int) {
        holder.bind(list[position], callback)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class SearchCharacterViewHolder(val binding: ViewSearchCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            characterEntity: CharacterEntity,
            callback: (characterEntity: CharacterEntity) -> Unit
        ) = with(binding) {
            characterEntity.thumbnailEntity?.let {
                Glide.with(binding.root).load(
                    it.getFullUrlThumbnailWithAspect(
                        MarvelThumbnailAspectRatio.Standard.SMALL
                    )
                ).centerCrop().into(imageviewSearchCharacterItem)
            }
            textviewSearchCharacterItemName.text = characterEntity.name
            textviewSearchCharacterItemDescription.text = characterEntity.description
            floatingactionbuttonSearchCharacterItemAdd.setOnClickListener {
                callback.invoke(characterEntity)
            }
        }
    }
}
