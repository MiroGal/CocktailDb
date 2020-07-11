package com.mirogal.cocktail.presentation.ui.main.drink.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.filter.DrinkFilter
import com.mirogal.cocktail.presentation.model.filter.DrinkFilterType
import com.mirogal.cocktail.presentation.ui.base.BaseDialogFragment
import com.mirogal.cocktail.presentation.ui.main.drink.DrinkViewModel
import com.mirogal.cocktail.presentation.ui.main.drink.dialog.adapter.DrinkSortListAdapter

class DrinkSortDialogFragment : BaseDialogFragment<DrinkViewModel>(), DrinkSortListAdapter.OnItemClickListener {

    override val contentLayoutResId = R.layout.dialog_fragment_drink_filter
    override val viewModel: DrinkViewModel by activityViewModels()

    private lateinit var drinkFilterListAdapter: DrinkSortListAdapter
    private lateinit var rvFilter: RecyclerView

    private lateinit var drinkFilterType: DrinkFilterType
    private lateinit var currentFilterList: HashMap<DrinkFilterType, DrinkFilter>

    companion object {
        private const val KEY_DRINK_FILTER_TYPE = "KEY_DRINK_FILTER_TYPE"
        private const val KEY_CURRENT_FILTER_LIST = "KEY_CURRENT_FILTER_LIST"

        fun newInstance(drinkFilterType: DrinkFilterType,
                        currentFilterList: HashMap<DrinkFilterType, DrinkFilter>
        ): DrinkSortDialogFragment {
            val fragment = DrinkSortDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_DRINK_FILTER_TYPE, drinkFilterType)
            bundle.putSerializable(KEY_CURRENT_FILTER_LIST, currentFilterList)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        drinkFilterType = arguments?.getSerializable(KEY_DRINK_FILTER_TYPE) as DrinkFilterType
        currentFilterList = arguments?.getSerializable(KEY_CURRENT_FILTER_LIST) as HashMap<DrinkFilterType, DrinkFilter>
    }

    @SuppressLint("SetTextI18n")
    override fun configureView(view: View, savedInstanceState: Bundle?): View {
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        tvTitle.text = drinkFilterType.key + " " + getString(R.string.dialog_filter_title)

        rvFilter = view.findViewById(R.id.rv_filter_list)
        rvFilter.layoutManager = LinearLayoutManager(requireActivity())
        val dividerItemDecoration = DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL)
        rvFilter.addItemDecoration(dividerItemDecoration)

        drinkFilterListAdapter = DrinkSortListAdapter(drinkFilterType, currentFilterList, this)
        rvFilter.adapter = drinkFilterListAdapter

        return view
    }

    override fun configureDialog(builder: AlertDialog.Builder, savedInstanceState: Bundle?): AlertDialog.Builder {
        builder.setNegativeButton(R.string.dialog_btn_cancel, null)
        return builder
    }

    override fun onItemClick(filterList: HashMap<DrinkFilterType, DrinkFilter>) {
        viewModel.drinkFilterLiveData.value = filterList
//        listAdapter.refreshData(filterList)
        dismiss()
    }

}