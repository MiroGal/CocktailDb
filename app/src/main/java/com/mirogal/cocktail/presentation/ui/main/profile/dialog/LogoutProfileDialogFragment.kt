package com.mirogal.cocktail.presentation.ui.main.profile.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.base.BaseBottomSheetDialogFragment
import com.mirogal.cocktail.presentation.ui.main.profile.ProfileViewModel
import kotlinx.android.synthetic.main.dialog_fragment_profile_logout.*

class LogoutProfileDialogFragment : BaseBottomSheetDialogFragment<ProfileViewModel>() {

    override val contentLayoutResId = R.layout.dialog_fragment_profile_logout
    override val viewModel: ProfileViewModel by activityViewModels()

    private var listener: OnDialogLogoutActionListener? = null

    companion object {
        fun newInstance() = LogoutProfileDialogFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnDialogLogoutActionListener
        if (listener == null) {
            throw ClassCastException("$context must implement Listener")
        }
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        btn_dialog_cancel.setOnClickListener { dismiss() }
        btn_dialog_ok.setOnClickListener { listener?.onButtonLogoutClick() }
    }

    interface OnDialogLogoutActionListener {
        fun onButtonLogoutClick()
    }

}