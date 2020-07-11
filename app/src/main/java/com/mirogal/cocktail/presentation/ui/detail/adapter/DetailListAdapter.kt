package com.mirogal.cocktail.presentation.ui.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.detail.IngredientModel

class DetailListAdapter(private val ingredientList: List<IngredientModel>) : RecyclerView.Adapter<DetailListAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_ingredient_detail, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(ingredientList[position])
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

}