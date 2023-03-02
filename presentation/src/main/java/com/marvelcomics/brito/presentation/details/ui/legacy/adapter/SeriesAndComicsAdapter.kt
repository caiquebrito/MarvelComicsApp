package com.marvelcomics.brito.presentation.details.ui.legacy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marvelcomics.brito.entity.ComicEntity
import com.marvelcomics.brito.entity.SeriesEntity
import com.marvelcomics.brito.presentation.databinding.ViewSeriesAndComicItemBinding
import com.marvelcomics.brito.presentation.ui.models.MarvelThumbnailAspectRatio

class SeriesAndComicsAdapter(
    private val list: List<Any>
) : RecyclerView.Adapter<SeriesAndComicsAdapter.SeriesAndComicsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesAndComicsViewHolder {
        val itemBinding = ViewSeriesAndComicItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SeriesAndComicsViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SeriesAndComicsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class SeriesAndComicsViewHolder(val binding: ViewSeriesAndComicItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataObject: Any) = with(binding) {
            val uiOject = when (dataObject) {
                is SeriesEntity -> {
                    dataObject.entityToUiObject()
                }
                is ComicEntity -> {
                    dataObject.entityToUiObject()
                }
                else -> {
                    null
                }
            }

            uiOject?.let {
                Glide.with(root)
                    .load(it.thumbnailUri)
                    .centerCrop()
                    .into(imageviewSeriesAndMoviesItem)
                textviewSeriesAndMoviesItemContent.text = it.description
            }
        }
    }
}

data class SeriesAndComicsUiObject(
    val description: String,
    val thumbnailUri: String
)

fun SeriesEntity.entityToUiObject() = SeriesAndComicsUiObject(
    description = description ?: "Empty Description",
    thumbnailUri = thumbnailEntity?.getFullUrlThumbnailWithAspect(MarvelThumbnailAspectRatio.Portrait.MEDIUM)
        ?: ""
)

fun ComicEntity.entityToUiObject() = SeriesAndComicsUiObject(
    description = description ?: "Empty Description",
    thumbnailUri = thumbnailEntity?.getFullUrlThumbnailWithAspect(MarvelThumbnailAspectRatio.Portrait.MEDIUM)
        ?: ""
)
