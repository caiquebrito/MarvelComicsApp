package com.marvelcomics.brito.view.fragment.comics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marvelcomics.brito.R
import com.marvelcomics.brito.circularProgressBar
import com.marvelcomics.brito.data.entity.ComicEntity
import com.marvelcomics.brito.infrastructure.utils.MarvelThumbnailAspectRatio

class ComicsAdapter(private val comics: List<ComicEntity>) :
    RecyclerView.Adapter<ComicsAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_comic, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comics[position])
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private var title: TextView? = null
        private var imageView: ImageView? = null

        init {
            title = view.findViewById(R.id.textview_title_comic)
            imageView = view.findViewById(R.id.imageview_comic)
        }

        fun bind(comicEntity: ComicEntity) {
            title?.text = comicEntity.title
            imageView?.let {
                Glide.with(it.context)
                    .load(comicEntity.getFullUrlThumbnailWithAspect(MarvelThumbnailAspectRatio.Portrait.SMALL))
                    .placeholder(view.context.circularProgressBar())
                    .fitCenter()
                    .into(it)
            }
        }
    }
}