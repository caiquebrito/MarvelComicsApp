package com.marvelcomics.brito.marvelcomics.view.fragment.series

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SeriesAdapter : RecyclerView.Adapter<SeriesAdapter.ViewHolder?>() {

    //    private List<SeriesEntity> series = new ArrayList<>();
//
//    public SeriesAdapter(List<SeriesEntity> series) {
//        this.series = series;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        ItemSeriesBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_series, parent, false);
//        return new ViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.bind(series.get(position));
//    }
//
//    @BindingAdapter("android:src")
//    public static void setComicImage(ImageView imageView, SeriesEntity series) {
//        Context context = imageView.getContext();
//        Picasso.with(imageView.getContext())
//                .load(series.getFullUrlThumbnailWithAspect(MarvelThumbnailAspectRatio.Portrait.XLARGE))
//                .placeholder(context.getDrawable(R.drawable.progress))
//                .fit()
//                .centerInside()
//                .into(imageView);
//    }
//
//    @Override
//    public int getItemCount() {
//        return series.size();
//    }
//

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}