package com.mirogal.cocktail.ui.savelist

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.receiver.BatteryChangeReceiver
import com.mirogal.cocktail.ui.base.BaseFragment
import com.mirogal.cocktail.ui.detail.DetailActivity
import com.mirogal.cocktail.ui.savelist.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.ui.savelist.filter.CategoryDrinkFilter
import com.mirogal.cocktail.ui.searchlist.SearchListActivity
import com.mirogal.cocktail.ui.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.content_save_list.*
import kotlinx.android.synthetic.main.fragment_save_list.*
import kotlinx.android.synthetic.main.layout_battery_indicator.*
import kotlinx.android.synthetic.main.layout_save_list_empty.*


class SaveListFragment : BaseFragment(), ListAdapter.OnItemClickListener,
        ListAdapter.OnItemLongClickListener, BatteryChangeReceiver.OnBatteryChangeListener {

    override val contentLayoutResId = R.layout.fragment_save_list
    private var listener: OnFragmentActionListener? = null

    private lateinit var viewModel: ViewModel
    private lateinit var listAdapter: ListAdapter
    private lateinit var cocktailList: List<CocktailDbEntity>
    var alcoholFilter: AlcoholDrinkFilter? = null
    var categoryFilter: CategoryDrinkFilter? = null

    private lateinit var proposeDrinkReceiver: BroadcastReceiver
    private val batteryChangeReceiver = BatteryChangeReceiver()

    companion object {
        fun newInstance() = SaveListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnFragmentActionListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnArticleSelectedListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.save_list_label)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        setList()
        setReceiver()

        fab_search.setOnClickListener {
            openSearchListActivity()
        }

        btn_battery_indicator_close.setOnClickListener {
            layout_charge_indicator.visibility = View.INVISIBLE
        }
    }

    private fun setReceiver() {
        batteryChangeReceiver.setBatteryChangeListener(this)

        proposeDrinkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                showProposeDrink(intent!!.getIntExtra("KEY", 20))
            }
        }
    }

    private fun setList() {
        val listColumn = when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 1
        }
        rv_save_list.layoutManager = GridLayoutManager(requireContext(), listColumn)

        val spaceInPixel = resources.getDimensionPixelSize(R.dimen.offset_16)
        val itemDecoration = SpaceItemDecoration(listColumn, spaceInPixel, true, 0)
        rv_save_list.addItemDecoration(itemDecoration)

        listAdapter = ListAdapter(requireContext(), this, this)
        viewModel.cocktailList.observe(viewLifecycleOwner, Observer { list: List<CocktailDbEntity> ->
            cocktailList = list
            val filteredList = filterAlcohol(list, alcoholFilter)
            if (filteredList.isNotEmpty()) {
                showData()
            } else {
                showEmpty()
            }
            listAdapter.refreshData(filteredList)
        })
        rv_save_list.adapter = listAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_list_function, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> { listener?.onButtonFilterClick(alcoholFilter, categoryFilter)
                return true }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        requireContext().registerReceiver(proposeDrinkReceiver, IntentFilter("ACTION_SNACKBAR"))
        requireContext().registerReceiver(batteryChangeReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        IntentFilter().apply {
            addAction("android.intent.action.BATTERY_LOW")
            addAction("android.intent.action.BATTERY_OK")
        }
    }

    override fun onStop() {
        requireContext().unregisterReceiver(proposeDrinkReceiver)
        requireContext().unregisterReceiver(batteryChangeReceiver)
        super.onStop()
    }


    override fun onItemClick(cocktail: CocktailDbEntity?) {
        openDetailActivity(cocktail!!)
    }

    override fun onItemLongClick(cocktailId: Int) {
        viewModel.deleteCocktail(cocktailId)
    }

    override fun onBatteryChange(level: Int, state: Int) {
        showChargeLevel(level.toString())
        showChargeState(state)
    }


    private fun openSearchListActivity() {
        val intent = Intent(activity, SearchListActivity::class.java)
        startActivity(intent)
    }

    private fun openDetailActivity(cocktail: CocktailDbEntity) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity::class.java.simpleName, cocktail)
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
                    tv_charge.setTextColor(ContextCompat.getColor(requireContext(), R.color.battery_status_charging))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        iv_charge.setImageResource(R.drawable.ic_battery_charging)
                    }
                    iv_charge.setColorFilter(ContextCompat.getColor(requireContext(), R.color.battery_status_charging))
                }
                2 -> {
                    tv_charge.setTextColor(ContextCompat.getColor(requireContext(), R.color.battery_status_ok))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        iv_charge.setImageResource(R.drawable.ic_battery_full)
                    }
                    iv_charge.setColorFilter(ContextCompat.getColor(requireContext(), R.color.battery_status_ok))
                }
                3 -> {
                    tv_charge.setTextColor(ContextCompat.getColor(requireContext(), R.color.battery_status_low))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        iv_charge.setImageResource(R.drawable.ic_battery_alert)
                    }
                    iv_charge.setColorFilter(ContextCompat.getColor(requireContext(), R.color.battery_status_low))
                }
            }
        }
    }

    private fun showProposeDrink(id: Int) {
        var entity: CocktailDbEntity? = null
        viewModel.cocktailList.observe(viewLifecycleOwner, Observer { list: List<CocktailDbEntity> ->
            if (list.isNotEmpty()) {
                if (list.size > 1) {
                    entity = list[0]
                    if (entity!!.id == id) {
                        entity = list[1]
                    }
                }
            }
        })
        if (entity != null) {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    "Переглянути " + entity!!.name, Snackbar.LENGTH_LONG)
                    .setAction("Переглянути") {
                        openDetailActivity(entity!!)
                    }.show()
        }
    }

    fun setFilter(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        this.alcoholFilter = alcoholFilter
        this.categoryFilter = categoryFilter
        val filteredList = filterAlcohol(cocktailList, alcoholFilter)
        listAdapter.refreshData(filteredList)
    }

    private fun filterAlcohol(cocktailList: List<CocktailDbEntity>, filter: AlcoholDrinkFilter?): List<CocktailDbEntity> {
        return when (filter) {
            AlcoholDrinkFilter.ALCOHOLIC -> cocktailList.filter { it.alcoholic == AlcoholDrinkFilter.ALCOHOLIC.key }
            AlcoholDrinkFilter.NON_ALCOHOLIC -> cocktailList.filter { it.alcoholic == AlcoholDrinkFilter.NON_ALCOHOLIC.key }
            AlcoholDrinkFilter.OPTIONAL_ALCOHOL -> cocktailList.filter { it.alcoholic == AlcoholDrinkFilter.OPTIONAL_ALCOHOL.key }
            else -> cocktailList
        }
    }

