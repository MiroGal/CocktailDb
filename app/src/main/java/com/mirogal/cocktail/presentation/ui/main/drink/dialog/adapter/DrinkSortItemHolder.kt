package com.mirogal.cocktail.presentation.ui.main.drink.dialog.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.modelnative.filter.DrinkSort

class DrinkSortItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvSortName: TextView = itemView.findViewById(R.id.tv_filter_name)

    private lateinit var sort: DrinkSort

    fun bind(sort: DrinkSort, currentSort: DrinkSort) {
        this.sort = sort

        tvSortName.text = sort.key.replace("\\", "")
        itemView.isPressed = sort == currentSort
    }

    fun setListener(onClickListener: DrinkSortListAdapter.OnItemClickListener) {
        itemView.setOnClickListener {
            onClickListener.onItemClick(sort)
        }
    }

}