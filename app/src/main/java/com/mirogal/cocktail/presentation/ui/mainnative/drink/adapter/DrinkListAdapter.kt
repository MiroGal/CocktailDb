package com.mirogal.cocktail.presentation.ui.mainnative.drink.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.datanative.db.model.CocktailDbModel
import java.util.*

class DrinkListAdapter(
        private val context: Context,
        private val onItemClickListener: OnItemClickListener,
        private val onItemLongClickListener: OnItemLongClickListener
) : RecyclerView.Adapter<DrinkItemHolder>() {

    private var cocktailList: List<CocktailDbModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_cocktail_drink_history, parent, false)
        return DrinkItemHolder(context, view)
    }

    override fun onBindViewHolder(holderDrink: DrinkItemHolder, position: Int) {
        holderDrink.bind(cocktailList[position])
        holderDrink.setListener(onItemClickListener, onItemLongClickListener)
    }

    override fun getItemCount(): Int {
        return cocktailList.size
    }

    fun refreshData(newData: List<CocktailDbModel>) {
        cocktailList = newData
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(cocktailId: Int, cocktailName: String?)
        fun onFavoriteClick(cocktailId: Int, isFavorite: Boolean)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, cocktailModel: CocktailDbModel)
    }

}