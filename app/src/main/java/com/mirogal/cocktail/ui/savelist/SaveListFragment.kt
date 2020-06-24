package com.mirogal.cocktail.ui.savelist

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
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
import kotlinx.android.synthetic.main.fragment_drink_history.*
import kotlinx.android.synthetic.main.fragment_save_list.*
import kotlinx.android.synthetic.main.layout_battery_indicator.*
import kotlinx.android.synthetic.main.layout_save_list_empty.*


class SaveListFragment : BaseFragment(), BatteryChangeReceiver.OnBatteryChangeListener {

    override val contentLayoutResId = R.layout.fragment_save_list
    private var listener: OnFragmentActionListener? = null

    private lateinit var viewModel: ViewModel
    private lateinit var cocktailList: List<CocktailDbEntity>
    private var alcoholFilter: AlcoholDrinkFilter? = null
    private var categoryFilter: CategoryDrinkFilter? = null

    private lateinit var pagerAdapter: SaveListPagerAdapter

    private lateinit var proposeDrinkReceiver: BroadcastReceiver
    private val batteryChangeReceiver = BatteryChangeReceiver()

    companion object {
        fun newInstance() = SaveListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnFragmentActionListener
        if (listener == null) {
            throw ClassCastException("$context must implement Listener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.save_list_label)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        setList()
        setReceiver()
        setBtnFilterIcon()
        setPagerFragments()

        btn_toolbar_filter.setOnClickListener {
            listener?.onToolbarBtnFilterClick(alcoholFilter, categoryFilter)
        }

        btn_toolbar_filter.setOnLongClickListener {
            setFilter(AlcoholDrinkFilter.DISABLE, CategoryDrinkFilter.DISABLE)
            true
        }

        fab_search.setOnClickListener {
            openSearchListActivity()
        }

        btn_battery_indicator_close.setOnClickListener {
            layout_charge_indicator.visibility = View.INVISIBLE
        }
    }

    private fun setPagerFragments() {
        pagerAdapter = SaveListPagerAdapter(this)
        pager.adapter = pagerAdapter

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.save_list_tab_history)
            } else if (position == 1) {
                tab.text = getString(R.string.save_list_tab_favorite)
            }
        }.attach()
    }

    private fun setList() {
        viewModel.cocktailList.observe(viewLifecycleOwner, Observer { list: List<CocktailDbEntity> ->
            cocktailList = list
        })
    }

    private fun setBtnFilterIcon() {
        if ((alcoholFilter == null || alcoholFilter == AlcoholDrinkFilter.DISABLE)
                && (categoryFilter == null || categoryFilter == CategoryDrinkFilter.DISABLE)) {
            btn_toolbar_filter.setImageResource(R.drawable.ic_filter_list_disable)
        } else {
            btn_toolbar_filter.setImageResource(R.drawable.ic_filter_list_enable)
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
        if (cocktailList.isNotEmpty()) {
            if (cocktailList.size > 1) {
                do {
                    entity = cocktailList.shuffled()[0]
                } while (entity!!.id == id)
            }
        }
        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                "Переглянути ${entity!!.name}", Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.save_list_snackbar_btn_open_cocktail)) {
                    openDetailActivity(entity)
                }.show()
    }

    fun setFilter(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        this.alcoholFilter = alcoholFilter
        this.categoryFilter = categoryFilter
        setBtnFilterIcon()
    }

    interface OnFragmentActionListener {
        fun onToolbarBtnFilterClick(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?)
    }

}