package com.mirogal.cocktail.ui.main.history

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseFragment
import com.mirogal.cocktail.ui.detail.DrinkDetailActivity
import com.mirogal.cocktail.ui.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_drink_favorite.*
import kotlinx.android.synthetic.main.layout_drink_history_empty.*


class DrinkFavoriteFragment : BaseFragment<HistoryViewModel>(), ListAdapter.OnItemClickListener,
        ListAdapter.OnItemLongClickListener {

    override val contentLayoutResId = R.layout.fragment_drink_favorite
    override val viewModel: HistoryViewModel by activityViewModels()

    private lateinit var listAdapter: ListAdapter

    companion object {
        fun newInstance() = DrinkFavoriteFragment()
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        setList()
    }

    private fun setList() {
        val listColumn = when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 1
        }
        rv_favorite_drink_list.layoutManager = GridLayoutManager(requireContext(), listColumn)

        val spaceInPixel = resources.getDimensionPixelSize(R.dimen.offset_16)
        val itemDecoration = SpaceItemDecoration(listColumn, spaceInPixel, true, 0)
        rv_favorite_drink_list.addItemDecoration(itemDecoration)

        listAdapter = ListAdapter(requireContext(), this, this)
        viewModel.favoriteCocktailListLiveData.observe(viewLifecycleOwner, Observer { list ->
            if (list?.isNotEmpty()!!) {
                showData()
            } else {
                showEmpty()
            }
            listAdapter.refreshData(list)
        })
        rv_favorite_drink_list.adapter = listAdapter
    }


    override fun onItemClick(cocktailId: Int, cocktailName: String?) {
        openDrinkDetailActivity(cocktailId, cocktailName)
    }

    override fun onFavoriteClick(cocktailId: Int, isFavorite: Boolean) {
        viewModel.switchCocktailStateFavorite(cocktailId, isFavorite)
    }

    override fun onItemLongClick(cocktailId: Int) {
        viewModel.deleteCocktail(cocktailId)
    }


    private fun openDrinkDetailActivity(cocktailId: Int, cocktailName: String?) {
        val intent = Intent(activity, DrinkDetailActivity::class.java)
        intent.putExtra("cocktailId", cocktailId)
        intent.putExtra("cocktailName", cocktailName)
        startActivity(intent)
    }

    private fun showData() {
        if (rv_favorite_drink_list.visibility == View.INVISIBLE) {
            rv_favorite_drink_list.visibility = View.VISIBLE
            layoutEmpty.visibility = View.INVISIBLE
        }
    }

    private fun showEmpty() {
        if (layoutEmpty.visibility == View.INVISIBLE) {
            rv_favorite_drink_list.visibility = View.INVISIBLE
            layoutEmpty.visibility = View.VISIBLE
        }
    }

}