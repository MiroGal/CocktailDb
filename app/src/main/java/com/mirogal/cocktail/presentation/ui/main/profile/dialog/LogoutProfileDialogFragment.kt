package com.mirogal.cocktail.presentation.ui.main.profile.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.auth.AuthViewModel
import com.mirogal.cocktail.presentation.ui.base.BaseDialogFragment

class LogoutProfileDialogFragment : BaseDialogFragment<AuthViewModel>() {

    override val contentLayoutResId = R.layout.dialog_fragment_auth_data_invalid
    override val viewModel: AuthViewModel by activityViewModels()

    companion object {
        fun newInstance() = LogoutProfileDialogFragment()
    }

    @SuppressLint("SetTextI18n")
    override fun configureView(view: View, savedInstanceState: Bundle?): View {
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvMessage = view.findViewById<TextView>(R.id.tv_message)
        tvTitle.text = getString(R.string.dialog_profile_logout_title)
        tvMessage.text = getString(R.string.dialog_profile_logout_message)
        return view
    }

    override fun configureDialog(builder: AlertDialog.Builder, savedInstanceState: Bundle?): AlertDialog.Builder {
        builder.setPositiveButton(R.string.dialog_profile_btn_logout, null)
        builder.setNegativeButton(R.string.dialog_btn_cancel, null)
        return builder
    }

    interface onDialogButtonClickListener {
        fun onPositiveButtonClickListener()
    }

}