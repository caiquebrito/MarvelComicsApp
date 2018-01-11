package com.marvelcomics.brito.marvelcomics.ui.fragment.comics;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.marvelcomics.brito.entity.ComicEntity;
import com.marvelcomics.brito.infrastructure.utils.MarvelThumbnailAspectRatio;
import com.marvelcomics.brito.marvelcomics.R;
import com.marvelcomics.brito.marvelcomics.databinding.ItemComicBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolder> {

    private List<ComicEntity> comics = new ArrayList<>();

    public ComicsAdapter(List<ComicEntity> comics) {
        this.comics = comics;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemComicBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_comic, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(comics.get(position));
    }

    @BindingAdapter("android:src")
    public static void setComicImage(ImageView imageView, ComicEntity comic) {
        Context context = imageView.getContext();
        Picasso.with(imageView.getContext())
                .load(comic.getFullUrlThumbnailWithAspect(MarvelThumbnailAspectRatio.Portrait.XLARGE))
                .placeholder(context.getDrawable(R.drawable.progress))
                .fit()
                .centerInside()
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemComicBinding binding;

        ViewHolder(ItemComicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ComicEntity comicEntity) {
            binding.setComic(comicEntity);
            binding.executePendingBindings();
        }
    }
}
