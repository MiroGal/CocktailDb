package com.mirogal.cocktail.ui.savelist

import android.content.Context
import android.os.Bundle
import android.view.View
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseFragment
import com.mirogal.cocktail.ui.savelist.filter.AlcoholDrinkFilter
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment() : BaseFragment() {

    override val contentLayoutResId = R.layout.fragment_filter
    private var listener: OnFragmentActionListener? = null
    private var alcoholFilter: AlcoholDrinkFilter? = null

    companion object {
        private const val ALCOHOL_DRINK_FILTER = "alcohol_drink_filter"

        fun newInstance(alcoholDrinkFilter: AlcoholDrinkFilter) : FilterFragment {
            val fragment = FilterFragment()
            val bundle = Bundle()
            bundle.putSerializable(ALCOHOL_DRINK_FILTER, alcoholDrinkFilter)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnFragmentActionListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnArticleSelectedListener")
        }

        alcoholFilter = arguments?.getSerializable(ALCOHOL_DRINK_FILTER) as AlcoholDrinkFilter?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (alcoholFilter) {
            AlcoholDrinkFilter.ALCOHOLIC -> rb_alcoholic.isChecked = true
            AlcoholDrinkFilter.NON_ALCOHOLIC -> rb_non_alcoholic.isChecked = true
            AlcoholDrinkFilter.OPTIONAL_ALCOHOL -> rb_optional_alcohol.isChecked = true
            else -> rb_alcoholic_filter_unable.isChecked = true
        }

        btn_apply.setOnClickListener {
            listener?.onApplyClick(Bundle.EMPTY)
            requireActivity().onBackPressed()
        }

        btn_cancel.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    interface OnFragmentActionListener {
        fun onApplyClick(filter: Bundle)
    }

}