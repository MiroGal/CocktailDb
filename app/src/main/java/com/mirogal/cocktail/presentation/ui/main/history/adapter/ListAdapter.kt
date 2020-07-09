package com.mirogal.cocktail.presentation.ui.main.history.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.db.entity.CocktailDbEntity
import java.util.*

class ListAdapter(private val context: Context,
                  private val onItemClickListener: OnItemClickListener,
                  private val onItemLongClickListener: OnItemLongClickListener)
    : RecyclerView.Adapter<ItemHolder>() {

    private var cocktailList: List<CocktailDbEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_cocktail_drink_history, parent, false)
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
        fun onItemClick(cocktailId: Int, cocktailName: String?)
        fun onFavoriteClick(cocktailId: Int, isFavorite: Boolean)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, cocktailEntity: CocktailDbEntity)
    }

}