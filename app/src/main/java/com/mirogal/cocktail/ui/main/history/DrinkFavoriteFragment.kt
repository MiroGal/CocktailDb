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
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.ui.base.BaseFragment
import com.mirogal.cocktail.ui.detail.DrinkDetailActivity
import com.mirogal.cocktail.ui.main.MainViewModel
import com.mirogal.cocktail.ui.main.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.ui.main.filter.CategoryDrinkFilter
import com.mirogal.cocktail.ui.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_drink_favorite.*
import kotlinx.android.synthetic.main.layout_drink_history_empty.*


class DrinkFavoriteFragment : BaseFragment(), ListAdapter.OnItemClickListener,
        ListAdapter.OnItemLongClickListener {

    override val contentLayoutResId = R.layout.fragment_drink_favorite
    override val viewModel: MainViewModel by activityViewModels()

    private lateinit var listAdapter: ListAdapter
    private lateinit var cocktailList: List<CocktailDbEntity>
    private var alcoholFilter: AlcoholDrinkFilter? = null
    private var categoryFilter: CategoryDrinkFilter? = null


    companion object {
        fun newInstance() = DrinkFavoriteFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        viewModel.cocktailListLiveData.observe(viewLifecycleOwner, Observer { list: List<CocktailDbEntity> ->
            if (list.isNotEmpty()) {
                showData()
            } else {
                showEmpty()
            }
            listAdapter.refreshData(list)
        })
        rv_favorite_drink_list.adapter = listAdapter
    }


    override fun onItemClick(cocktail: CocktailDbEntity?) {
        openDrinkDetailActivity(cocktail!!)
    }

    override fun onFavoriteClick(cocktail: CocktailDbEntity?) {
        viewModel.switchCocktailFavoriteStatus(cocktail)
    }

    override fun onItemLongClick(cocktailId: Int) {
        viewModel.deleteCocktailFromDb(cocktailId)
    }


    private fun openDrinkDetailActivity(cocktail: CocktailDbEntity) {
        val intent = Intent(activity, DrinkDetailActivity::class.java)
        intent.putExtra(DrinkDetailActivity::class.java.simpleName, cocktail)
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

    fun setFilter(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        this.alcoholFilter = alcoholFilter
        this.categoryFilter = categoryFilter

        val filteredList0 = filterFavorite(cocktailList)
        val filteredList1 = filterAlcohol(filteredList0, alcoholFilter)
        val filteredList2 = filterCategory(filteredList1, categoryFilter)

        listAdapter.refreshData(filteredList2)
    }

    private fun filterFavorite(cocktailList: List<CocktailDbEntity>): List<CocktailDbEntity> {
        return cocktailList.filter { it.isFavorite }
    }

    private fun filterAlcohol(cocktailList: List<CocktailDbEntity>, filter: AlcoholDrinkFilter?): List<CocktailDbEntity> {
        return when (filter) {
            AlcoholDrinkFilter.ALCOHOLIC -> cocktailList.filter { it.alcoholic == AlcoholDrinkFilter.ALCOHOLIC.key }
            AlcoholDrinkFilter.NON_ALCOHOLIC -> cocktailList.filter { it.alcoholic == AlcoholDrinkFilter.NON_ALCOHOLIC.key }
            AlcoholDrinkFilter.OPTIONAL_ALCOHOL -> cocktailList.filter { it.alcoholic == AlcoholDrinkFilter.OPTIONAL_ALCOHOL.key }
            else -> cocktailList
        }
    }

    private fun filterCategory(cocktailList: List<CocktailDbEntity>, filter: CategoryDrinkFilter?): List<CocktailDbEntity> {
        return when (filter) {
            CategoryDrinkFilter.ORDINARY_DRINK -> cocktailList.filter { it.category == CategoryDrinkFilter.ORDINARY_DRINK.key }
            CategoryDrinkFilter.COCKTAIL -> cocktailList.filter { it.category == CategoryDrinkFilter.COCKTAIL.key }
            CategoryDrinkFilter.MILK_FLOAT_SHAKE -> cocktailList.filter { it.category == CategoryDrinkFilter.MILK_FLOAT_SHAKE.key }
            CategoryDrinkFilter.OTHER_UNKNOWN -> cocktailList.filter { it.category == CategoryDrinkFilter.OTHER_UNKNOWN.key }
            CategoryDrinkFilter.COCOA -> cocktailList.filter { it.category == CategoryDrinkFilter.COCOA.key }
            CategoryDrinkFilter.SHOT -> cocktailList.filter { it.category == CategoryDrinkFilter.SHOT.key }
            CategoryDrinkFilter.COFFEE_TEA -> cocktailList.filter { it.category == CategoryDrinkFilter.COFFEE_TEA.key }
            CategoryDrinkFilter.HOMEMADE_LIQUEUR -> cocktailList.filter { it.category == CategoryDrinkFilter.HOMEMADE_LIQUEUR.key }
            CategoryDrinkFilter.PUNCH_PARTY_DRINK -> cocktailList.filter { it.category == CategoryDrinkFilter.PUNCH_PARTY_DRINK.key }
            CategoryDrinkFilter.BEER -> cocktailList.filter { it.category == CategoryDrinkFilter.BEER.key }
            CategoryDrinkFilter.SOFT_DRINK_SODA -> cocktailList.filter { it.category == CategoryDrinkFilter.SOFT_DRINK_SODA.key }
            else -> cocktailList
        }
    }

}