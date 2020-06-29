package com.mirogal.cocktail.ui.search

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.NetworkState
import com.mirogal.cocktail.ui.auth.AuthViewModel
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.detail.DrinkDetailActivity
import com.mirogal.cocktail.ui.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_search_drink.*
import kotlinx.android.synthetic.main.content_search_drink.*
import kotlinx.android.synthetic.main.layout_drink_history_empty.*
import kotlinx.android.synthetic.main.layout_search_drink_preview.*


class SearchDrinkActivity : BaseActivity(), ListAdapter.OnItemClickListener {

    override val viewModel: SearchDrinkViewModel by viewModels()

    private var requestQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_drink)
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
        viewModel.cocktailListViewModel.observe(this, Observer { pagedList: PagedList<CocktailDbEntity?> ->
            try {
                listAdapter.submitList(pagedList)
            } catch (ignored: Exception) {
            }
        })
        rv_search_list.adapter = listAdapter

        viewModel.networkStatus.observe(this, Observer { status: NetworkState.Status ->
            if (status == NetworkState.EMPTY) {
                showEmpty()
            } else {
                showData()
            }
        })

        viewModel.searchNameMutableLiveData.observe(this, Observer { query: String? ->
            requestQuery = query
            if (!(query != null && query.isNotEmpty())) {
                showPreview()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.drink_history_container_toolbar, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.setIconifiedByDefault(false) // set inner icon
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.requestFocusFromTouch()
        if (requestQuery != null) {
            searchView.setQuery(requestQuery, false)
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


    override fun onItemClick(cocktail: CocktailDbEntity?) {
        viewModel.addCocktailToDb(cocktail)
        openDrinkDetailActivity(cocktail)
    }


    private fun openDrinkDetailActivity(cocktail: CocktailDbEntity?) {
        val intent = Intent(this@SearchDrinkActivity, DrinkDetailActivity::class.java)
        intent.putExtra(DrinkDetailActivity::class.java.simpleName, cocktail)
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