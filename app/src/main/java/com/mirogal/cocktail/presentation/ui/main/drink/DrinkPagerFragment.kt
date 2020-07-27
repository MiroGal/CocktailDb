package com.mirogal.cocktail.presentation.ui.main.drink

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.mirogal.cocktail.R
import com.mirogal.cocktail.datanative.db.model.CocktailDbModel
import com.mirogal.cocktail.presentation.modelnative.drink.DrinkPage
import com.mirogal.cocktail.presentation.modelnative.filter.*
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.detail.DetailActivity
import com.mirogal.cocktail.presentation.ui.main.MainViewModel
import com.mirogal.cocktail.presentation.ui.main.drink.adapter.DrinkPagerAdapter
import com.mirogal.cocktail.presentation.ui.main.drink.dialog.DayDrinkDialogFragment
import com.mirogal.cocktail.presentation.ui.main.drink.dialog.DrinkSortDialogFragment
import com.mirogal.cocktail.presentation.ui.search.SearchActivity
import com.mirogal.cocktail.presentation.ui.util.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.fragment_drink_pager.*
import kotlinx.android.synthetic.main.layout_battery_indicator.*
import kotlinx.android.synthetic.main.layout_drink_filter_indicator.*
import java.util.*

class DrinkPagerFragment : BaseFragment<DrinkViewModel>() {

    override val contentLayoutResId = R.layout.fragment_drink_pager
    override val viewModel: DrinkViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var drinkPagerAdapter: DrinkPagerAdapter

    companion object {
        fun newInstance() = DrinkPagerFragment()
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.drink_pager_label)

        toolbar_action_filter.apply {
            setOnClickListener(onClickListener)
            setOnLongClickListener(onLongClickListener)
        }
        toolbar_action_sort.apply {
            setOnClickListener(onClickListener)
            setOnLongClickListener(onLongClickListener)
        }

        fab_search.setOnClickListener(onClickListener)

        btn_battery_indicator_close.setOnClickListener(onClickListener)

        btn_item_filter_category_close.setOnClickListener(onClickListener)
        btn_item_filter_alcohol_close.setOnClickListener(onClickListener)
        btn_item_filter_ingredient_close.setOnClickListener(onClickListener)
        btn_item_filter_glass_close.setOnClickListener(onClickListener)

