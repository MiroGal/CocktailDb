package com.mirogal.cocktail.presentation.ui.main.history.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.presentation.model.filter.DrinkFilterType
import com.mirogal.cocktail.presentation.ui.main.history.HistoryViewModel
import com.mirogal.cocktail.presentation.ui.main.history.dialog.adapter.ListAdapter


class DrinkFilterDialogFragment : DialogFragment(), ListAdapter.OnItemClickListener {

    val viewModel: HistoryViewModel by activityViewModels()

    private var drinkFilterType: DrinkFilterType? = null
    private var currentFilter: AlcoholDrinkFilter? = null

    companion object {
        private const val KEY_DRINK_FILTER_TYPE = "KEY_DRINK_FILTER_TYPE"
        private const val KEY_CURRENT_FILTER = "KEY_CURRENT_FILTER"

        fun newInstance(drinkFilterType: DrinkFilterType, currentFilter: AlcoholDrinkFilter): DrinkFilterDialogFragment {
            val fragment = DrinkFilterDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_DRINK_FILTER_TYPE, drinkFilterType)
            bundle.putSerializable(KEY_CURRENT_FILTER, currentFilter)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        drinkFilterType = arguments?.getSerializable(KEY_DRINK_FILTER_TYPE) as DrinkFilterType?
        currentFilter = arguments?.getSerializable(KEY_CURRENT_FILTER) as AlcoholDrinkFilter?
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())

        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.layout_dialog_drink_filter, null)

        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        tvTitle.text = drinkFilterType?.key ?: "Drink filter"

        val rvFilter = view.findViewById<RecyclerView>(R.id.rv_filter_list)
        rvFilter.layoutManager = LinearLayoutManager(requireActivity())
        val dividerItemDecoration = DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL)
        rvFilter.addItemDecoration(dividerItemDecoration)
        val listAdapter = ListAdapter(AlcoholDrinkFilter.values(), this, currentFilter)
        rvFilter.adapter = listAdapter

        builder.setView(view)
                .setNegativeButton(R.string.dialog_btn_cancel, null)
        return builder.create()
    }

    override fun onItemClick(filter: AlcoholDrinkFilter) {
        val drinkFilter = viewModel.drinkFilterLiveData.value
        drinkFilter?.put(DrinkFilterType.ALCOHOL, filter)
        viewModel.drinkFilterLiveData.value = drinkFilter
    }

}