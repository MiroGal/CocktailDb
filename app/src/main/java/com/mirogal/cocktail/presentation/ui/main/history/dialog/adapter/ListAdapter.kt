package com.mirogal.cocktail.presentation.ui.main.history.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.presentation.model.filter.CategoryDrinkFilter
import com.mirogal.cocktail.presentation.model.filter.DrinkFilter
import com.mirogal.cocktail.presentation.model.filter.DrinkFilterType

class ListAdapter(private val drinkFilterType: DrinkFilterType,
                  private val currentFilterList: HashMap<DrinkFilterType, DrinkFilter>,
                  private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ItemHolder>() {

    private var innerCurrentFilterList = currentFilterList

    fun refreshData(currentFilterList: HashMap<DrinkFilterType, DrinkFilter>) {
        innerCurrentFilterList = currentFilterList
        notifyDataSetChanged()
    }

    val list = when (drinkFilterType) {
        DrinkFilterType.ALCOHOL -> AlcoholDrinkFilter.values()
        DrinkFilterType.CATEGORY -> CategoryDrinkFilter.values()
        else -> null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_filter_dialog, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (list != null) {
            holder.bind(list[position] as DrinkFilter, drinkFilterType, innerCurrentFilterList)
            holder.setListener(onItemClickListener)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    interface OnItemClickListener {
        fun onItemClick(filterList: HashMap<DrinkFilterType, DrinkFilter>)
    }

}