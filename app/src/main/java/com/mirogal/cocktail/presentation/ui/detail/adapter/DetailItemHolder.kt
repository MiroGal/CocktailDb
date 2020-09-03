package com.mirogal.cocktail.presentation.ui.detail.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R

class DetailItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvPosition: TextView = itemView.findViewById(R.id.tv_position)
    private val tvIngredient: TextView = itemView.findViewById(R.id.tv_ingredient)
    private val tvMeasure: TextView = itemView.findViewById(R.id.tv_measure)

    @SuppressLint("SetTextI18n")
    fun bind(position: Int, ingredientsWithMeasures: Pair<String, String?>) {
        tvPosition.text = "$position."
        tvIngredient.text = ingredientsWithMeasures.first
        tvMeasure.text = ingredientsWithMeasures.second
    }

}