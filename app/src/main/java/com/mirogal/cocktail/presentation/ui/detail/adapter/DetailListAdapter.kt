package com.mirogal.cocktail.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R

class DetailListAdapter(private val ingredientsWithMeasures: List<Pair<String, String?>>) : RecyclerView.Adapter<DetailItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_ingredient_detail, parent, false)
        return DetailItemHolder(view)
    }

    override fun onBindViewHolder(holder: DetailItemHolder, position: Int) {
        holder.bind(position + 1, ingredientsWithMeasures[position])
    }

    override fun getItemCount(): Int {
        return ingredientsWithMeasures.size
    }

}