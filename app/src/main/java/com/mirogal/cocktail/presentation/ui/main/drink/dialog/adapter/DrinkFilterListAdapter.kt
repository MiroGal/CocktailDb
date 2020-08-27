package com.mirogal.cocktail.presentation.ui.main.drink.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.constant.filter.*

class DrinkFilterListAdapter(
        private val drinkFilterType: DrinkFilterType,
        private val currentFilterList: HashMap<DrinkFilterType, DrinkFilter>,
        private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DrinkFilterItemHolder>() {

    private var innerCurrentFilterList = currentFilterList

    fun refreshData(currentFilterList: HashMap<DrinkFilterType, DrinkFilter>) {
        innerCurrentFilterList = currentFilterList
        notifyDataSetChanged()
    }

    val list = when (drinkFilterType) {
        DrinkFilterType.CATEGORY -> DrinkFilterCategory.values()
        DrinkFilterType.ALCOHOL -> DrinkFilterAlcohol.values()
        DrinkFilterType.INGREDIENT -> DrinkFilterIngredient.values()
        DrinkFilterType.GLASS -> DrinkFilterGlass.values()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkFilterItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_type_filter_sort_dialog, parent, false)
        return DrinkFilterItemHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkFilterItemHolder, position: Int) {
        holder.bind(list[position] as DrinkFilter, drinkFilterType, innerCurrentFilterList)
        holder.setListener(onItemClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(filterList: HashMap<DrinkFilterType, DrinkFilter>)
    }

}