package com.mirogal.cocktail.presentation.ui.main.history

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.base.dialog.RegularBottomSheetDialogFragment
import com.mirogal.cocktail.presentation.model.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.presentation.model.filter.CategoryDrinkFilter
import com.mirogal.cocktail.presentation.model.filter.DrinkFilterType
import com.mirogal.cocktail.presentation.model.history.HistoryPage
import kotlinx.android.synthetic.main.content_drink_filter.*
import kotlinx.android.synthetic.main.fragment_drink_filter.*
import kotlinx.android.synthetic.main.fragment_history_pager.toolbar


class DrinkFilterFragment : BaseFragment<HistoryViewModel>() {

    override val contentLayoutResId = R.layout.fragment_drink_filter
    override val viewModel: HistoryViewModel by activityViewModels()

    companion object {
        fun newInstance() = DrinkFilterFragment()
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.drink_filter_label)

        setFilterState()
        setOnCheckListener()

        btn_toolbar_back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        btn_result.setOnClickListener {
//            requireActivity().onBackPressed()
            createDialog()
        }

        btn_reset.setOnClickListener {
            viewModel.resetDrinkFilter()
            requireActivity().onBackPressed()
        }

        setObserver()
    }

    private fun setObserver() {
        viewModel.currentHistoryPage.observe(viewLifecycleOwner, Observer {
            if (it == HistoryPage.HISTORY) {
                btn_result_icon.setImageResource(R.drawable.ic_button_history)
            } else {
                btn_result_icon.setImageResource(R.drawable.ic_button_favorite)
            }
        })
        viewModel.filterResultStringLiveData.observe(viewLifecycleOwner, Observer {
            btn_result_text.text = it
        })
    }

    private fun setFilterState() {
        when (viewModel.drinkFilterLiveData.value?.get(DrinkFilterType.ALCOHOL)) {
            AlcoholDrinkFilter.ALCOHOLIC -> rb_alcoholic.isChecked = true
            AlcoholDrinkFilter.NON_ALCOHOLIC -> rb_non_alcoholic.isChecked = true
            AlcoholDrinkFilter.OPTIONAL_ALCOHOL -> rb_optional_alcohol.isChecked = true
            else -> rb_alcoholic_filter_disable.isChecked = true
        }
        when (viewModel.drinkFilterLiveData.value?.get(DrinkFilterType.CATEGORY)) {
            CategoryDrinkFilter.ORDINARY_DRINK -> rb_ordinary_drink.isChecked = true
            CategoryDrinkFilter.COCKTAIL -> rb_cocktail.isChecked = true
            CategoryDrinkFilter.MILK_FLOAT_SHAKE -> rb_milk_float_shake.isChecked = true
            CategoryDrinkFilter.OTHER_UNKNOWN -> rb_other_unknown.isChecked = true
            CategoryDrinkFilter.COCOA -> rb_cocoa.isChecked = true
            CategoryDrinkFilter.SHOT -> rb_shot.isChecked = true
            CategoryDrinkFilter.COFFEE_TEA -> rb_coffee_tea.isChecked = true
            CategoryDrinkFilter.HOMEMADE_LIQUEUR -> rb_homemade_liqueur.isChecked = true
            CategoryDrinkFilter.PUNCH_PARTY_DRINK -> rb_punch_arty_drink.isChecked = true
            CategoryDrinkFilter.BEER -> rb_beer.isChecked = true
            CategoryDrinkFilter.SOFT_DRINK_SODA -> rb_soft_drink_soda.isChecked = true
            else -> rb_category_filter_disable.isChecked = true
        }
    }

    private fun setOnCheckListener() {
        rg_alcohol_filter.setOnCheckedChangeListener { _, checkedId ->
            val drinkFilter = viewModel.drinkFilterLiveData.value
            when (checkedId) {
                rb_alcoholic.id -> drinkFilter?.put(DrinkFilterType.ALCOHOL, AlcoholDrinkFilter.ALCOHOLIC)
                rb_non_alcoholic.id -> drinkFilter?.put(DrinkFilterType.ALCOHOL, AlcoholDrinkFilter.NON_ALCOHOLIC)
                rb_optional_alcohol.id -> drinkFilter?.put(DrinkFilterType.ALCOHOL, AlcoholDrinkFilter.OPTIONAL_ALCOHOL)
                else -> drinkFilter?.put(DrinkFilterType.ALCOHOL, AlcoholDrinkFilter.DISABLE)
            }
            viewModel.drinkFilterLiveData.value = drinkFilter
        }
        rg_category_filter.setOnCheckedChangeListener { _, checkedId ->
            val drinkFilter = viewModel.drinkFilterLiveData.value
            when (checkedId) {
                rb_ordinary_drink.id -> drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.ORDINARY_DRINK)
                rb_cocktail.id -> drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.COCKTAIL)
                rb_milk_float_shake.id -> drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.MILK_FLOAT_SHAKE)
                rb_other_unknown.id -> drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.OTHER_UNKNOWN)
                rb_cocoa.id -> drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.COCOA)
                rb_shot.id -> drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.SHOT)
                rb_coffee_tea.id -> drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.COFFEE_TEA)
                rb_homemade_liqueur.id -> drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.HOMEMADE_LIQUEUR)
                rb_punch_arty_drink.id -> drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.PUNCH_PARTY_DRINK)
                rb_beer.id -> drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.BEER)
                rb_soft_drink_soda.id -> drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.SOFT_DRINK_SODA)
                else -> drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.DISABLE)
            }
            viewModel.drinkFilterLiveData.value = drinkFilter
        }
    }

    private fun createDialog() {
        RegularBottomSheetDialogFragment.newInstance {
            this.titleText="Test"
        }.show(requireActivity().supportFragmentManager)
    }

}