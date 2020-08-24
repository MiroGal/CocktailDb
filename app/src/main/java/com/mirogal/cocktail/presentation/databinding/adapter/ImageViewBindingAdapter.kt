package com.mirogal.cocktail.presentation.databinding.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mirogal.cocktail.R

object ImageViewBindingAdapter {

    @BindingAdapter("android:bind_iv_loadUrl")
    fun ImageView.loadUrl(url: String?) {
        Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder_drink)
                .error(R.drawable.ic_placeholder_error)
                .into(this)
    }

}