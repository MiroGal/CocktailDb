package com.mirogal.cocktail.presentation.ui.main.history

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.db.entity.CocktailDbEntity
import com.mirogal.cocktail.presentation.receiver.BatteryChangeReceiver
import com.mirogal.cocktail.presentation.service.ProposeDrinkService
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.detail.DrinkDetailActivity
import com.mirogal.cocktail.presentation.ui.main.MainViewModel
import com.mirogal.cocktail.presentation.ui.main.history.adapter.PagerAdapter
import com.mirogal.cocktail.presentation.ui.main.history.model.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.presentation.ui.main.history.model.filter.CategoryDrinkFilter
import com.mirogal.cocktail.presentation.ui.main.history.model.filter.DrinkFilterType
import com.mirogal.cocktail.presentation.ui.main.history.model.page.HistoryPage
import com.mirogal.cocktail.presentation.ui.main.history.model.sort.DrinkSort
import com.mirogal.cocktail.presentation.ui.search.SearchDrinkActivity
import com.mirogal.cocktail.presentation.ui.util.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.fragment_history_pager.*
import kotlinx.android.synthetic.main.layout_battery_indicator.*
import kotlinx.android.synthetic.main.layout_drink_filter_indicator.*
import java.util.*


class HistoryPagerFragment : BaseFragment<HistoryViewModel>(), BatteryChangeReceiver.OnBatteryChangeListener {

    override val contentLayoutResId = R.layout.fragment_history_pager
    override val viewModel: HistoryViewModel by activityViewModels()
    val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var pagerAdapter: PagerAdapter

    private var cocktailList: List<CocktailDbEntity>? = null

    private lateinit var proposeDrinkReceiver: BroadcastReceiver
    private val batteryChangeReceiver = BatteryChangeReceiver()

    companion object {
        fun newInstance() = HistoryPagerFragment()
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.drink_history_pager_label)

        setViewPager()
        setReceiver()

        toolbar_action_filter.setOnClickListener {
            addDrinkFilterFragment()
        }

        toolbar_action_filter.setOnLongClickListener {
            viewModel.resetDrinkFilter()
            true
        }

        toolbar_action_sort.setOnClickListener {
            viewModel.drinkSortLiveData.value = DrinkSort.NAME //TODO
        }

        toolbar_action_sort.setOnLongClickListener {
            viewModel.drinkSortLiveData.value = DrinkSort.DISABLE
            true
        }

        fab_search.setOnClickListener {
            openSearchDrinkActivity()
        }

        btn_battery_indicator_close.setOnClickListener {
            mainViewModel.isBatteryIndicatorVisibleLiveData.value = false
        }

        btn_item_filter_alcohol_close.setOnClickListener {
            val drinkFilter = viewModel.drinkFilterLiveData.value
            drinkFilter?.put(DrinkFilterType.ALCOHOL, AlcoholDrinkFilter.DISABLE)
            viewModel.drinkFilterLiveData.value = drinkFilter
        }

