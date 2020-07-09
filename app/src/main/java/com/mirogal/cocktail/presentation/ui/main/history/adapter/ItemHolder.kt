package com.mirogal.cocktail.presentation.ui.main.history.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.db.entity.CocktailDbEntity
import com.mirogal.cocktail.presentation.ui.main.history.adapter.ListAdapter

class ItemHolder(private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvName: TextView = itemView.findViewById(R.id.tv_name)
    private val ivImage: ImageView = itemView.findViewById(R.id.iv_image)
    private val ivFavorite: ImageView = itemView.findViewById(R.id.iv_favorite)

    private lateinit var cocktailEntity: CocktailDbEntity
    private var cocktailId = 0
    private var cocktailName: String? = ""
    private var isFavorite: Boolean = false

    fun bind(cocktailEntity: CocktailDbEntity) {
        this.cocktailEntity = cocktailEntity
        cocktailId = cocktailEntity.id
        cocktailName = cocktailEntity.name
        isFavorite = cocktailEntity.isFavorite

        tvName.text = cocktailEntity.name
        if (cocktailEntity.isFavorite) {
            ivFavorite.setImageResource(R.drawable.ic_item_favorite)
        } else {
            ivFavorite.setImageResource(R.drawable.ic_item_favorite_border)
        }
        Glide.with(context)
                .load(cocktailEntity.imagePath)
                .centerCrop()
                .placeholder(R.drawable.anim_placeholder_progress)
                .error(R.drawable.ic_placeholder_error)
                .into(ivImage)
        Log.d("TAG", "holder")
    }

    fun setListener(clickListener: ListAdapter.OnItemClickListener,
                    longClickListener: ListAdapter.OnItemLongClickListener) {
        itemView.setOnClickListener {
            clickListener.onItemClick(cocktailId, cocktailName)
        }
        ivFavorite.setOnClickListener {
            clickListener.onFavoriteClick(cocktailId, isFavorite)
        }
        itemView.setOnLongClickListener {
            longClickListener.onItemLongClick(itemView, cocktailEntity)
            false
        }
    }

}