package com.mirogal.cocktail.presentation.databinding.adapter

import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter

//object TextViewBindingAdapter {

    @BindingAdapter("bind:txt_textOrGone")
    fun TextView.setTextOrGone(typedText: String?) {
        text = typedText
        isGone = typedText.isNullOrEmpty()
        Toast.makeText(this.context, "AdapterText", Toast.LENGTH_LONG).show()
    }

//}