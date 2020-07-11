package com.mirogal.cocktail.presentation.ui.main.drink.dialog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.filter.DrinkSort

class DrinkSortListAdapter(private val currentSort: DrinkSort,
                           private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DrinkSortListAdapter.ItemHolder>() {

    private var innerCurrentSort = currentSort

    fun refreshData(currentSort: DrinkSort) {
        innerCurrentSort = currentSort
        notifyDataSetChanged()
    }

    val list = DrinkSort.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_tipe_filter_sort_dialog, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(list[position], innerCurrentSort)
        holder.setListener(onItemClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(currentSort: DrinkSort)
    }


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvSortName: TextView = itemView.findViewById(R.id.tv_filter_name)

        private lateinit var sort: DrinkSort

        fun bind(sort: DrinkSort, currentSort: DrinkSort) {
            this.sort = sort

            tvSortName.text = sort.key.replace("\\", "")
            itemView.isPressed = sort == currentSort
        }

        fun setListener(onClickListener: OnItemClickListener) {
            itemView.setOnClickListener {
                onClickListener.onItemClick(sort)
            }
        }
    }

}