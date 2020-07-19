package com.mirogal.cocktail.presentation.ui.main.drink

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.filter.*
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.main.drink.dialog.DrinkFilterDialogFragment
import kotlinx.android.synthetic.main.fragment_drink_filter.*
import kotlinx.android.synthetic.main.fragment_drink_filter_content.*
import kotlinx.android.synthetic.main.fragment_drink_pager.toolbar

class DrinkFilterFragment : BaseFragment<DrinkViewModel>() {

    override val contentLayoutResId = R.layout.fragment_drink_filter
    override val viewModel: DrinkViewModel by activityViewModels()

    private val currentFilterList: HashMap<DrinkFilterType, DrinkFilter> = hashMapOf(
            Pair(DrinkFilterType.CATEGORY, DrinkFilterCategory.DISABLE),
            Pair(DrinkFilterType.ALCOHOL, DrinkFilterAlcohol.DISABLE),
            Pair(DrinkFilterType.INGREDIENT, DrinkFilterIngredient.DISABLE),
            Pair(DrinkFilterType.GLASS, DrinkFilterGlass.DISABLE))
    private val saveFilterList: HashMap<DrinkFilterType, DrinkFilter> = hashMapOf(
            Pair(DrinkFilterType.CATEGORY, DrinkFilterCategory.DISABLE),
            Pair(DrinkFilterType.ALCOHOL, DrinkFilterAlcohol.DISABLE),
            Pair(DrinkFilterType.INGREDIENT, DrinkFilterIngredient.DISABLE),
            Pair(DrinkFilterType.GLASS, DrinkFilterGlass.DISABLE))

    private var isFirstTimeOpen = true

    companion object {
        fun newInstance() = DrinkFilterFragment()
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.drink_filter_label)

        btn_filter_category_text_1.text = DrinkFilterType.CATEGORY.key
        btn_filter_alcohol_text_1.text = DrinkFilterType.ALCOHOL.key
        btn_filter_ingredient_text_1.text = DrinkFilterType.INGREDIENT.key
        btn_filter_glass_text_1.text = DrinkFilterType.GLASS.key

        btn_toolbar_back.setOnClickListener { requireActivity().onBackPressed() }

        btn_filter_category.setOnClickListener {
            copyFilterList(saveFilterList, currentFilterList)
            showDrinkFilterDialog(DrinkFilterType.CATEGORY)
        }

        btn_filter_alcohol.setOnClickListener {
            copyFilterList(saveFilterList, currentFilterList)
            showDrinkFilterDialog(DrinkFilterType.ALCOHOL)
        }

        btn_filter_ingredient.setOnClickListener {
            copyFilterList(saveFilterList, currentFilterList)
            showDrinkFilterDialog(DrinkFilterType.INGREDIENT)
        }

        btn_filter_glass.setOnClickListener {
            copyFilterList(saveFilterList, currentFilterList)
            showDrinkFilterDialog(DrinkFilterType.GLASS)
        }

        btn_result.setOnClickListener { requireActivity().onBackPressed() }

        btn_reset.setOnClickListener {
            copyFilterList(saveFilterList, currentFilterList)
            viewModel.resetDrinkFilter()
        }
    }

    override fun configureObserver(view: View, savedInstanceState: Bundle?) {
        viewModel.drinkFilterLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                copyFilterList(currentFilterList, it)
                btn_filter_category_text_2.text = it[DrinkFilterType.CATEGORY]?.key?.replace("\\", "") ?: ""
                btn_filter_alcohol_text_2.text = it[DrinkFilterType.ALCOHOL]?.key?.replace("\\", "") ?: ""
                btn_filter_ingredient_text_2.text = it[DrinkFilterType.INGREDIENT]?.key?.replace("\\", "") ?: ""
                btn_filter_glass_text_2.text = it[DrinkFilterType.GLASS]?.key?.replace("\\", "") ?: ""
            }
        })
        viewModel.cocktailListSizeLiveData.observe(viewLifecycleOwner, Observer {
            showFilterSnackbar(it?.first ?: 0, it?.second ?: 0)
        })
    }

    private fun showDrinkFilterDialog(drinkFilterType: DrinkFilterType) {
        val dialogFragment = DrinkFilterDialogFragment.newInstance(drinkFilterType, currentFilterList)
        dialogFragment.show(childFragmentManager, DrinkFilterDialogFragment::class.java.simpleName)
    }

    private fun showFilterSnackbar(historyListSize: Int, favoriteListSize: Int) {
        if (!isFirstTimeOpen) {
            Snackbar.make(requireView().findViewById(R.id.container),
                    if (historyListSize != 0) {
                        getString(R.string.drink_filter_message_result_found) + ": " + historyListSize + "\uD83D\uDD51" + " / " + favoriteListSize + "\u2665"
                    } else {
                        getString(R.string.drink_filter_message_result_not_found)
                    }, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.background_primary))
                    .setTextColor(resources.getColor(R.color.txt_title))
                    .setAction(getString(R.string.drink_filter_btn_undo)) {
                        viewModel.drinkFilterLiveData.value = saveFilterList
                    }
                    .show()
        }
        isFirstTimeOpen = false
    }

    private fun copyFilterList(copyList: HashMap<DrinkFilterType, DrinkFilter>, originalList: HashMap<DrinkFilterType, DrinkFilter>) {
        copyList[DrinkFilterType.CATEGORY] = originalList[DrinkFilterType.CATEGORY] ?: DrinkFilterCategory.DISABLE
        copyList[DrinkFilterType.ALCOHOL] = originalList[DrinkFilterType.ALCOHOL] ?: DrinkFilterAlcohol.DISABLE
        copyList[DrinkFilterType.INGREDIENT] = originalList[DrinkFilterType.INGREDIENT] ?: DrinkFilterIngredient.DISABLE
        copyList[DrinkFilterType.GLASS] = originalList[DrinkFilterType.GLASS] ?: DrinkFilterGlass.DISABLE
    }

}