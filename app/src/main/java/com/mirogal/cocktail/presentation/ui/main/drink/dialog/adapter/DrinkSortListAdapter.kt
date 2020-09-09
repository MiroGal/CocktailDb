package com.mirogal.cocktail.presentation.ui.main.drink.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.constant.filter.DrinkSort

class DrinkSortListAdapter(
        currentSort: DrinkSort,
        private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DrinkSortItemHolder>() {

    private var innerCurrentSort = currentSort

    fun refreshData(currentSort: DrinkSort) {
        innerCurrentSort = currentSort
        notifyDataSetChanged()
    }

    val list = DrinkSort.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkSortItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_type_filter_sort_dialog, parent, false)
        return DrinkSortItemHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkSortItemHolder, position: Int) {
        holder.bind(list[position], innerCurrentSort)
        holder.setListener(onItemClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(currentSort: DrinkSort)
    }

}