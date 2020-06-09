package com.mirogal.cocktail.presentation.ui.detail.ingredientlist

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.detail.ingredientlist.entity.IngredientEntity

class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvPosition: TextView = itemView.findViewById(R.id.tv_position)
    private val tvIngredient: TextView = itemView.findViewById(R.id.tv_ingredient)
    private val tvMeasure: TextView = itemView.findViewById(R.id.tv_measure)

    @SuppressLint("SetTextI18n")
    fun bind(ingredientEntity: IngredientEntity) {
        tvPosition.text = ingredientEntity.id.toString() + "."
        tvIngredient.text = ingredientEntity.name
        tvMeasure.text = ingredientEntity.measure
    }

}