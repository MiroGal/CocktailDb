package com.mirogal.cocktail.presentation.databinding.adapter

import android.widget.TextView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter

object TextViewBindingAdapter {

    @BindingAdapter("android:bind_tv_setTextOrGone")
    @JvmStatic
    fun TextView.setTextOrGone(typedText: String?) {
        text = typedText
        isGone = typedText.isNullOrEmpty()
    }

}