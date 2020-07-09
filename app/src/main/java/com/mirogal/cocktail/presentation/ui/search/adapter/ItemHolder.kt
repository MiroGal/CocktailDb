package com.mirogal.cocktail.presentation.ui.search.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.db.entity.CocktailDbEntity
import com.mirogal.cocktail.presentation.ui.search.adapter.ListAdapter

internal class ItemHolder(private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var cocktailEntity: CocktailDbEntity
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

    fun setListener(listener: ListAdapter.OnItemClickListener) {
        itemView.setOnClickListener {
            listener.onItemClick(cocktailEntity)
        }
    }

}