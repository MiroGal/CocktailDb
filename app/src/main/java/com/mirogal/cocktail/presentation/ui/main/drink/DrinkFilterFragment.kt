package com.mirogal.cocktail.presentation.ui.main.drink

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.extension.sharedViewModels
import com.mirogal.cocktail.presentation.constant.filter.DrinkFilterType
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.main.drink.dialog.DrinkFilterDialogFragment
import kotlinx.android.synthetic.main.fragment_drink_filter.*
import kotlinx.android.synthetic.main.fragment_drink_filter_content.*
import kotlinx.android.synthetic.main.fragment_drink_pager.toolbar

class DrinkFilterFragment : BaseFragment<DrinkViewModel>() {

    override val contentLayoutResId = R.layout.fragment_drink_filter
    override val viewModel: DrinkViewModel by sharedViewModels()

    private var isFragmentNotJustCreated = false

    companion object {
        fun newInstance() = DrinkFilterFragment()
    }

    override fun configureView(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.drink_filter_label)

        btn_filter_category_text_1.text = DrinkFilterType.CATEGORY.key
        btn_filter_alcohol_text_1.text = DrinkFilterType.ALCOHOL.key
        btn_filter_ingredient_text_1.text = DrinkFilterType.INGREDIENT.key
        btn_filter_glass_text_1.text = DrinkFilterType.GLASS.key

        btn_toolbar_back.setOnClickListener(onClickListener)

        btn_filter_category.setOnClickListener(onClickListener)
        btn_filter_alcohol.setOnClickListener(onClickListener)
        btn_filter_ingredient.setOnClickListener(onClickListener)
        btn_filter_glass.setOnClickListener(onClickListener)

        btn_result.setOnClickListener(onClickListener)
        btn_reset.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener {
        when (it) {
            btn_toolbar_back -> requireActivity().onBackPressed()
            btn_filter_category -> showDrinkFilterDialog(DrinkFilterType.CATEGORY)
            btn_filter_alcohol -> showDrinkFilterDialog(DrinkFilterType.ALCOHOL)
            btn_filter_ingredient -> showDrinkFilterDialog(DrinkFilterType.INGREDIENT)
            btn_filter_glass -> showDrinkFilterDialog(DrinkFilterType.GLASS)
            btn_result -> requireActivity().onBackPressed()
            btn_reset -> viewModel.resetDrinkFilter()
        }
    }

    override fun configureObserver() {
        viewModel.drinkFilterLiveData.observe(viewLifecycleOwner, Observer {
            btn_filter_category_text_2.text = it[DrinkFilterType.CATEGORY]?.key?.replace("\\", "") ?: ""
            btn_filter_alcohol_text_2.text = it[DrinkFilterType.ALCOHOL]?.key?.replace("\\", "") ?: ""
            btn_filter_ingredient_text_2.text = it[DrinkFilterType.INGREDIENT]?.key?.replace("\\", "") ?: ""
            btn_filter_glass_text_2.text = it[DrinkFilterType.GLASS]?.key?.replace("\\", "") ?: ""
        })
        viewModel.cocktailListSizeLiveData.observe(viewLifecycleOwner, Observer {
            showFilterSnackbar(it?.first ?: 0, it?.second ?: 0)
        })
    }

    private fun showDrinkFilterDialog(drinkFilterType: DrinkFilterType) {
        val dialogFragment = DrinkFilterDialogFragment.newInstance(drinkFilterType, viewModel.drinkFilterLiveData.value!!)
        dialogFragment.show(childFragmentManager, DrinkFilterDialogFragment::class.java.name)
    }

    private fun showFilterSnackbar(historyListSize: Int, favoriteListSize: Int) {
        if (isFragmentNotJustCreated) {
            Snackbar.make(requireView().findViewById(R.id.container),
                    if (historyListSize != 0) {
                        getString(R.string.drink_filter_message_result_found) + ": " + historyListSize + "\uD83D\uDD51" + " / " + favoriteListSize + "\u2665"
                    } else {
                        getString(R.string.drink_filter_message_result_not_found)
                    }, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.background_primary))
                    .setTextColor(resources.getColor(R.color.txt_title))
                    .setAction(getString(R.string.drink_filter_btn_undo)) {
                        viewModel.backDrinkFilter()
                    }
                    .show()
        }
        isFragmentNotJustCreated = true
    }

}