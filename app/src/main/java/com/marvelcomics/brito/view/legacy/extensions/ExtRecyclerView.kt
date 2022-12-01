package com.marvelcomics.brito.view.legacy.extensions

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.marvelcomics.brito.R

fun RecyclerView.animateFallDown() {
    layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
    adapter?.notifyDataSetChanged()
    scheduleLayoutAnimation()
}

fun RecyclerView.animateFallRight() {
    layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_right)
    adapter?.notifyDataSetChanged()
    scheduleLayoutAnimation()
}
