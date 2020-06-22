package com.mirogal.cocktail.ui.savelist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseFragment
import com.mirogal.cocktail.ui.savelist.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.ui.savelist.filter.CategoryDrinkFilter
import kotlinx.android.synthetic.main.content_filter.*
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.fragment_save_list.toolbar


class FilterFragment : BaseFragment() {

    override val contentLayoutResId = R.layout.fragment_filter
    private var listener: OnFragmentActionListener? = null
    private var alcoholFilter: AlcoholDrinkFilter? = null
    private var categoryFilter: CategoryDrinkFilter? = null

    companion object {
        private const val ALCOHOL_DRINK_FILTER = "alcohol_drink_filter"
        private const val CATEGORY_DRINK_FILTER = "category_drink_filter"

        fun newInstance(alcoholDrinkFilter: AlcoholDrinkFilter?, categoryDrinkFilter: CategoryDrinkFilter?): FilterFragment {
            val fragment = FilterFragment()
            val bundle = Bundle()
            bundle.putSerializable(ALCOHOL_DRINK_FILTER, alcoholDrinkFilter)
            bundle.putSerializable(CATEGORY_DRINK_FILTER, categoryDrinkFilter)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnFragmentActionListener
        if (listener == null) {
            throw ClassCastException("$context must implement Listener")
        }

        alcoholFilter = arguments?.getSerializable(ALCOHOL_DRINK_FILTER) as AlcoholDrinkFilter?
        categoryFilter = arguments?.getSerializable(CATEGORY_DRINK_FILTER) as CategoryDrinkFilter?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.filter_label)

        initFiler()
        setOnCheckListener()

        btn_toolbar_back.setOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }

        btn_apply.setOnClickListener {
            listener?.onActionButtonClick(alcoholFilter, categoryFilter)
            requireActivity().onBackPressed()
        }

        btn_reset.setOnClickListener {
            listener?.onActionButtonClick(AlcoholDrinkFilter.DISABLE, CategoryDrinkFilter.DISABLE)
            requireActivity().onBackPressed()
        }
    }

    private fun initFiler() {
        when (alcoholFilter) {
            AlcoholDrinkFilter.ALCOHOLIC -> rb_alcoholic.isChecked = true
            AlcoholDrinkFilter.NON_ALCOHOLIC -> rb_non_alcoholic.isChecked = true
            AlcoholDrinkFilter.OPTIONAL_ALCOHOL -> rb_optional_alcohol.isChecked = true
            else -> rb_alcoholic_filter_disable.isChecked = true
        }
        when (categoryFilter) {
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
            alcoholFilter = when (checkedId) {
                rb_alcoholic.id -> AlcoholDrinkFilter.ALCOHOLIC
                rb_non_alcoholic.id -> AlcoholDrinkFilter.NON_ALCOHOLIC
                rb_optional_alcohol.id -> AlcoholDrinkFilter.OPTIONAL_ALCOHOL
                else -> AlcoholDrinkFilter.DISABLE
            }
        }
        rg_category_filter.setOnCheckedChangeListener { _, checkedId ->
            categoryFilter = when (checkedId) {
                rb_ordinary_drink.id -> CategoryDrinkFilter.ORDINARY_DRINK
                rb_cocktail.id -> CategoryDrinkFilter.COCKTAIL
                rb_milk_float_shake.id -> CategoryDrinkFilter.MILK_FLOAT_SHAKE
                rb_other_unknown.id -> CategoryDrinkFilter.OTHER_UNKNOWN
                rb_cocoa.id -> CategoryDrinkFilter.COCOA
                rb_shot.id -> CategoryDrinkFilter.SHOT
                rb_coffee_tea.id -> CategoryDrinkFilter.COFFEE_TEA
                rb_homemade_liqueur.id -> CategoryDrinkFilter.HOMEMADE_LIQUEUR
                rb_punch_arty_drink.id -> CategoryDrinkFilter.PUNCH_PARTY_DRINK
                rb_beer.id -> CategoryDrinkFilter.BEER
                rb_soft_drink_soda.id -> CategoryDrinkFilter.SOFT_DRINK_SODA
                else -> CategoryDrinkFilter.DISABLE
            }
        }
    }

    interface OnFragmentActionListener {
        fun onActionButtonClick(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?)
    }

}