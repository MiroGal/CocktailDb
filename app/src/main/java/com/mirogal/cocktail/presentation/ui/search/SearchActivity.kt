package com.mirogal.cocktail.presentation.ui.search

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mirogal.cocktail.R
import com.mirogal.cocktail.databinding.ActivitySearchBinding
import com.mirogal.cocktail.presentation.extension.baseViewModels
import com.mirogal.cocktail.presentation.model.cocktail.CocktailModel
import com.mirogal.cocktail.presentation.ui.base.BaseActivity
import com.mirogal.cocktail.presentation.ui.search.adapter.SearchListAdapter
import com.mirogal.cocktail.presentation.ui.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search_content.*
import kotlinx.android.synthetic.main.layout_drink_history_empty.*
import kotlinx.android.synthetic.main.layout_search_drink_preview.*

class SearchActivity : BaseActivity<SearchViewModel, ActivitySearchBinding>(),
        SearchListAdapter.OnItemClickListener {

    override val contentLayoutResId = R.layout.activity_search
    override val viewModel: SearchViewModel by baseViewModels()

    private val listAdapter = SearchListAdapter(this, this)

    override fun configureDataBinding(binding: ActivitySearchBinding) {
        super.configureDataBinding(binding)
        dataBinding.viewmodel = viewModel
    }

    override fun configureView(savedInstanceState: Bundle?) {
        super.configureView(savedInstanceState)

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

    override fun configureObserver() {
        super.configureObserver()

        viewModel.searchResultCocktailListLiveData.observe(this, Observer { list ->
            when {
                list == null -> showPreview()
                list.isEmpty() -> showEmpty()
                else -> {
                    listAdapter.refreshData(list)
                    showData()
                }
            }
        })
        rv_search_list.adapter = listAdapter
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
            searchView.setQuery(viewModel.searchQueryLiveData.value ?: "", false) // set searchText
            setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(s: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(s: String): Boolean {
                        viewModel.searchQueryLiveData.value = when {
                            s.isNotEmpty() -> s
                            else -> null
                        }
                        return false
                    }
                }
            )
        }
        return true
    }


    override fun onItemClick(cocktailModel: CocktailModel) {
        viewModel.saveCocktail(cocktailModel)
        openDrinkDetailActivity(cocktailModel.id, cocktailModel.names.baseValue)
    }


    private fun openDrinkDetailActivity(cocktailId: Long, cocktailName: String?) {
        val intent = Intent(this, com.mirogal.cocktail.presentation.ui.detail.DetailActivity::class.java)
        intent.putExtra("cocktailId", cocktailId)
        intent.putExtra("cocktailName", cocktailName)
        startActivity(intent)
    }

    private fun showData() {
        if (rv_search_list.visibility == View.GONE) {
            rv_search_list.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE
            layoutStart.visibility = View.GONE
        }
    }

    private fun showEmpty() {
        if (layoutEmpty.visibility == View.GONE) {
            rv_search_list.visibility = View.GONE
            layoutEmpty.visibility = View.VISIBLE
            layoutStart.visibility = View.GONE
        }
    }

    private fun showPreview() {
        if (layoutStart.visibility == View.GONE) {
            rv_search_list.visibility = View.GONE
            layoutEmpty.visibility = View.GONE
            layoutStart.visibility = View.VISIBLE
        }
    }

}