package com.mirogal.cocktail.presentation.ui.savelist

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity

class ItemHolder(private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var cocktailEntity: CocktailDbEntity? = null
    private val tvName: TextView = itemView.findViewById(R.id.tv_name)
    private val ivImage: ImageView = itemView.findViewById(R.id.iv_image)

    fun bind(cocktailEntity: CocktailDbEntity) {
        this.cocktailEntity = cocktailEntity
        tvName.text = cocktailEntity.name
        Glide.with(context)
                .load(cocktailEntity.imagePath)
                .centerCrop()
                .placeholder(R.drawable.anim_placeholder_progress)
                .error(R.drawable.ic_placeholder_error)
                .into(ivImage)
    }

    fun setListener(clickListener: ListAdapter.OnItemClickListener,
                    longClickListener: ListAdapter.OnItemLongClickListener) {
        itemView.setOnClickListener { clickListener.onItemClick(cocktailEntity) }
        itemView.setOnLongClickListener {
            longClickListener.onItemLongClick(cocktailEntity!!.id)
            false
        }
    }

}