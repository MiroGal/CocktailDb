package com.mirogal.cocktail.presentation.ui.searchnative

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
import com.mirogal.cocktail.datanative.db.model.CocktailDbModel
import com.mirogal.cocktail.datanative.network.model.NetworkStatus
import com.mirogal.cocktail.presentation.ui.basenative.BaseActivity
import com.mirogal.cocktail.presentation.ui.detail.DetailActivity
import com.mirogal.cocktail.presentation.ui.searchnative.adapter.SearchListAdapter
import com.mirogal.cocktail.presentation.ui.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search_content.*
import kotlinx.android.synthetic.main.layout_drink_history_empty.*
import kotlinx.android.synthetic.main.layout_search_drink_preview.*

class SearchActivity : BaseActivity<SearchViewModel>(),
        SearchListAdapter.OnItemClickListener {

    override val contentLayoutResId = R.layout.activity_search
    override val viewModel: SearchViewModel by viewModels()

    private val listAdapter = SearchListAdapter(this, this)

    private var searchText: String? = null

    override fun configureView(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val listColumn = when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 1
        }
        rv_search_list.layoutManager = GridLayoutManager(this, listColumn)

        val spaceInPixel = resources.getDimensionPixelSize(R.dimen.offset_16)
        val itemDecoration = SpaceItemDecoration(listColumn, spaceInPixel, true, 0)
        rv_search_list.addItemDecoration(itemDecoration)
    }

    override fun configureObserver(savedInstanceState: Bundle?) {
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

        viewModel.searchTextMutableLiveData.observe(this, Observer { query: String? ->
            searchText = query
            if (!(query != null && query.isNotEmpty())) {
                showPreview()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_search_toolbar_menu, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.apply {
            setIconifiedByDefault(false) // set inner icon
            isFocusable = true
            isIconified = false
            requestFocusFromTouch() // set focus
            if (searchText != null)
                searchView.setQuery(searchText, false) // set searchText
            setOnQueryTextListener(
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
        }
        return true
    }


    override fun onItemClick(cocktail: CocktailDbModel) {
        viewModel.addCocktailToDb(cocktail)
        openDrinkDetailActivity(cocktail.id, cocktail.name)
    }


    private fun openDrinkDetailActivity(cocktailId: Int, cocktailName: String?) {
        val intent = Intent(this, DetailActivity::class.java)
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