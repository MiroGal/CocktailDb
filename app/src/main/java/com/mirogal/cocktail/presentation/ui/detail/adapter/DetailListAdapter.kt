package com.mirogal.cocktail.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.modelnative.detail.IngredientModel

class DetailListAdapter(private val ingredientList: List<IngredientModel>) : RecyclerView.Adapter<DetailItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_ingredient_detail, parent, false)
        return DetailItemHolder(view)
    }

    override fun onBindViewHolder(holder: DetailItemHolder, position: Int) {
        holder.bind(ingredientList[position])
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

}