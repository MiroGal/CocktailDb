package com.mirogal.cocktail.ui.main.history

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.ui.main.history.ListAdapter

class ItemHolder(private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var cocktailEntity: CocktailDbEntity? = null
    private val tvName: TextView = itemView.findViewById(R.id.tv_name)
    private val ivImage: ImageView = itemView.findViewById(R.id.iv_image)
    private val ivFavorite: ImageView = itemView.findViewById(R.id.iv_favorite)

    fun bind(cocktailEntity: CocktailDbEntity) {
        this.cocktailEntity = cocktailEntity
        tvName.text = cocktailEntity.name
        if (cocktailEntity.isFavorite) {
            ivFavorite.setImageResource(R.drawable.ic_star)
        } else {
            ivFavorite.setImageResource(R.drawable.ic_star_border)
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
        itemView.setOnClickListener { clickListener.onItemClick(cocktailEntity) }
        ivFavorite.setOnClickListener { clickListener.onFavoriteClick(cocktailEntity) }
        itemView.setOnLongClickListener {
            longClickListener.onItemLongClick(cocktailEntity!!.id)
            false
        }
    }

}