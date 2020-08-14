package com.mirogal.cocktail.presentation.databinding.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mirogal.cocktail.R

object ImageViewBindingAdapter {

    @BindingAdapter("bind:iv_loadUrl")
    fun ImageView.setTextOrGone(url: String?) {
        Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.anim_placeholder_progress)
                .error(R.drawable.ic_placeholder_error)
                .into(this)
    }

}