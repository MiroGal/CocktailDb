package com.mirogal.cocktail.presentation.ui.mainnative.drink.dialog.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.modelnative.filter.DrinkFilter
import com.mirogal.cocktail.presentation.modelnative.filter.DrinkFilterType

class DrinkFilterItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvFilterName: TextView = itemView.findViewById(R.id.tv_filter_name)

    private lateinit var filter: DrinkFilter
    private lateinit var drinkFilterType: DrinkFilterType
    private val filterList = hashMapOf<DrinkFilterType, DrinkFilter>()

    fun bind(filter: DrinkFilter, drinkFilterType: DrinkFilterType, currentFilterList: HashMap<DrinkFilterType, DrinkFilter>) {
        this.filter = filter
        this.drinkFilterType = drinkFilterType
        this.filterList.putAll(currentFilterList)

        tvFilterName.text = filter.key.replace("\\", "")
        itemView.isPressed = filter == currentFilterList[drinkFilterType]
    }

    fun setListener(onClickListener: DrinkFilterListAdapter.OnItemClickListener) {
        itemView.setOnClickListener {
            filterList[drinkFilterType] = filter
            onClickListener.onItemClick(filterList)
        }
    }

}