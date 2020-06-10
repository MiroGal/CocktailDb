package com.mirogal.cocktail.ui.detail.ingredientlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.ui.detail.ingredientlist.entity.IngredientEntity

class ListAdapter(private val ingredientList: List<IngredientEntity>,
                  private val itemLayoutId: Int) : RecyclerView.Adapter<ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(itemLayoutId, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(ingredientList[position])
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

}