        setViewPager()
    }

    private val onClickListener = View.OnClickListener {
        when (it) {
            toolbar_action_filter -> addDrinkFilterFragment()
            toolbar_action_sort -> showDrinkSortDialog()
            fab_search -> openSearchDrinkActivity()
            btn_battery_indicator_close -> mainViewModel.isBatteryIndicatorVisibleLiveData.value = false
            btn_item_filter_category_close -> {
                val drinkFilter = viewModel.drinkFilterLiveData.value
                drinkFilter?.put(DrinkFilterType.CATEGORY, DrinkFilterCategory.DISABLE)
                viewModel.drinkFilterLiveData.value = drinkFilter
            }
            btn_item_filter_alcohol_close -> {
                val drinkFilter = viewModel.drinkFilterLiveData.value
                drinkFilter?.put(DrinkFilterType.ALCOHOL, DrinkFilterAlcohol.DISABLE)
                viewModel.drinkFilterLiveData.value = drinkFilter
            }
            btn_item_filter_ingredient_close -> {
                val drinkFilter = viewModel.drinkFilterLiveData.value
                drinkFilter?.put(DrinkFilterType.INGREDIENT, DrinkFilterIngredient.DISABLE)
                viewModel.drinkFilterLiveData.value = drinkFilter
            }
            btn_item_filter_glass_close -> {
                val drinkFilter = viewModel.drinkFilterLiveData.value
                drinkFilter?.put(DrinkFilterType.GLASS, DrinkFilterGlass.DISABLE)
                viewModel.drinkFilterLiveData.value = drinkFilter
            }
        }
    }

    private val onLongClickListener = View.OnLongClickListener {
        when (it) {
            toolbar_action_filter -> {
                viewModel.resetDrinkFilter()
                true
            }
            toolbar_action_sort -> {
                viewModel.drinkSortLiveData.value = DrinkSort.DISABLE
                true
            }
            else -> false
        }
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

        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    viewModel.currentDrinkPage.value = DrinkPage.HISTORY
                } else {
                    viewModel.currentDrinkPage.value = DrinkPage.FAVORITE
                }
            }
        })
    }

    override fun configureObserver(view: View, savedInstanceState: Bundle?) {
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

        mainViewModel.isBatteryIndicatorVisibleLiveData.observe(this, Observer {
            if (it) {
                layout_charge_indicator.visibility = View.VISIBLE
            } else {
                layout_charge_indicator.visibility = View.INVISIBLE
            }
        })

        viewModel.proposeDrinkReceiverLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null)
                showProposeDrink(it)
        })

        viewModel.batteryChangeReceiverLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                showChargeLevel(it.first.toString())
                showChargeState(it.second)
            }
        })

        ProcessLifecycleOwner.get().lifecycle.addObserver(DrinkPagerObserver(requireActivity() as AppCompatActivity))
    }

    private fun openSearchDrinkActivity() {
        val intent = Intent(activity, SearchActivity::class.java)
        startActivity(intent)
    }

    private fun openDrinkDetailActivity(cocktailId: Int, cocktailName: String?) {
        val intent = Intent(activity, DetailActivity::class.java).apply {
            putExtra("cocktailId", cocktailId)
            putExtra("cocktailName", cocktailName)
        }
        startActivity(intent)
    }

    private fun addDrinkFilterFragment() {
        val newFragment = DrinkFilterFragment.newInstance()
        childFragmentManager.beginTransaction().apply {
            add(R.id.root_view, newFragment, DrinkFilterFragment::class.java.simpleName)
            commit()
        }
    }

    private fun showDrinkSortDialog() {
        val dialogFragment = DrinkSortDialogFragment.newInstance(
                viewModel.drinkSortLiveData.value ?: DrinkSort.DISABLE)
        dialogFragment.show(childFragmentManager, DrinkSortDialogFragment::class.java.simpleName)
    }

    fun showDayDrinkDialog(cocktailId: Int, cocktailName: String?) {
        val dialogFragment = DayDrinkDialogFragment.newInstance(cocktailId, cocktailName)
        dialogFragment.show(childFragmentManager, DayDrinkDialogFragment::class.java.simpleName)
    }

    private fun showProposeDrink(id: Int) {
        val cocktailList = viewModel.historyCocktailListLiveData.value
        if (cocktailList != null && cocktailList.size > 1) {
            val model: CocktailDbModel? = cocktailList.filter{ it.id != id }.shuffled()[0]
            if (model != null) {
                Snackbar.make(requireView().findViewById(R.id.container),
                        getString(R.string.drink_pager_snackbar_message) + ": " + model.name.toString(), Snackbar.LENGTH_LONG)
                        .setBackgroundTint(resources.getColor(R.color.background_primary))
                        .setTextColor(resources.getColor(R.color.txt_title))
                        .setAction(getString(R.string.drink_pager_snackbar_action_open_detail)) {
                            openDrinkDetailActivity(model.id, model.name)
                        }.show()
            }
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

    private fun showFilterCategory(filterCategory: DrinkFilterCategory) {
        if (filterCategory != DrinkFilterCategory.DISABLE) {
            if (item_filter_category.visibility != View.VISIBLE) {
                item_filter_category.setCardBackgroundColor(getRandomColor())
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
                item_filter_alcohol.setCardBackgroundColor(getRandomColor())
            }
            item_filter_alcohol.visibility = View.VISIBLE
            when (filterAlcohol) {
                DrinkFilterAlcohol.ALCOHOLIC -> iv_filter_alcohol_icon.setImageResource(R.drawable.ic_drink_alcohol_alcoholic)
                DrinkFilterAlcohol.NON_ALCOHOLIC -> iv_filter_alcohol_icon.setImageResource(R.drawable.ic_drink_alcohol_non)
                DrinkFilterAlcohol.OPTIONAL_ALCOHOL -> iv_filter_alcohol_icon.setImageResource(R.drawable.ic_drink_alcohol_optional)
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
                item_filter_ingredient.setCardBackgroundColor(getRandomColor())
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
                item_filter_glass.setCardBackgroundColor(getRandomColor())
            }
            item_filter_glass.visibility = View.VISIBLE
            iv_filter_glass_icon.setImageResource(R.drawable.ic_drink_glass)
            tv_filter_glass_name.text = filterGlass.key.replace("\\", "")
        } else {
            item_filter_glass.visibility = View.GONE
        }
    }

    private fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(85, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

}