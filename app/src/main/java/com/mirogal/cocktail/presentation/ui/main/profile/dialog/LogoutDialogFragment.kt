package com.mirogal.cocktail.presentation.ui.main.profile.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_fragment_logout.*

class LogoutDialogFragment : BaseBottomSheetDialogFragment() {

    override val contentLayoutResId = R.layout.dialog_fragment_logout
    private var listener: OnActionListener? = null

    companion object {
        fun newInstance() = LogoutDialogFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnActionListener
        if (listener == null) {
            throw ClassCastException("$context must implement Listener")
        }
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        btn_dialog_cancel.setOnClickListener { dismiss() }
        btn_dialog_ok.setOnClickListener {
            listener?.onDialogLogoutBtnLogoutClick()
//            dismiss()
        }
    }

    interface OnActionListener {
        fun onDialogLogoutBtnLogoutClick()
    }

}