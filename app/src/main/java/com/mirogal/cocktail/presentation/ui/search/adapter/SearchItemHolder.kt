package com.mirogal.cocktail.presentation.ui.search.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.db.model.CocktailDbModel

class SearchItemHolder(
        private val context: Context,
        itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private lateinit var cocktailModel: CocktailDbModel
    private val tvName: TextView = itemView.findViewById(R.id.tv_name)
    private val ivImage: ImageView = itemView.findViewById(R.id.iv_image)

    fun bind(cocktailModel: CocktailDbModel) {
        this.cocktailModel = cocktailModel
        tvName.text = cocktailModel.name
        Glide.with(context)
                .load(cocktailModel.imagePath)
                .centerCrop()
                .placeholder(R.drawable.anim_placeholder_progress)
                .error(R.drawable.ic_placeholder_error)
                .into(ivImage)
    }

    fun setListener(listener: SearchListAdapter.OnItemClickListener) {
        itemView.setOnClickListener {
            listener.onItemClick(cocktailModel)
        }
    }

}