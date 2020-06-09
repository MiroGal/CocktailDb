package com.mirogal.cocktail.presentation.ui.savelist

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.presentation.ui.constant.IntentTag
import com.mirogal.cocktail.presentation.ui.detail.CocktailDetailActivity
import com.mirogal.cocktail.presentation.ui.searchlist.CocktailSearchListActivity
import com.mirogal.cocktail.presentation.ui.util.GridSpaceItemDecoration
import com.mirogal.cocktail.presentation.viewmodel.savelist.CocktailSaveListViewModel
import kotlinx.android.synthetic.main.activity_cocktail_save_list.*
import kotlinx.android.synthetic.main.content_cocktail_save_list.*
import kotlinx.android.synthetic.main.layout_save_list_empty.*

class CocktailSaveListActivity : AppCompatActivity(), ListAdapter.OnItemClickListener, ListAdapter.OnItemLongClickListener {

    private lateinit var viewModel: CocktailSaveListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_save_list)

        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(R.string.save_list_label)

        val listColumn = when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 1
        }
        recyclerView.layoutManager = GridLayoutManager(this, listColumn)

        val spaceInPixel = resources.getDimensionPixelSize(R.dimen.padding_horizontal)
        val itemDecoration = GridSpaceItemDecoration(listColumn, spaceInPixel, true, 0)
        recyclerView.addItemDecoration(itemDecoration)

        val listAdapter = ListAdapter(this, this, this, R.layout.item_cocktail)
        viewModel = ViewModelProvider(this).get(CocktailSaveListViewModel::class.java)
        viewModel.cocktailList.observe(this, Observer { pagedList: PagedList<CocktailDbEntity?> ->
            if (!pagedList.isEmpty()) {
                showData()
            } else {
                showEmpty()
            }
            try {
                listAdapter.submitList(pagedList)
            } catch (ignored: Exception) {
            }
        })
        recyclerView.adapter = listAdapter
    }


    override fun onItemClick(cocktail: CocktailDbEntity?) {
        openCocktailDetailActivity(cocktail!!)
    }

    override fun onItemLongClick(cocktailId: Int) {
        viewModel.deleteCocktail(cocktailId)
    }

    fun onFabClick(view: View) {
        openCocktailSearchListActivity()
    }


    private fun openCocktailSearchListActivity() {
        val intent = Intent(this@CocktailSaveListActivity, CocktailSearchListActivity::class.java)
        startActivity(intent)
    }

    private fun openCocktailDetailActivity(cocktail: CocktailDbEntity) {
        val intent = Intent(this@CocktailSaveListActivity, CocktailDetailActivity::class.java)
        intent.putExtra(IntentTag.COCKTAIL_ENTITY.toString(), cocktail)
        startActivity(intent)
    }

    private fun showData() {
        if (recyclerView.visibility == View.INVISIBLE) {
            recyclerView.visibility = View.VISIBLE
            layoutEmpty.visibility = View.INVISIBLE
        }
    }

    private fun showEmpty() {
        if (layoutEmpty.visibility == View.INVISIBLE) {
            recyclerView.visibility = View.INVISIBLE
            layoutEmpty.visibility = View.VISIBLE
        }
    }

}