//    private fun filterCategory(cocktailList: List<CocktailDbEntity>, filter: CategoryDrinkFilter?): List<CocktailDbEntity> {
//        return when (filter) {
//            CategoryDrinkFilter.ORDINARY_DRINK -> cocktailList.filter { it.category == CategoryDrinkFilter.ORDINARY_DRINK.key }
//            CategoryDrinkFilter.COCKTAIL -> cocktailList.filter { it.category == CategoryDrinkFilter.COCKTAIL.key }
//            CategoryDrinkFilter.MILK_FLOAT_SHAKE -> cocktailList.filter { it.category == CategoryDrinkFilter.MILK_FLOAT_SHAKE.key }
//            CategoryDrinkFilter.OTHER_UNKNOWN -> cocktailList.filter { it.category == CategoryDrinkFilter.OTHER_UNKNOWN.key }
//            CategoryDrinkFilter.COCOA -> cocktailList.filter { it.category == CategoryDrinkFilter.COCOA.key }
//            CategoryDrinkFilter.SHOT -> cocktailList.filter { it.category == CategoryDrinkFilter.SHOT.key }
//            CategoryDrinkFilter.COFFEE_TEA -> cocktailList.filter { it.category == CategoryDrinkFilter.COFFEE_TEA.key }
//            CategoryDrinkFilter.HOMEMADE_LIQUEUR -> cocktailList.filter { it.category == CategoryDrinkFilter.HOMEMADE_LIQUEUR.key }
//            CategoryDrinkFilter.PUNCH_PARTY_DRINK -> cocktailList.filter { it.category == CategoryDrinkFilter.PUNCH_PARTY_DRINK.key }
//            CategoryDrinkFilter.BEER -> cocktailList.filter { it.category == CategoryDrinkFilter.BEER.key }
//            CategoryDrinkFilter.SOFT_DRINK_SODA -> cocktailList.filter { it.category == CategoryDrinkFilter.SOFT_DRINK_SODA.key }
//            else -> cocktailList
//        }
//    }

    interface OnFragmentActionListener {
        fun onButtonFilterClick(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?)
    }

}