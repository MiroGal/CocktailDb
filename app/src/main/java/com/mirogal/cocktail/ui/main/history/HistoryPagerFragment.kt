package com.mirogal.cocktail.ui.main.history

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
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mirogal.cocktail.R
import com.mirogal.cocktail.receiver.BatteryChangeReceiver
import com.mirogal.cocktail.service.ProposeDrinkService
import com.mirogal.cocktail.ui.base.BaseFragment
import com.mirogal.cocktail.ui.detail.DrinkDetailActivity
import com.mirogal.cocktail.ui.main.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.ui.main.filter.CategoryDrinkFilter
import com.mirogal.cocktail.ui.search.SearchDrinkActivity
import com.mirogal.cocktail.ui.util.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.fragment_history_pager.*
import kotlinx.android.synthetic.main.layout_battery_indicator.*
import kotlinx.android.synthetic.main.layout_drink_filter_indicator.*
import java.util.*


class HistoryPagerFragment : BaseFragment<HistoryPagerViewModel>(), BatteryChangeReceiver.OnBatteryChangeListener {

    override val contentLayoutResId = R.layout.fragment_history_pager
    override val viewModel: HistoryPagerViewModel by viewModels()

    private var listener: OnFragmentActionListener? = null

//    private var cocktailList: List<CocktailDbEntity>? = null
    private var alcoholFilter: AlcoholDrinkFilter? = null
    private var categoryFilter: CategoryDrinkFilter? = null

    private lateinit var pagerAdapter: PagerAdapter

    private lateinit var proposeDrinkReceiver: BroadcastReceiver
    private val batteryChangeReceiver = BatteryChangeReceiver()

    companion object {
        fun newInstance() = HistoryPagerFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnFragmentActionListener
        if (listener == null) {
            throw ClassCastException("$context must implement Listener")
        }
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.drink_history_pager_label)

        setList()
        setViewPager()
        setToolbarButtonFilterIcon()
        setReceiver()

        btn_toolbar_filter.setOnClickListener {
            listener?.onToolbarBtnFilterClick()
        }

        btn_toolbar_filter.setOnLongClickListener {
            setFilter(AlcoholDrinkFilter.DISABLE, CategoryDrinkFilter.DISABLE)
            true
        }

        fab_search.setOnClickListener {
            openSearchDrinkActivity()
        }

        btn_battery_indicator_close.setOnClickListener {
            layout_charge_indicator.visibility = View.INVISIBLE
        }

        btn_item_filter_alcohol_close.setOnClickListener {
            setFilter(AlcoholDrinkFilter.DISABLE, categoryFilter)
        }

        btn_item_filter_category_close.setOnClickListener {
            setFilter(alcoholFilter, CategoryDrinkFilter.DISABLE)
        }

        setFilter(alcoholFilter, categoryFilter)
    }

    private fun setList() {
//        viewModel.cocktailListLiveData.observe(viewLifecycleOwner, Observer { list ->
//            cocktailList = list
//        })
    }

    private fun setViewPager() {
        pagerAdapter = PagerAdapter(this)
        view_pager.setPageTransformer(ZoomOutPageTransformer())
//        pager.setPageTransformer(DepthPageTransformer())
        view_pager.adapter = pagerAdapter

        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.drink_history_pager_tab_history)
            } else if (position == 1) {
                tab.text = getString(R.string.drink_history_pager_tab_favorite)
            }
        }.attach()
    }

    private fun setToolbarButtonFilterIcon() {
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
//        if (cocktailList!!.isNotEmpty()) {
//            if (cocktailList!!.size > 1) {
//                var entity: CocktailDbEntity?
//                do {
//                    entity = cocktailList!!.shuffled()[0]
//                } while (entity!!.id == id)
//
//                Snackbar.make(requireActivity().findViewById(android.R.id.content),
//                        "Переглянути ${entity.name}", Snackbar.LENGTH_LONG)
//                        .setAction(getString(R.string.drink_history_pager_snackbar_btn_open_cocktail)) {
//                            openDrinkDetailActivity(entity.id, entity.name)
//                        }.show()
//            }
//        }
    }

    fun setFilter(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        this.alcoholFilter = alcoholFilter
        this.categoryFilter = categoryFilter

        setToolbarButtonFilterIcon()
        showFilterAlcohol()
        showFilterCategory()

        val fragment1 = pagerAdapter.fragment1
        if (fragment1 is DrinkHistoryFragment) {
            (fragment1 as DrinkHistoryFragment?)!!.setFilter(alcoholFilter, categoryFilter)
        }

        val fragment2 = pagerAdapter.fragment2
        if (fragment2 is DrinkFavoriteFragment) {
            (fragment2 as DrinkFavoriteFragment?)!!.setFilter(alcoholFilter, categoryFilter)
        }
    }

    private fun showFilterAlcohol() {
        if (alcoholFilter != null && alcoholFilter != AlcoholDrinkFilter.DISABLE) {
            item_alcohol_filter.visibility = View.VISIBLE
            item_alcohol_filter.setCardBackgroundColor(randomColor())
        }
        when (alcoholFilter) {
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

    private fun showFilterCategory() {
        if (categoryFilter != null && categoryFilter != CategoryDrinkFilter.DISABLE) {
            item_category_filter.visibility = View.VISIBLE
            item_category_filter.setCardBackgroundColor(randomColor())
            iv_filter_category_icon.setImageResource(R.drawable.ic_drink_category)
        }
        when (categoryFilter) {
            CategoryDrinkFilter.ORDINARY_DRINK -> { tv_filter_category_name.text = CategoryDrinkFilter.ORDINARY_DRINK.key }
            CategoryDrinkFilter.COCKTAIL -> { tv_filter_category_name.text = CategoryDrinkFilter.COCKTAIL.key }
            CategoryDrinkFilter.MILK_FLOAT_SHAKE -> { tv_filter_category_name.text = CategoryDrinkFilter.MILK_FLOAT_SHAKE.key }
            CategoryDrinkFilter.OTHER_UNKNOWN -> { tv_filter_category_name.text = CategoryDrinkFilter.OTHER_UNKNOWN.key }
            CategoryDrinkFilter.COCOA -> { tv_filter_category_name.text = CategoryDrinkFilter.COCOA.key }
            CategoryDrinkFilter.SHOT -> { tv_filter_category_name.text = CategoryDrinkFilter.SHOT.key }
            CategoryDrinkFilter.COFFEE_TEA -> { tv_filter_category_name.text = CategoryDrinkFilter.COFFEE_TEA.key }
            CategoryDrinkFilter.HOMEMADE_LIQUEUR -> { tv_filter_category_name.text = CategoryDrinkFilter.HOMEMADE_LIQUEUR.key }
            CategoryDrinkFilter.PUNCH_PARTY_DRINK -> { tv_filter_category_name.text = CategoryDrinkFilter.PUNCH_PARTY_DRINK.key }
            CategoryDrinkFilter.BEER -> { tv_filter_category_name.text = CategoryDrinkFilter.BEER.key }
            CategoryDrinkFilter.SOFT_DRINK_SODA -> { tv_filter_category_name.text = CategoryDrinkFilter.SOFT_DRINK_SODA.key }
            else -> item_category_filter.visibility = View.GONE
        }
    }

    private fun randomColor(): Int {
        val rnd = Random()
        return Color.argb(85, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }


    interface OnFragmentActionListener {
        fun onToolbarBtnFilterClick()
    }

}