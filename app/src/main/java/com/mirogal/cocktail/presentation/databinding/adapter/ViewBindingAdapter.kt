package com.mirogal.cocktail.presentation.databinding.adapter

import android.view.View
import androidx.databinding.BindingAdapter

object ViewBindingAdapter {

    @BindingAdapter("android:bind_isVisible")
    @JvmStatic
    fun View.setVisible(isVisible: Boolean) {
        if (isVisible) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }
    }

}