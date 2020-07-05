package com.mirogal.cocktail.ui.main.history

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseFragment
import com.mirogal.cocktail.ui.detail.DrinkDetailActivity
import com.mirogal.cocktail.ui.main.MainViewModel
import com.mirogal.cocktail.ui.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_drink_history.*
import kotlinx.android.synthetic.main.layout_drink_history_empty.*


class DrinkHistoryFragment : BaseFragment<DrinkHistoryViewModel>(), ListAdapter.OnItemClickListener,
        ListAdapter.OnItemLongClickListener {

    override val contentLayoutResId = R.layout.fragment_drink_history
    override val viewModel: DrinkHistoryViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    private lateinit var listAdapter: ListAdapter

    companion object {
        fun newInstance() = DrinkHistoryFragment()
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
        rv_drink_history_list.layoutManager = GridLayoutManager(requireContext(), listColumn)

        val spaceInPixel = resources.getDimensionPixelSize(R.dimen.offset_16)
        val itemDecoration = SpaceItemDecoration(listColumn, spaceInPixel, true, 0)
        rv_drink_history_list.addItemDecoration(itemDecoration)

        listAdapter = ListAdapter(requireContext(), this, this)
        activityViewModel.historyCocktailListLiveData.observe(viewLifecycleOwner, Observer { list ->
            if (list?.isNotEmpty()!!) {
                showData()
            } else {
                showEmpty()
            }
            listAdapter.refreshData(list)
        })
        rv_drink_history_list.adapter = listAdapter
    }


    override fun onItemClick(cocktailId: Int, cocktailName: String?) {
        openDrinkDetailActivity(cocktailId, cocktailName)
    }

    override fun onFavoriteClick(cocktailId: Int, isFavorite: Boolean) {
        activityViewModel.switchCocktailStateFavorite(cocktailId, isFavorite)
    }

    override fun onItemLongClick(cocktailId: Int) {
        activityViewModel.deleteCocktail(cocktailId)
    }


    private fun openDrinkDetailActivity(cocktailId: Int, cocktailName: String?) {
        val intent = Intent(activity, DrinkDetailActivity::class.java)
        intent.putExtra("cocktailId", cocktailId)
        intent.putExtra("cocktailName", cocktailName)
        startActivity(intent)
    }

    private fun showData() {
        if (rv_drink_history_list.visibility == View.INVISIBLE) {
            rv_drink_history_list.visibility = View.VISIBLE
            layoutEmpty.visibility = View.INVISIBLE
        }
    }

    private fun showEmpty() {
        if (layoutEmpty.visibility == View.INVISIBLE) {
            rv_drink_history_list.visibility = View.INVISIBLE
            layoutEmpty.visibility = View.VISIBLE
        }
    }

}