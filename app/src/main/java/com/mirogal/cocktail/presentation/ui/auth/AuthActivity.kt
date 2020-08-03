package com.mirogal.cocktail.presentation.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.modelnative.auth.AuthDataValidStatus
import com.mirogal.cocktail.presentation.ui.auth.dialog.InvalidAuthDataDialogFragment
import com.mirogal.cocktail.presentation.ui.base.BaseActivity
import com.mirogal.cocktail.presentation.ui.test.TestActivity
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : BaseActivity<AuthViewModel>() {

    override val contentLayoutResId = R.layout.activity_auth

    override fun getViewModelClass() = AuthViewModel::class

    private val validLogin = "MiroGal"
    private val validPassword = "Miro89"

    override fun setApplicationTheme() {
        setTheme(R.style.AppTheme_NoActionBar)
    }

    override fun configureView(savedInstanceState: Bundle?) {
        txt_login.filters = arrayOf(inputFilter)
        txt_password.filters = arrayOf(inputFilter)

        txt_login.addTextChangedListener(textWatcherLogin)
        txt_password.addTextChangedListener(textWatcherPassword)

        btn_authorization.isClickable = false
        btn_authorization.setBackgroundResource(R.drawable.bg_accent_button_inactive)
        btn_authorization.setOnClickListener {
            when (viewModel.isAuthDataValidLiveData.value ?: AuthDataValidStatus.LOGIN_INVALID_PASSWORD_INVALID) {
                AuthDataValidStatus.LOGIN_VALID_PASSWORD_VALID -> {
//                    startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                    startActivity(Intent(this@AuthActivity, TestActivity::class.java))
                }
                AuthDataValidStatus.LOGIN_VALID_PASSWORD_INVALID -> {
                    showInvalidAuthDataDialog(AuthDataValidStatus.LOGIN_VALID_PASSWORD_INVALID)
                    txt_password.requestFocus()
                    txt_password.setSelection(txt_password.text?.length!!)
                    txt_password_layout.error = getString(R.string.auth_message_invalid_password)
                }
                AuthDataValidStatus.LOGIN_INVALID_PASSWORD_VALID -> {
                    showInvalidAuthDataDialog(AuthDataValidStatus.LOGIN_INVALID_PASSWORD_VALID)
                    txt_login.requestFocus()
                    txt_login.setSelection(txt_login.text?.length!!)
                    txt_login_layout.error = getString(R.string.auth_message_invalid_login)
                }
                else -> {
                    showInvalidAuthDataDialog(AuthDataValidStatus.LOGIN_INVALID_PASSWORD_INVALID)
                    txt_login.requestFocus()
                    txt_login.setSelection(txt_login.text?.length!!)
                    txt_login_layout.error = getString(R.string.auth_message_invalid_login)
                    txt_password_layout.error = getString(R.string.auth_message_invalid_password)
                }
            }
        }

        root_view.setOnFocusChangeListener { v, hasFocus -> hideKeyboard() }
    }

    override fun configureObserver() {
        viewModel.isAuthDataCorrectLiveData.observe(this, Observer {
            if (btn_authorization.isClickable != it) {
                btn_authorization.isClickable = it ?: false
                if (it == true) {
                    btn_authorization.setBackgroundResource(R.drawable.bg_accent_button)
                } else {
                    btn_authorization.setBackgroundResource(R.drawable.bg_accent_button_inactive)
                }
            }
        })

        fillInputField()
    }

    private val inputFilter = InputFilter { source, start, end, dest, dstart, dend ->
        for (i in start until end) {
            if (Character.isWhitespace(source[i])) {
                return@InputFilter ""
            }
        }
        return@InputFilter null
    }

    private val textWatcherLogin: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (txt_login_layout.isErrorEnabled) {
                txt_login_layout.isErrorEnabled = false
            }
            viewModel.inputLoginLiveData.value = s.toString()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private val textWatcherPassword: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (txt_password_layout.isErrorEnabled) {
                txt_password_layout.isErrorEnabled = false
            }
            viewModel.inputPasswordLiveData.value = s.toString()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }

    private fun showInvalidAuthDataDialog(authDataValidStatus: AuthDataValidStatus) {
        val dialogFragment = InvalidAuthDataDialogFragment.newInstance(authDataValidStatus)
        dialogFragment.show(supportFragmentManager, InvalidAuthDataDialogFragment::class.java.simpleName)
    }

    // Temporary method
    private fun fillInputField() {
        btn_authorization.isClickable = true
        btn_authorization.setBackgroundResource(R.drawable.bg_accent_button)
        txt_login.setText(validLogin)
        txt_password.setText(validPassword)
    }

}