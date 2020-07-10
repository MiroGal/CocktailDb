package com.mirogal.cocktail.presentation.ui.main.history.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.filter.AlcoholDrinkFilter

class ListAdapter(private val filterList: Array<AlcoholDrinkFilter>,
                  private val onItemClickListener: OnItemClickListener,
                  private val currentFilter: AlcoholDrinkFilter?
) : RecyclerView.Adapter<ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_filter_dialog, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(filterList[position], currentFilter ?: AlcoholDrinkFilter.DISABLE)
        holder.setListener(onItemClickListener)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    interface OnItemClickListener {
        fun onItemClick(filter: AlcoholDrinkFilter)
    }

}