package com.mirogal.cocktail.ui.savelist

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.study.battery.BatteryChangeReceiver
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.constant.IntentTag
import com.mirogal.cocktail.ui.detail.DetailActivity
import com.mirogal.cocktail.ui.searchlist.SearchListActivity
import com.mirogal.cocktail.ui.util.GridSpaceItemDecoration
import kotlinx.android.synthetic.main.activity_save_list.*
import kotlinx.android.synthetic.main.content_save_list.*
import kotlinx.android.synthetic.main.layout_charge_indicator.*
import kotlinx.android.synthetic.main.layout_save_list_empty.*


class SaveListActivity : BaseActivity(), ListAdapter.OnItemClickListener, ListAdapter.OnItemLongClickListener, BatteryChangeReceiver.OnBatteryChangeListener {

    private lateinit var viewModel: ViewModel
    private lateinit var proposeDrinkReceiver: BroadcastReceiver
    private val batteryChangeReceiver = BatteryChangeReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_list)

        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(R.string.save_list_label)

        val listColumn = when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 1
        }
        rv_save_list.layoutManager = GridLayoutManager(this, listColumn)

        val spaceInPixel = resources.getDimensionPixelSize(R.dimen.offset_16)
        val itemDecoration = GridSpaceItemDecoration(listColumn, spaceInPixel, true, 0)
        rv_save_list.addItemDecoration(itemDecoration)

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
        rv_save_list.adapter = listAdapter

        proposeDrinkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                showProposeDrink(intent!!.getIntExtra("KEY", 20))
            }
        }

        batteryChangeReceiver.setBatteryChangeListener(this)
    }

    override fun onStart() {
        super.onStart()
        Intent.ACTION_POWER_DISCONNECTED
        registerReceiver(proposeDrinkReceiver, IntentFilter("ACTION_SNACKBAR"))
        registerReceiver(batteryChangeReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        IntentFilter().apply {
            addAction("android.intent.action.BATTERY_LOW")
            addAction("android.intent.action.BATTERY_OK")
        }
    }

    override fun onStop() {
        unregisterReceiver(proposeDrinkReceiver)
        unregisterReceiver(batteryChangeReceiver)
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

    fun onCloseChargeIndicatorClick(view: View) {
        layout_charge_indicator.visibility = View.INVISIBLE
    }

    override fun onBatteryChange(level: Int, state: Int) {
        showChargeLevel(level.toString())
        showChargeState(state)
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
        if (rv_save_list.visibility == View.INVISIBLE) {
            rv_save_list.visibility = View.VISIBLE
            layoutEmpty.visibility = View.INVISIBLE
        }
    }

    private fun showEmpty() {
        if (layoutEmpty.visibility == View.INVISIBLE) {
            rv_save_list.visibility = View.INVISIBLE
            layoutEmpty.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showChargeLevel(level: String) {
        if (layout_charge_indicator.visibility == View.VISIBLE) {
            tv_charge.text = "$level %"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showChargeState(state: Int) {
        if (layout_charge_indicator.visibility == View.VISIBLE) {
            when (state) {
                1 -> {
                    tv_charge.setTextColor(ContextCompat.getColor(this, R.color.green))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        iv_charge.setImageResource(R.drawable.ic_battery_charging)
                    }
                    iv_charge.setColorFilter(ContextCompat.getColor(this, R.color.green))
                }
                2 -> {
                    tv_charge.setTextColor(ContextCompat.getColor(this, R.color.yellow))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        iv_charge.setImageResource(R.drawable.ic_battery_full)
                    }
                    iv_charge.setColorFilter(ContextCompat.getColor(this, R.color.yellow))
                }
                3 -> {
                    tv_charge.setTextColor(ContextCompat.getColor(this, R.color.red))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        iv_charge.setImageResource(R.drawable.ic_battery_alert)
                    }
                    iv_charge.setColorFilter(ContextCompat.getColor(this, R.color.red))
                }
            }
        }
    }

    private fun showProposeDrink(id: Int) {
        var entity: CocktailDbEntity? = null
        viewModel.cocktailList.observe(this, Observer { pagedList: PagedList<CocktailDbEntity> ->
            if (!pagedList.isEmpty()) {
                if (pagedList.size > 1) {
                    entity = pagedList[0]!!
                    if (entity!!.id == id) {
                        entity = pagedList[1]!!
                    }
                }
            }
        })
        if (entity != null) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Переглянути " + entity!!.name, Snackbar.LENGTH_LONG)
                    .setAction("Переглянути") {
                        openCocktailDetailActivity(entity!!) }.show()
        }
    }

}