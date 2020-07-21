package com.mirogal.cocktail.presentation.ui.main.drink.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_fragment_logout.*

class DayDrinkDialogFragment : BaseBottomSheetDialogFragment() {

    override val contentLayoutResId = R.layout.dialog_fragment_day_drink
    private var listener: OnActionListener? = null

    private var cocktailId: Int = 0
    private var cocktailName: String? = ""

    companion object {
        private const val KEY_COCKTAIL_ID = "KEY_COCKTAIL_ID"
        private const val KEY_COCKTAIL_NAME = "KEY_COCKTAIL_NAME"

        fun newInstance(cocktailId: Int, cocktailName: String?): DayDrinkDialogFragment {
            val fragment = DayDrinkDialogFragment()
            val bundle = Bundle()
            bundle.putInt(KEY_COCKTAIL_ID, cocktailId)
            bundle.putString(KEY_COCKTAIL_NAME, cocktailName)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cocktailId = arguments?.getInt(KEY_COCKTAIL_ID)!!
        cocktailName = arguments?.getString(KEY_COCKTAIL_NAME)
        listener = context as? OnActionListener
        if (listener == null) {
            throw ClassCastException("$context must implement Listener")
        }
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        btn_dialog_cancel.setOnClickListener { dismiss() }
        btn_dialog_ok.setOnClickListener {
            listener?.onDialogDayDrinkBtnOkClick(cocktailId, cocktailName)
            dismiss()
        }
    }

    interface OnActionListener {
        fun onDialogDayDrinkBtnOkClick(cocktailId: Int, cocktailName: String?)
    }

}