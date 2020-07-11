package com.mirogal.cocktail.presentation.ui.main.drink

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
import com.mirogal.cocktail.data.db.model.CocktailDbModel
import com.mirogal.cocktail.presentation.model.filter.*
import com.mirogal.cocktail.presentation.model.history.HistoryPage
import com.mirogal.cocktail.presentation.receiver.BatteryChangeReceiver
import com.mirogal.cocktail.presentation.service.ProposeDrinkService
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.detail.DetailActivity
import com.mirogal.cocktail.presentation.ui.main.MainViewModel
import com.mirogal.cocktail.presentation.ui.main.drink.adapter.DrinkPagerAdapter
import com.mirogal.cocktail.presentation.ui.search.SearchActivity
import com.mirogal.cocktail.presentation.ui.util.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.fragment_drink_pager.*
import kotlinx.android.synthetic.main.layout_battery_indicator.*
import kotlinx.android.synthetic.main.layout_drink_filter_indicator.*
import java.util.*

class DrinkPagerFragment : BaseFragment<DrinkViewModel>(), BatteryChangeReceiver.OnBatteryChangeListener {

    override val contentLayoutResId = R.layout.fragment_drink_pager
    override val viewModel: DrinkViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var drinkPagerAdapter: DrinkPagerAdapter

    private var cocktailList: List<CocktailDbModel>? = null

    private lateinit var proposeDrinkReceiver: BroadcastReceiver
    private val batteryChangeReceiver = BatteryChangeReceiver()

    companion object {
        fun newInstance() = DrinkPagerFragment()
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.drink_pager_label)

        setViewPager()
        setReceiver()

        toolbar_action_filter.setOnClickListener { addDrinkFilterFragment() }

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

        fab_search.setOnClickListener { openSearchDrinkActivity() }

        btn_battery_indicator_close.setOnClickListener { mainViewModel.isBatteryIndicatorVisibleLiveData.value = false }

        btn_item_filter_category_close.setOnClickListener {
            val drinkFilter = viewModel.drinkFilterLiveData.value
            drinkFilter?.put(DrinkFilterType.CATEGORY, DrinkFilterCategory.DISABLE)
            viewModel.drinkFilterLiveData.value = drinkFilter
        }

        btn_item_filter_alcohol_close.setOnClickListener {
            val drinkFilter = viewModel.drinkFilterLiveData.value
            drinkFilter?.put(DrinkFilterType.ALCOHOL, DrinkFilterAlcohol.DISABLE)
            viewModel.drinkFilterLiveData.value = drinkFilter
        }

        btn_item_filter_ingredient_close.setOnClickListener {
            val drinkFilter = viewModel.drinkFilterLiveData.value
            drinkFilter?.put(DrinkFilterType.INGREDIENT, DrinkFilterIngredient.DISABLE)
            viewModel.drinkFilterLiveData.value = drinkFilter
        }

        btn_item_filter_glass_close.setOnClickListener {
            val drinkFilter = viewModel.drinkFilterLiveData.value
            drinkFilter?.put(DrinkFilterType.GLASS, DrinkFilterGlass.DISABLE)
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
                showFilterCategory(it[DrinkFilterType.CATEGORY] as DrinkFilterCategory)
                showFilterAlcohol(it[DrinkFilterType.ALCOHOL] as DrinkFilterAlcohol)
                showFilterIngredient(it[DrinkFilterType.INGREDIENT] as DrinkFilterIngredient)
                showFilterGlass(it[DrinkFilterType.GLASS] as DrinkFilterGlass)
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
        drinkPagerAdapter = DrinkPagerAdapter(this)
        view_pager.setPageTransformer(ZoomOutPageTransformer()) // Animation of transition
        view_pager.adapter = drinkPagerAdapter

        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.drink_pager_tab_history)
            } else if (position == 1) {
                tab.text = getString(R.string.drink_pager_tab_favorite)
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
        val intent = Intent(activity, SearchActivity::class.java)
        startActivity(intent)
    }

    private fun openDrinkDetailActivity(cocktailId: Int, cocktailName: String?) {
        val intent = Intent(activity, DetailActivity::class.java)
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
            val model: CocktailDbModel? = cocktailList!!.filter{ it.id != id }.shuffled()[0]
            if (model != null) {
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        "Переглянути ${model.name}", Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.drink_pager_snackbar_action_open_detail)) {
                            openDrinkDetailActivity(model.id, model.name)
                        }.show()
            }
        }
    }

    private fun showFilterCategory(filterCategory: DrinkFilterCategory) {
        if (filterCategory != DrinkFilterCategory.DISABLE) {
            if (item_filter_category.visibility != View.VISIBLE) {
                item_filter_category.setCardBackgroundColor(randomColor())
            }
            item_filter_category.visibility = View.VISIBLE
            iv_filter_category_icon.setImageResource(R.drawable.ic_drink_category)
            tv_filter_category_name.text = filterCategory.key.replace("\\", "")
        } else {
            item_filter_category.visibility = View.GONE
        }
    }

    private fun showFilterAlcohol(filterAlcohol: DrinkFilterAlcohol) {
        if (filterAlcohol != DrinkFilterAlcohol.DISABLE) {
            if (item_filter_alcohol.visibility != View.VISIBLE) {
                item_filter_alcohol.setCardBackgroundColor(randomColor())
            }
            item_filter_alcohol.visibility = View.VISIBLE
            when (filterAlcohol) {
                DrinkFilterAlcohol.ALCOHOLIC -> iv_filter_alcohol_icon.setImageResource(R.drawable.ic_drink_alcohol_alcoholic)
                DrinkFilterAlcohol.NON_ALCOHOLIC -> iv_filter_alcohol_icon.setImageResource(R.drawable.ic_drink_alcohol_non)
                DrinkFilterAlcohol.OPTIONAL_Alcohol -> iv_filter_alcohol_icon.setImageResource(R.drawable.ic_drink_alcohol_optional)
                else -> item_filter_alcohol.visibility = View.GONE
            }
            tv_filter_alcohol_name.text = filterAlcohol.key.replace("\\", "")
        } else {
            item_filter_alcohol.visibility = View.GONE
        }
    }

    private fun showFilterIngredient(filterIngredient: DrinkFilterIngredient) {
        if (filterIngredient != DrinkFilterIngredient.DISABLE) {
            if (item_filter_ingredient.visibility != View.VISIBLE) {
                item_filter_ingredient.setCardBackgroundColor(randomColor())
            }
            item_filter_ingredient.visibility = View.VISIBLE
            iv_filter_ingredient_icon.setImageResource(R.drawable.ic_drink_ingredient)
            tv_filter_ingredient_name.text = filterIngredient.key.replace("\\", "")
        } else {
            item_filter_ingredient.visibility = View.GONE
        }
    }

    private fun showFilterGlass(filterGlass: DrinkFilterGlass) {
        if (filterGlass != DrinkFilterGlass.DISABLE) {
            if (item_filter_glass.visibility != View.VISIBLE) {
                item_filter_glass.setCardBackgroundColor(randomColor())
            }
            item_filter_glass.visibility = View.VISIBLE
            iv_filter_glass_icon.setImageResource(R.drawable.ic_drink_glass)
            tv_filter_glass_name.text = filterGlass.key.replace("\\", "")
        } else {
            item_filter_glass.visibility = View.GONE
        }
    }

    private fun randomColor(): Int {
        val rnd = Random()
        return Color.argb(85, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

}