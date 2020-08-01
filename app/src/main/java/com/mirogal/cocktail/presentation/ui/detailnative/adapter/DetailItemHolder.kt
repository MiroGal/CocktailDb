package com.mirogal.cocktail.presentation.ui.detailnative.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.modelnative.detail.IngredientModel

class DetailItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvPosition: TextView = itemView.findViewById(R.id.tv_position)
    private val tvIngredient: TextView = itemView.findViewById(R.id.tv_ingredient)
    private val tvMeasure: TextView = itemView.findViewById(R.id.tv_measure)

    @SuppressLint("SetTextI18n")
    fun bind(ingredientModel: IngredientModel) {
        tvPosition.text = ingredientModel.id.toString() + "."
        tvIngredient.text = ingredientModel.name
        tvMeasure.text = ingredientModel.measure
    }

}