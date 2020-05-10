package com.marvelcomics.brito.view.fragment.comics

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ComicsAdapter : RecyclerView.Adapter<ComicsAdapter.ViewHolder?>() {

    //    private List<ComicEntity> comics = new ArrayList<>();
//
//    public ComicsAdapter(List<ComicEntity> comics) {
//        this.comics = comics;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        ItemComicBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_comic, parent, false);
//        return new ViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.bind(comics.get(position));
//    }
//
//    @BindingAdapter("android:src")
//    public static void setComicImage(ImageView imageView, ComicEntity comic) {
//        Context context = imageView.getContext();
//        Picasso.with(imageView.getContext())
//                .load(comic.getFullUrlThumbnailWithAspect(MarvelThumbnailAspectRatio.Portrait.XLARGE))
//                .placeholder(context.getDrawable(R.drawable.progress))
//                .fit()
//                .centerInside()
//                .into(imageView);
//    }
//
//    @Override
//    public int getItemCount() {
//        return comics.size();
//    }

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}