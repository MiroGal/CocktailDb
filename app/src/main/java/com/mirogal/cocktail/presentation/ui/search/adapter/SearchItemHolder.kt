package com.mirogal.cocktail.presentation.ui.search.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.cocktail.CocktailModel

class SearchItemHolder(
        private val context: Context,
        itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val tvName: TextView = itemView.findViewById(R.id.tv_name)
    private val ivImage: ImageView = itemView.findViewById(R.id.iv_image)

    private lateinit var cocktailModel: CocktailModel

    fun bind(cocktailModel: CocktailModel) {
        this.cocktailModel = cocktailModel
        tvName.text = cocktailModel.names?.default
        Glide.with(context)
                .load(cocktailModel.image)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder_drink)
                .error(R.drawable.ic_placeholder_error)
                .into(ivImage)
    }

    fun setListener(listener: SearchListAdapter.OnItemClickListener) {
        itemView.setOnClickListener {
            listener.onItemClick(cocktailModel)
        }
    }

}