package com.mirogal.cocktail.presentation.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.mirogal.cocktail.R
import com.mirogal.cocktail.databinding.ActivityAuthBinding
import com.mirogal.cocktail.presentation.model.auth.AuthDataValidStatus
import com.mirogal.cocktail.presentation.ui.auth.dialog.InvalidAuthDataDialogFragment
import com.mirogal.cocktail.presentation.ui.base.BaseActivity
import com.mirogal.cocktail.presentation.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : BaseActivity<AuthViewModel, ActivityAuthBinding>() {

    override val contentLayoutResId = R.layout.activity_auth
    override val viewModel: AuthViewModel by viewModels()

    override fun setApplicationTheme() {
        setTheme(R.style.AppTheme_NoActionBar)
    }

    override fun configureView(savedInstanceState: Bundle?) {
        root_view.setOnFocusChangeListener { v, hasFocus -> hideKeyboard() }
        btn_authorization.setOnClickListener {
            when (viewModel.isAuthDataValidLiveData.value ?: AuthDataValidStatus.LOGIN_INVALID_PASSWORD_INVALID) {
                AuthDataValidStatus.LOGIN_VALID_PASSWORD_VALID -> {
                    startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                }
                AuthDataValidStatus.LOGIN_VALID_PASSWORD_INVALID -> {
                    showInvalidAuthDataDialog(AuthDataValidStatus.LOGIN_VALID_PASSWORD_INVALID)
                    txt_password_layout.error = getString(R.string.auth_message_invalid_password)
                    txt_password.apply {
                        requestFocus()
                        setSelection(text?.length!!)
                    }
                }
                AuthDataValidStatus.LOGIN_INVALID_PASSWORD_VALID -> {
                    showInvalidAuthDataDialog(AuthDataValidStatus.LOGIN_INVALID_PASSWORD_VALID)
                    txt_login_layout.error = getString(R.string.auth_message_invalid_login)
                    txt_login.apply {
                        requestFocus()
                        setSelection(text?.length!!)
                    }
                }
                else -> {
                    showInvalidAuthDataDialog(AuthDataValidStatus.LOGIN_INVALID_PASSWORD_INVALID)
                    txt_password_layout.error = getString(R.string.auth_message_invalid_password)
                    txt_login_layout.error = getString(R.string.auth_message_invalid_login)
                    txt_login.apply {
                        requestFocus()
                        setSelection(text?.length!!)
                    }
                }
            }
        }
    }

    override fun configureDataBinding(binding: ActivityAuthBinding) {
        super.configureDataBinding(binding)
        dataBinding.viewmodel = viewModel
    }

    override fun configureObserver() {
        viewModel.inputLoginLiveData.observe(this, Observer {
            if (txt_login_layout.isErrorEnabled) {
                txt_login_layout.isErrorEnabled = false
            }
        })
        viewModel.inputPasswordLiveData.observe(this, Observer {
            if (txt_password_layout.isErrorEnabled) {
                txt_password_layout.isErrorEnabled = false
            }
        })
    }

    private fun showInvalidAuthDataDialog(authDataValidStatus: AuthDataValidStatus) {
        val dialogFragment = InvalidAuthDataDialogFragment.newInstance(authDataValidStatus)
        dialogFragment.show(supportFragmentManager, InvalidAuthDataDialogFragment::class.java.simpleName)
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }

}