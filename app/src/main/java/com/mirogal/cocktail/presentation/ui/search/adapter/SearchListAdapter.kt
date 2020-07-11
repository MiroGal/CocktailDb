package com.mirogal.cocktail.presentation.ui.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.db.model.CocktailDbModel

internal class SearchListAdapter(private val context: Context,
                                 private val onItemClickListener: OnItemClickListener)
    : PagedListAdapter<CocktailDbModel, SearchItemHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CocktailDbModel>() {

            override fun areItemsTheSame(oldItem: CocktailDbModel, newItem: CocktailDbModel)
                    = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CocktailDbModel, newItem: CocktailDbModel)
                    = oldItem.name == newItem.name && oldItem.imagePath == newItem.imagePath
        }
    }

    override fun onBindViewHolder(holderSearch: SearchItemHolder, position: Int) {
        if (position <= -1) {
            return
        }
        val item = getItem(position)
        holderSearch.bind(item!!)
        holderSearch.setListener(onItemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_cocktail_search, parent, false)
        return SearchItemHolder(context, view)
    }

    internal interface OnItemClickListener {
        fun onItemClick(cocktail: CocktailDbModel)
    }

}