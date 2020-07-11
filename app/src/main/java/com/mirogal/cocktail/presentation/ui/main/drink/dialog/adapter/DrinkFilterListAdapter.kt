package com.mirogal.cocktail.presentation.ui.main.drink.dialog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.filter.*

class DrinkFilterListAdapter(private val drinkFilterType: DrinkFilterType,
                             private val currentFilterList: HashMap<DrinkFilterType, DrinkFilter>,
                             private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DrinkFilterListAdapter.ItemHolder>() {

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
        val view = inflater.inflate(R.layout.item_filter_filter_dialog, parent, false)
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


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvFilterName: TextView = itemView.findViewById(R.id.tv_filter_name)

        private lateinit var filter: DrinkFilter
        private lateinit var drinkFilterType: DrinkFilterType
        private lateinit var filterList: HashMap<DrinkFilterType, DrinkFilter>

        fun bind(filter: DrinkFilter, drinkFilterType: DrinkFilterType, currentFilterList: HashMap<DrinkFilterType, DrinkFilter>) {
            this.filter = filter
            this.drinkFilterType = drinkFilterType
            this.filterList = currentFilterList

            tvFilterName.text = filter.key.replace("\\", "")
            itemView.isPressed = filter == currentFilterList[drinkFilterType]
        }

        fun setListener(onClickListener: OnItemClickListener) {
            itemView.setOnClickListener {
                filterList[drinkFilterType] = filter
                onClickListener.onItemClick(filterList)
            }
        }
    }

}