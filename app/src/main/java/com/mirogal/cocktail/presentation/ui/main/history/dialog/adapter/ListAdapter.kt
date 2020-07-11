package com.mirogal.cocktail.presentation.ui.main.history.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.filter.*

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
        DrinkFilterType.CATEGORY -> DrinkFilterCategory.values()
        DrinkFilterType.ALCOHOL -> DrinkFilterAlcohol.values()
        DrinkFilterType.INGREDIENT -> DrinkFilterIngredient.values()
        DrinkFilterType.GLASS -> DrinkFilterGlass.values()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_filter_dialog, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
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