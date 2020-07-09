package com.mirogal.cocktail.presentation.ui.search

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.db.model.CocktailDbModel
import com.mirogal.cocktail.data.network.model.NetworkStatus
import com.mirogal.cocktail.presentation.ui.base.BaseActivity
import com.mirogal.cocktail.presentation.ui.detail.DrinkDetailActivity
import com.mirogal.cocktail.presentation.ui.search.adapter.ListAdapter
import com.mirogal.cocktail.presentation.ui.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_search_drink.*
import kotlinx.android.synthetic.main.content_search_drink.*
import kotlinx.android.synthetic.main.layout_drink_history_empty.*
import kotlinx.android.synthetic.main.layout_search_drink_preview.*


class SearchDrinkActivity : BaseActivity<SearchDrinkViewModel>(), ListAdapter.OnItemClickListener {

    override val contentLayoutResId = R.layout.activity_search_drink
    override val viewModel: SearchDrinkViewModel by viewModels()

    private var searchName: String? = null

    override fun configureView(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        setList()
    }

    private fun setList() {
        val listColumn = when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 1
        }
        rv_search_list.layoutManager = GridLayoutManager(this, listColumn)

        val spaceInPixel = resources.getDimensionPixelSize(R.dimen.offset_16)
        val itemDecoration = SpaceItemDecoration(listColumn, spaceInPixel, true, 0)
        rv_search_list.addItemDecoration(itemDecoration)

        val listAdapter = ListAdapter(this, this)
        viewModel.cocktailListLiveData.observe(this, Observer { pagedList: PagedList<CocktailDbModel?> ->
            try {
                listAdapter.submitList(pagedList)
            } catch (ignored: Exception) {
            }
        })
        rv_search_list.adapter = listAdapter

        viewModel.networkStatusLiveData.observe(this, Observer { status: NetworkStatus.Status ->
            if (status == NetworkStatus.EMPTY) {
                showEmpty()
            } else {
                showData()
            }
        })

        viewModel.searchNameMutableLiveData.observe(this, Observer { query: String? ->
            searchName = query
            if (!(query != null && query.isNotEmpty())) {
                showPreview()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_drink_toolbar, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.setIconifiedByDefault(false) // set inner icon
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.requestFocusFromTouch()
        if (searchName != null) {
            searchView.setQuery(searchName, false)
        }
        searchView.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(s: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(s: String): Boolean {
                        viewModel.setSearchName(s)
                        return false
                    }
                }
        )
        return true
    }


    override fun onItemClick(cocktail: CocktailDbModel) {
        viewModel.addCocktailToDb(cocktail)
        openDrinkDetailActivity(cocktail.id, cocktail.name)
    }


    private fun openDrinkDetailActivity(cocktailId: Int, cocktailName: String?) {
        val intent = Intent(this, DrinkDetailActivity::class.java)
        intent.putExtra("cocktailId", cocktailId)
        intent.putExtra("cocktailName", cocktailName)
        startActivity(intent)
    }

    private fun showData() {
        if (rv_search_list.visibility == View.INVISIBLE) {
            rv_search_list.visibility = View.VISIBLE
            layoutEmpty.visibility = View.INVISIBLE
            layoutStart.visibility = View.INVISIBLE
        }
    }

    private fun showEmpty() {
        if (layoutEmpty.visibility == View.INVISIBLE) {
            rv_search_list.visibility = View.INVISIBLE
            layoutEmpty.visibility = View.VISIBLE
            layoutStart.visibility = View.INVISIBLE
        }
    }

    private fun showPreview() {
        if (layoutStart.visibility == View.INVISIBLE) {
            rv_search_list.visibility = View.INVISIBLE
            layoutEmpty.visibility = View.INVISIBLE
            layoutStart.visibility = View.VISIBLE
        }
    }

}