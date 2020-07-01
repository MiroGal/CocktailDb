package com.mirogal.cocktail.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : BaseActivity<AuthViewModel>() {

    override val contentLayoutResId = R.layout.activity_auth
    override val viewModel: AuthViewModel by viewModels()

    private var isAuthDataValid: AuthDataValidStatus = AuthDataValidStatus.LOGIN_INVALID_PASSWORD_INVALID

    override fun setApplicationTheme() {
        setTheme(R.style.AppTheme_NoActionBar)
    }

    override fun configureView(savedInstanceState: Bundle?) {
        txt_login.filters = arrayOf(inputFilter)
        txt_password.filters = arrayOf(inputFilter)

        txt_login.addTextChangedListener(textWatcherLogin)
        txt_password.addTextChangedListener(textWatcherPassword)

        btn_authorization.isClickable = false
        btn_authorization.setBackgroundResource(R.drawable.bg_button_inactive)
        btn_authorization.setOnClickListener {
            when (isAuthDataValid) {
                AuthDataValidStatus.LOGIN_VALID_PASSWORD_VALID -> {
                    startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                }
                AuthDataValidStatus.LOGIN_VALID_PASSWORD_INVALID -> {
                    txt_password.requestFocus()
                    txt_password.setSelection(txt_password.text?.length!!)
                    txt_password_layout.error = getString(R.string.auth_message_invalid_password)
                }
                AuthDataValidStatus.LOGIN_INVALID_PASSWORD_VALID -> {
                    txt_login.requestFocus()
                    txt_login.setSelection(txt_login.text?.length!!)
                    txt_login_layout.error = getString(R.string.auth_message_invalid_login)
                }
                else -> {
                    txt_login.requestFocus()
                    txt_login.setSelection(txt_login.text?.length!!)
                    txt_login_layout.error = getString(R.string.auth_message_invalid_login)
                    txt_password_layout.error = getString(R.string.auth_message_invalid_password)
                }
            }
        }

        setObservers()
        fillInputField()
    }

    private fun setObservers() {
        viewModel.isAuthDataCorrectLiveData.observe(this, Observer {
            if (btn_authorization.isClickable != it) {
                btn_authorization.isClickable = it
                if (it) {
                    btn_authorization.setBackgroundResource(R.drawable.bg_button)
                } else {
                    btn_authorization.setBackgroundResource(R.drawable.bg_button_inactive)
                }
            }
        })
        viewModel.isAuthDataValidLiveData.observe(this, Observer {
            isAuthDataValid = it
        })
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

    // Temporary method
    private fun fillInputField() {
        btn_authorization.isClickable = true
        btn_authorization.setBackgroundResource(R.drawable.bg_button)
        txt_login.setText("MiroGal")
        txt_password.setText("Miro89")
    }

}