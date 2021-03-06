package com.mirogal.cocktail.presentation.ui.main.drink.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.cocktail.CocktailModel

class DrinkItemHolder(
        private val context: Context,
        itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val tvName: TextView = itemView.findViewById(R.id.tv_name)
    private val ivImage: ImageView = itemView.findViewById(R.id.iv_image)
    private val ivFavorite: ImageView = itemView.findViewById(R.id.iv_favorite)

    private lateinit var cocktailModel: CocktailModel
    private var cocktailId = -1L
    private var cocktailName: String? = ""
    private var isFavorite: Boolean = false

    fun bind(cocktailModel: CocktailModel) {
        this.cocktailModel = cocktailModel
        cocktailId = cocktailModel.id
        cocktailName = cocktailModel.names.baseValue
        isFavorite = cocktailModel.isFavorite

        tvName.text = cocktailModel.names.baseValue
        if (cocktailModel.isFavorite) {
            ivFavorite.setImageResource(R.drawable.ic_item_favorite)
        } else {
            ivFavorite.setImageResource(R.drawable.ic_item_favorite_border)
        }
        Glide.with(context)
                .load(cocktailModel.image)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder_drink)
                .error(R.drawable.ic_placeholder_error)
                .into(ivImage)
    }

    fun setListener(clickListener: DrinkListAdapter.OnItemClickListener,
                    longClickListener: DrinkListAdapter.OnItemLongClickListener) {
        itemView.setOnClickListener {
            clickListener.onItemClick(cocktailId, cocktailName)
        }
        ivFavorite.setOnClickListener {
            clickListener.onFavoriteClick(cocktailId, isFavorite)
        }
        itemView.setOnLongClickListener {
            longClickListener.onItemLongClick(itemView, cocktailModel)
            false
        }
    }

}