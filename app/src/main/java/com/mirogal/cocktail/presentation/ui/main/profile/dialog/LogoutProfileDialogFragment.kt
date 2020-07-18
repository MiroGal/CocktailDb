package com.mirogal.cocktail.presentation.ui.main.profile.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.base.BaseDialogFragment
import com.mirogal.cocktail.presentation.ui.main.profile.ProfileViewModel

class LogoutProfileDialogFragment : BaseDialogFragment<ProfileViewModel>() {

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

    @SuppressLint("SetTextI18n")
    override fun configureView(view: View, savedInstanceState: Bundle?): View {
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvMessage = view.findViewById<TextView>(R.id.tv_message)
        tvTitle.text = getString(R.string.dialog_profile_logout_title)
        tvMessage.text = getString(R.string.dialog_profile_logout_message)
        return view
    }

    override fun configureDialog(builder: AlertDialog.Builder, savedInstanceState: Bundle?): AlertDialog.Builder {
        builder.setPositiveButton(R.string.dialog_profile_btn_logout) {
            dialogInterface: DialogInterface, i: Int -> listener?.onButtonLogoutClick()
        }
        builder.setNegativeButton(R.string.dialog_btn_cancel, null)
        return builder
    }

    interface OnDialogLogoutActionListener {
        fun onButtonLogoutClick()
    }

}