        btn_item_filter_category_close.setOnClickListener {
            val drinkFilter = viewModel.drinkFilterLiveData.value
            drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.DISABLE)
            viewModel.drinkFilterLiveData.value = drinkFilter
        }

        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    viewModel.currentHistoryPage.value = HistoryPage.HISTORY
                } else {
                    viewModel.currentHistoryPage.value = HistoryPage.FAVORITE
                }
            }
        })

        setObserver()
    }

    private fun setObserver() {
        viewModel.isDrinkFilterEnableLiveData.observe(viewLifecycleOwner, Observer {
            if (it) {
                toolbar_action_filter.setImageResource(R.drawable.ic_filter_list_disable)
            } else {
                toolbar_action_filter.setImageResource(R.drawable.ic_filter_list_enable)
            }
        })

        viewModel.isDrinkSortEnableLiveData.observe(viewLifecycleOwner, Observer {
            if (it) {
                toolbar_action_sort.setImageResource(R.drawable.ic_sort_list_disable)
            } else {
                toolbar_action_sort.setImageResource(R.drawable.ic_sort_list_enable)
            }
        })

        viewModel.drinkFilterLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                showFilterAlcohol(it[DrinkFilterType.ALCOHOL] as AlcoholDrinkFilter)
                showFilterCategory(it[DrinkFilterType.CATEGORY] as CategoryDrinkFilter)
            }
        })

        viewModel.historyCocktailListLiveData.observe(viewLifecycleOwner, Observer {
            cocktailList = it
        })

        mainViewModel.isBatteryIndicatorVisibleLiveData.observe(this, Observer {
            if (it) {
                layout_charge_indicator.visibility = View.VISIBLE
            } else {
                layout_charge_indicator.visibility = View.INVISIBLE
            }
        })
    }

    private fun setViewPager() {
        pagerAdapter = PagerAdapter(this)
        view_pager.setPageTransformer(ZoomOutPageTransformer()) // Animation of transition
        view_pager.adapter = pagerAdapter

        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.drink_history_pager_tab_history)
            } else if (position == 1) {
                tab.text = getString(R.string.drink_history_pager_tab_favorite)
            }
        }.attach()
    }

    private fun setReceiver() {
        batteryChangeReceiver.setBatteryChangeListener(this)

        proposeDrinkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                showProposeDrink(intent!!.getIntExtra(ProposeDrinkService::class.java.simpleName, 20))
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


    private fun openSearchDrinkActivity() {
        val intent = Intent(activity, SearchDrinkActivity::class.java)
        startActivity(intent)
    }

    private fun openDrinkDetailActivity(cocktailId: Int, cocktailName: String?) {
        val intent = Intent(activity, DrinkDetailActivity::class.java)
        intent.putExtra("cocktailId", cocktailId)
        intent.putExtra("cocktailName", cocktailName)
        startActivity(intent)
    }

    private fun addDrinkFilterFragment() {
        val newFragment = DrinkFilterFragment.newInstance()
        childFragmentManager.beginTransaction().apply {
            add(R.id.root_view, newFragment, DrinkFilterFragment::class.java.simpleName)
            commit()
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
        if (cocktailList != null && cocktailList!!.size > 1) {
            val entity: CocktailDbEntity? = cocktailList!!.filter{ it.id != id }.shuffled()[0]
            if (entity != null) {
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        "Переглянути ${entity.name}", Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.drink_history_pager_snackbar_btn_open_cocktail)) {
                            openDrinkDetailActivity(entity.id, entity.name)
                        }.show()
            }
        }
    }

    private fun showFilterAlcohol(filter: AlcoholDrinkFilter) {
        if (filter != AlcoholDrinkFilter.DISABLE) {
            item_alcohol_filter.visibility = View.VISIBLE
            item_alcohol_filter.setCardBackgroundColor(randomColor())
        }
        when (filter) {
            AlcoholDrinkFilter.ALCOHOLIC -> {
                iv_filter_alcohol_icon.setImageResource(R.drawable.ic_drink_alcohol_alcoholic)
                tv_filter_alcohol_name.text = AlcoholDrinkFilter.ALCOHOLIC.key
            }
            AlcoholDrinkFilter.NON_ALCOHOLIC -> {
                iv_filter_alcohol_icon.setImageResource(R.drawable.ic_drink_alcohol_non)
                tv_filter_alcohol_name.text = AlcoholDrinkFilter.NON_ALCOHOLIC.key
            }
            AlcoholDrinkFilter.OPTIONAL_ALCOHOL -> {
                iv_filter_alcohol_icon.setImageResource(R.drawable.ic_drink_alcohol_optional)
                tv_filter_alcohol_name.text = AlcoholDrinkFilter.OPTIONAL_ALCOHOL.key
            }
            else -> item_alcohol_filter.visibility = View.GONE
        }
    }

    private fun showFilterCategory(filter: CategoryDrinkFilter) {
        if (filter != CategoryDrinkFilter.DISABLE) {
            item_category_filter.visibility = View.VISIBLE
            item_category_filter.setCardBackgroundColor(randomColor())
            iv_filter_category_icon.setImageResource(R.drawable.ic_drink_category)
        }
        when (filter) {
            CategoryDrinkFilter.ORDINARY_DRINK -> tv_filter_category_name.text = CategoryDrinkFilter.ORDINARY_DRINK.key
            CategoryDrinkFilter.COCKTAIL -> tv_filter_category_name.text = CategoryDrinkFilter.COCKTAIL.key
            CategoryDrinkFilter.MILK_FLOAT_SHAKE -> tv_filter_category_name.text = CategoryDrinkFilter.MILK_FLOAT_SHAKE.key
            CategoryDrinkFilter.OTHER_UNKNOWN -> tv_filter_category_name.text = CategoryDrinkFilter.OTHER_UNKNOWN.key
            CategoryDrinkFilter.COCOA -> tv_filter_category_name.text = CategoryDrinkFilter.COCOA.key
            CategoryDrinkFilter.SHOT -> tv_filter_category_name.text = CategoryDrinkFilter.SHOT.key
            CategoryDrinkFilter.COFFEE_TEA -> tv_filter_category_name.text = CategoryDrinkFilter.COFFEE_TEA.key
            CategoryDrinkFilter.HOMEMADE_LIQUEUR -> tv_filter_category_name.text = CategoryDrinkFilter.HOMEMADE_LIQUEUR.key
            CategoryDrinkFilter.PUNCH_PARTY_DRINK -> tv_filter_category_name.text = CategoryDrinkFilter.PUNCH_PARTY_DRINK.key
            CategoryDrinkFilter.BEER -> tv_filter_category_name.text = CategoryDrinkFilter.BEER.key
            CategoryDrinkFilter.SOFT_DRINK_SODA -> tv_filter_category_name.text = CategoryDrinkFilter.SOFT_DRINK_SODA.key
            else -> item_category_filter.visibility = View.GONE
        }
    }

    private fun randomColor(): Int {
        val rnd = Random()
        return Color.argb(85, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

}