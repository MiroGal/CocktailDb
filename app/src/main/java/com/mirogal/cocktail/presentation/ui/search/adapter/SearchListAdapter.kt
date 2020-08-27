package com.mirogal.cocktail.presentation.ui.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.cocktail.CocktailModel
import java.util.*

class SearchListAdapter(
        private val context: Context,
        private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SearchItemHolder>() {

    private var cocktailList: List<CocktailModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_cocktail_search, parent, false)
        return SearchItemHolder(context, view)
    }

    override fun onBindViewHolder(holderSearch: SearchItemHolder, position: Int) {
        holderSearch.bind(cocktailList[position])
        holderSearch.setListener(onItemClickListener)
    }

    override fun getItemCount(): Int {
        return cocktailList.size
    }

    fun refreshData(newData: List<CocktailModel>) {
        cocktailList = newData
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(cocktailModel: CocktailModel)
    }

}