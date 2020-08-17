package com.marvelcomics.brito.view.fragment.series

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marvelcomics.brito.R
import com.marvelcomics.brito.circularProgressBar
import com.marvelcomics.brito.data.entity.SeriesEntity
import com.marvelcomics.brito.infrastructure.utils.MarvelThumbnailAspectRatio

class SeriesAdapter(private val series: List<SeriesEntity>) :
    RecyclerView.Adapter<SeriesAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_series, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(series[position])
    }

    override fun getItemCount(): Int {
        return series.size
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private var title: TextView? = null
        private var imageView: ImageView? = null

        init {
            title = view.findViewById(R.id.textview_title_series)
            imageView = view.findViewById(R.id.imageview_series)
        }

        fun bind(seriesEntity: SeriesEntity) {
            title?.text = seriesEntity.title
            imageView?.let {
                Glide.with(it.context)
                    .load(seriesEntity.getFullUrlThumbnailWithAspect(MarvelThumbnailAspectRatio.Portrait.SMALL))
                    .placeholder(view.context.circularProgressBar())
                    .fitCenter()
                    .into(it)
            }
        }
    }
}