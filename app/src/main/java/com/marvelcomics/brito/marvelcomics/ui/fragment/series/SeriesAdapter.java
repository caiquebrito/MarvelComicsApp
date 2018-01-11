package com.marvelcomics.brito.marvelcomics.ui.fragment.series;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.marvelcomics.brito.entity.SeriesEntity;
import com.marvelcomics.brito.infrastructure.utils.MarvelThumbnailAspectRatio;
import com.marvelcomics.brito.marvelcomics.R;
import com.marvelcomics.brito.marvelcomics.databinding.ItemSeriesBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    private List<SeriesEntity> series = new ArrayList<>();

    public SeriesAdapter(List<SeriesEntity> series) {
        this.series = series;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSeriesBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_series, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(series.get(position));
    }

    @BindingAdapter("android:src")
    public static void setComicImage(ImageView imageView, SeriesEntity series) {
        Context context = imageView.getContext();
        Picasso.with(imageView.getContext())
                .load(series.getFullUrlThumbnailWithAspect(MarvelThumbnailAspectRatio.Portrait.XLARGE))
                .placeholder(context.getDrawable(R.drawable.progress))
                .fit()
                .centerInside()
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return series.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemSeriesBinding binding;

        ViewHolder(ItemSeriesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(SeriesEntity seriesEntity) {
            binding.setSeries(seriesEntity);
            binding.executePendingBindings();
        }
    }
}
