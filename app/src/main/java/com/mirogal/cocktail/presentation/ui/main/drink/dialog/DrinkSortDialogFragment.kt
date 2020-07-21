package com.mirogal.cocktail.presentation.ui.main.drink.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.filter.DrinkSort
import com.mirogal.cocktail.presentation.ui.base.BaseDialogFragment
import com.mirogal.cocktail.presentation.ui.main.drink.DrinkViewModel
import com.mirogal.cocktail.presentation.ui.main.drink.dialog.adapter.DrinkSortListAdapter
import com.mirogal.cocktail.presentation.ui.util.DividerItemDecorationWithoutUnderLine

class DrinkSortDialogFragment : BaseDialogFragment(),
        DrinkSortListAdapter.OnItemClickListener {

    override val contentLayoutResId = R.layout.dialog_fragment_drink_filter_sort
    private val viewModel: DrinkViewModel by activityViewModels()

    private lateinit var listAdapter: DrinkSortListAdapter
    private lateinit var rvFilter: RecyclerView

    private lateinit var currentSort: DrinkSort

    companion object {
        private const val KEY_CURRENT_SORT = "KEY_CURRENT_SORT"

        fun newInstance(
                currentSort: DrinkSort
        ): DrinkSortDialogFragment {
            val fragment = DrinkSortDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_CURRENT_SORT, currentSort)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentSort = arguments?.getSerializable(KEY_CURRENT_SORT) as DrinkSort
    }

    @SuppressLint("SetTextI18n")
    override fun configureView(view: View, savedInstanceState: Bundle?): View {
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        tvTitle.text = getString(R.string.dialog_drink_sort_title)

        rvFilter = view.findViewById(R.id.rv_filter_list)
        rvFilter.layoutManager = LinearLayoutManager(requireActivity())
        val dividerItemDecoration = DividerItemDecorationWithoutUnderLine(requireActivity(), LinearLayoutManager.VERTICAL)
        rvFilter.addItemDecoration(dividerItemDecoration)

        listAdapter = DrinkSortListAdapter(currentSort, this)
        rvFilter.adapter = listAdapter

        return view
    }

    override fun configureDialog(builder: AlertDialog.Builder, savedInstanceState: Bundle?): AlertDialog.Builder {
        builder.setNegativeButton(R.string.dialog_btn_cancel, null)
        return builder
    }

    override fun onItemClick(currentSort: DrinkSort) {
        viewModel.drinkSortLiveData.value = currentSort
//        listAdapter.refreshData(drinkSort)
        dismiss()
    }

}