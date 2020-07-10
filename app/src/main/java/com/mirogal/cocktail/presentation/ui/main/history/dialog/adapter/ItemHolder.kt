package com.mirogal.cocktail.presentation.ui.main.history.dialog.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.filter.DrinkFilter
import com.mirogal.cocktail.presentation.model.filter.DrinkFilterType

class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvFilterName: TextView = itemView.findViewById(R.id.tv_filter_name)

    private lateinit var filter: DrinkFilter
    private lateinit var drinkFilterType: DrinkFilterType
    private lateinit var filterList: HashMap<DrinkFilterType, DrinkFilter>

    fun bind(filter: DrinkFilter, drinkFilterType: DrinkFilterType, currentFilterList: HashMap<DrinkFilterType, DrinkFilter>) {
        this.filter = filter
        this.drinkFilterType = drinkFilterType
        this.filterList = currentFilterList

        tvFilterName.text = filter.key.replace("\\", "")
        if (filter == currentFilterList[drinkFilterType]) {
            itemView.isPressed = true
        }


    }

    fun setListener(onClickListener: ListAdapter.OnItemClickListener) {
        itemView.setOnClickListener {
            filterList[drinkFilterType] = filter
            onClickListener.onItemClick(filterList)
        }
    }

}