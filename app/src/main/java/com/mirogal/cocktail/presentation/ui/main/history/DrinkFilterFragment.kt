package com.mirogal.cocktail.presentation.ui.main.history

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.filter.DrinkFilter
import com.mirogal.cocktail.presentation.model.filter.DrinkFilterType
import com.mirogal.cocktail.presentation.model.history.HistoryPage
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.main.history.dialog.DrinkFilterDialogFragment
import kotlinx.android.synthetic.main.content_drink_filter.*
import kotlinx.android.synthetic.main.fragment_drink_filter.*
import kotlinx.android.synthetic.main.fragment_history_pager.toolbar


class DrinkFilterFragment : BaseFragment<HistoryViewModel>() {

    override val contentLayoutResId = R.layout.fragment_drink_filter
    override val viewModel: HistoryViewModel by activityViewModels()

    private lateinit var currentFilterList: HashMap<DrinkFilterType, DrinkFilter>

    companion object {
        fun newInstance() = DrinkFilterFragment()
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.drink_filter_label)

        btn_filter_alcohol_text_1.text = DrinkFilterType.ALCOHOL.key
        btn_filter_category_text_1.text = DrinkFilterType.CATEGORY.key

        btn_toolbar_back.setOnClickListener { requireActivity().onBackPressed() }

        btn_filter_alcohol.setOnClickListener { showDrinkFilterDialog(DrinkFilterType.ALCOHOL) }

        btn_filter_category.setOnClickListener { showDrinkFilterDialog(DrinkFilterType.CATEGORY) }

        btn_result.setOnClickListener { requireActivity().onBackPressed() }

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
        viewModel.drinkFilterLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                currentFilterList = it
                btn_filter_alcohol_text_2.text = it[DrinkFilterType.ALCOHOL]?.key?.replace("\\", "") ?: ""
                btn_filter_category_text_2.text = it[DrinkFilterType.CATEGORY]?.key?.replace("\\", "") ?: ""
            }
        })
    }

    private fun showDrinkFilterDialog(drinkFilterType: DrinkFilterType) {
        val dialogFragment = DrinkFilterDialogFragment.newInstance(drinkFilterType, currentFilterList)
        dialogFragment.show(childFragmentManager, DrinkFilterDialogFragment::class.java.simpleName)
    }

}