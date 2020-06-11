package com.mirogal.cocktail.ui.savelist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.study.charge.ChargeRestateReceiver
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.constant.IntentTag
import com.mirogal.cocktail.ui.detail.DetailActivity
import com.mirogal.cocktail.ui.searchlist.SearchListActivity
import com.mirogal.cocktail.ui.util.GridSpaceItemDecoration
import kotlinx.android.synthetic.main.activity_save_list.*
import kotlinx.android.synthetic.main.content_save_list.*
import kotlinx.android.synthetic.main.layout_save_list_empty.*


class SaveListActivity : BaseActivity(), ListAdapter.OnItemClickListener, ListAdapter.OnItemLongClickListener {

    private lateinit var viewModel: ViewModel
//    private val chargeRestateReceiver = ChargeRestateReceiver()
    private var proposeDrinkReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_list)

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
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.cocktailList.observe(this, Observer { pagedList: PagedList<CocktailDbEntity> ->
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

    override fun onStart() {
        super.onStart()
//        registerReceiver(chargeRestateReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
//        IntentFilter().apply {
//            addAction("android.intent.action.BATTERY_LOW")
//            addAction("android.intent.action.BATTERY_OK")
//        }

        proposeDrinkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                showProposeDrink(intent!!.getIntExtra("KEY", 20))
            }
        }
        registerReceiver(proposeDrinkReceiver, IntentFilter("ACTION_SNACKBAR"))
    }

    override fun onStop() {
//        unregisterReceiver(chargeRestateReceiver)
        unregisterReceiver(proposeDrinkReceiver)
        super.onStop()
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
        val intent = Intent(this@SaveListActivity, SearchListActivity::class.java)
        startActivity(intent)
    }

    private fun openCocktailDetailActivity(cocktail: CocktailDbEntity) {
        val intent = Intent(this@SaveListActivity, DetailActivity::class.java)
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

    private fun showProposeDrink(id: Int) {
        var entity: CocktailDbEntity? = null
        viewModel.cocktailList.observe(this, Observer { pagedList: PagedList<CocktailDbEntity> ->
            if (!pagedList.isEmpty()) {
                entity = pagedList[1]!!
                if (entity!!.id == id) {
                    entity = pagedList[2]!!
                }
            }
        })
        if (entity != null) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Переглянути " + entity!!.name, Snackbar.LENGTH_LONG)
                    .setAction("Переглянути", View.OnClickListener {
                        openCocktailDetailActivity(entity!!) }).show()
        }
    }

}