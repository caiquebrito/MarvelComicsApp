package com.marvelcomics.brito.marvelcomics.ui.fragment;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemOffSetDecorationHorizontal extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public ItemOffSetDecorationHorizontal(int itemOffset) {
        mItemOffset = itemOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffset, 0, mItemOffset, 0);
    }
}