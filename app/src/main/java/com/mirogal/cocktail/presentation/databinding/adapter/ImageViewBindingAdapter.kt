package com.mirogal.cocktail.presentation.databinding.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

object ImageViewBindingAdapter {

    @BindingAdapter(value = ["android:bind_iv_imageUrl",
        "android:bind_iv_placeholderResId",
        "android:bind_iv_errorResId", "android:bind_iv_errorUrl",
        "android:bind_iv_isCrossFadeEnabled"], requireAll = false)
    fun ImageView.loadImage(imageUrl: String?,
                          placeholderResId: Int?,
                          errorResId: Int?, errorUrl: String?,
                          isCrossFadeEnabled: Boolean) {
        val requestOptions = RequestOptions().apply {
            centerCrop()
            if (placeholderResId != null) placeholder(placeholderResId)
            if (errorUrl != null && errorResId == null) error(Glide.with(context).load(errorUrl))
            if (errorUrl == null && errorResId != null) error(errorResId)
            if (errorUrl != null && errorResId != null) error(errorResId)
        }

        Glide.with(context)
                .load(imageUrl).apply {
                    apply(requestOptions)
                    if (isCrossFadeEnabled) transition(DrawableTransitionOptions.withCrossFade())
                }
                .into(this)
    }

}