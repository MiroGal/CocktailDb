package com.mirogal.cocktail.presentation.ui.auth.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.auth.AuthDataValidStatus
import com.mirogal.cocktail.presentation.ui.auth.AuthViewModel
import com.mirogal.cocktail.presentation.ui.base.BaseDialogFragment

class InvalidAuthDataDialogFragment : BaseDialogFragment<AuthViewModel>() {

    override val contentLayoutResId = R.layout.dialog_fragment_auth_data_invalid
    override val viewModel: AuthViewModel by activityViewModels()

    private lateinit var authDataValidStatus: AuthDataValidStatus

    companion object {
        private const val KEY_AUTH_DATA_VALID_STATUS = "KEY_AUTH_DATA_VALID_STATUS"

        fun newInstance(authDataValidStatus: AuthDataValidStatus): InvalidAuthDataDialogFragment {
            val fragment = InvalidAuthDataDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_AUTH_DATA_VALID_STATUS, authDataValidStatus)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authDataValidStatus = arguments?.getSerializable(KEY_AUTH_DATA_VALID_STATUS) as AuthDataValidStatus
    }

    @SuppressLint("SetTextI18n")
    override fun configureView(view: View, savedInstanceState: Bundle?): View {
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvMessage = view.findViewById<TextView>(R.id.tv_message)
        tvMessage.text = getString(R.string.dialog_auth_message)
        when (authDataValidStatus) {
            AuthDataValidStatus.LOGIN_VALID_PASSWORD_INVALID -> tvTitle.text = getString(R.string.dialog_auth_title_valid_invalid)
            AuthDataValidStatus.LOGIN_INVALID_PASSWORD_VALID -> tvTitle.text = getString(R.string.dialog_auth_title_invalid_valid)
            AuthDataValidStatus.LOGIN_INVALID_PASSWORD_INVALID -> tvTitle.text = getString(R.string.dialog_auth_title_invalid_invalid)
        }
        return view
    }

    override fun configureDialog(builder: AlertDialog.Builder, savedInstanceState: Bundle?): AlertDialog.Builder {
        builder.setNegativeButton(R.string.dialog_btn_ok, null)
        return builder
    }

}