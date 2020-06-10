package com.mirogal.cocktail.ui.searchlist

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.NetworkState
import com.mirogal.cocktail.ui.constant.IntentTag
import com.mirogal.cocktail.ui.detail.CocktailDetailActivity
import com.mirogal.cocktail.ui.util.GridSpaceItemDecoration
import kotlinx.android.synthetic.main.activity_cocktail_save_list.*
import kotlinx.android.synthetic.main.content_cocktail_save_list.*
import kotlinx.android.synthetic.main.layout_save_list_empty.*
import kotlinx.android.synthetic.main.layout_search_list_start.*

class CocktailSearchListActivity : BaseActivity(), ListAdapter.OnItemClickListener {

    private lateinit var viewModel: ViewModel
    private var requestQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_search_list)

        setSupportActionBar(toolbar)

        val listColumn = when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 1
        }
        recyclerView.layoutManager = GridLayoutManager(this, listColumn)

        val spaceInPixel = resources.getDimensionPixelSize(R.dimen.padding_horizontal)
        val itemDecoration = GridSpaceItemDecoration(listColumn, spaceInPixel, true, 0)
        recyclerView.addItemDecoration(itemDecoration)

        val listAdapter = ListAdapter(this, this, R.layout.item_cocktail)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.cocktailList.observe(this, Observer { pagedList: PagedList<CocktailDbEntity?> ->
            try {
                listAdapter.submitList(pagedList)
            } catch (ignored: Exception) {
            }
        })
        recyclerView.adapter = listAdapter

        viewModel.networkStatus.observe(this, Observer { status: NetworkState.Status ->
            if (status == NetworkState.EMPTY) {
                showEmpty()
            } else {
                showData()
            }
        })

        viewModel.getRequestQuery().observe(this, Observer { query: String? ->
            requestQuery = query
            if (!(query != null && query.isNotEmpty())) {
                showStart()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cocktail_search_list_function, menu)
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
                        viewModel.setRequestQuery(s)
                        return false
                    }
                }
        )
        return true
    }

    override fun onItemClick(cocktail: CocktailDbEntity?) {
        viewModel.saveCocktail(cocktail)
        openCocktailDetailActivity(cocktail)
    }

    private fun openCocktailDetailActivity(cocktail: CocktailDbEntity?) {
        val intent = Intent(this@CocktailSearchListActivity, CocktailDetailActivity::class.java)
        intent.putExtra(IntentTag.COCKTAIL_ENTITY.toString(), cocktail)
        startActivity(intent)
    }

    private fun showData() {
        if (recyclerView.visibility == View.INVISIBLE) {
            recyclerView.visibility = View.VISIBLE
            layoutEmpty.visibility = View.INVISIBLE
            layoutStart.visibility = View.INVISIBLE
        }
    }

    private fun showEmpty() {
        if (layoutEmpty.visibility == View.INVISIBLE) {
            recyclerView.visibility = View.INVISIBLE
            layoutEmpty.visibility = View.VISIBLE
            layoutStart.visibility = View.INVISIBLE
        }
    }

    private fun showStart() {
        if (layoutStart.visibility == View.INVISIBLE) {
            recyclerView.visibility = View.INVISIBLE
            layoutEmpty.visibility = View.INVISIBLE
            layoutStart.visibility = View.VISIBLE
        }
    }

}