package com.mirogal.cocktail.presentation.ui.savelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity

class ListAdapter(private val context: Context,
                  private val onItemClickListener: OnItemClickListener,
                  private val onItemLongClickListener: OnItemLongClickListener,
                  private val itemLayoutId: Int) : PagedListAdapter<CocktailDbEntity, ItemHolder>(DIFF_CALLBACK) {


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (position <= -1) {
            return
        }
        val item = getItem(position)
        holder.bind(item!!)
        holder.setListener(onItemClickListener, onItemLongClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(itemLayoutId, parent, false)
        return ItemHolder(context, view)
    }

    interface OnItemClickListener {
        fun onItemClick(cocktail: CocktailDbEntity?)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(cocktailId: Int)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CocktailDbEntity>() {

            override fun areItemsTheSame(oldItem: CocktailDbEntity, newItem: CocktailDbEntity)
                    = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CocktailDbEntity, newItem: CocktailDbEntity)
                    = oldItem.name == newItem.name && oldItem.imagePath == newItem.imagePath
        }
    }
}