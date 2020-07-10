package com.mirogal.cocktail.presentation.ui.main.history.dialog.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.filter.AlcoholDrinkFilter
import okhttp3.internal.immutableListOf

class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvFilterName: TextView = itemView.findViewById(R.id.tv_filter_name)

    private lateinit var filter: AlcoholDrinkFilter

    @SuppressLint("SetTextI18n")
    fun bind(filter: AlcoholDrinkFilter, currentFilter: AlcoholDrinkFilter) {
        this.filter = filter
        tvFilterName.text = filter.key
        if (filter == currentFilter) {
            itemView.isPressed = true
        }
    }

    fun setListener(onClickListener: ListAdapter.OnItemClickListener) {
        itemView.setOnClickListener {
            onClickListener.onItemClick(filter)
        }
    }

}