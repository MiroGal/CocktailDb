package com.mirogal.cocktail.ui.savelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import java.util.*

class ListAdapter(private val context: Context,
                  private val onItemClickListener: OnItemClickListener,
                  private val onItemLongClickListener: OnItemLongClickListener)
    : RecyclerView.Adapter<ItemHolder>() {

    private var cocktailList: List<CocktailDbEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_save_list_cocktail, parent, false)
        return ItemHolder(context, view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(cocktailList[position])
        holder.setListener(onItemClickListener, onItemLongClickListener)
    }

    override fun getItemCount(): Int {
        return cocktailList.size
    }

    fun refreshData(newData: List<CocktailDbEntity>) {
        cocktailList = newData
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(cocktail: CocktailDbEntity?)
        fun onFavoriteClick(cocktail: CocktailDbEntity?)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(cocktailId: Int)
    }

}