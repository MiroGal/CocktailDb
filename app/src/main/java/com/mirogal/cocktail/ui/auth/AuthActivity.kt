package com.mirogal.cocktail.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : BaseActivity() {

    private val login = "MiroGal"
    private val password = "Miro89"

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme_NoActionBar)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        txt_login.filters = arrayOf(inputFilter)
        txt_password.filters = arrayOf(inputFilter)

        txt_login.addTextChangedListener(textWatcherLogin)
        txt_password.addTextChangedListener(textWatcherPassword)

        btn_authorization.setOnClickListener {
            checkAuthData()
        }

        fillInputField()
    }

    override fun onResume() {
        super.onResume()
        invalidateAuthData()
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
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (txt_login_layout.isErrorEnabled) {
                txt_login_layout.isErrorEnabled = false
//                txt_login.setTextColor(resources.getColor(R.color.txt_body))
            }
            invalidateAuthData()
        }
    }

    private val textWatcherPassword: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (txt_password_layout.isErrorEnabled) {
                txt_password_layout.isErrorEnabled = false
//                txt_password.setTextColor(resources.getColor(R.color.txt_body))
            }
            invalidateAuthData()
        }
    }

    private fun checkAuthData() {
        val login = txt_login.text.toString()
        val password = txt_password.text.toString()
        if (login == this.login && password == this.password) {
            startActivity(Intent(this@AuthActivity, MainActivity::class.java))
        } else if (login != this.login && password == this.password) {
            txt_login.requestFocus()
            txt_login.setSelection(txt_login.text?.length!!)
//            txt_login.setTextColor(resources.getColor(R.color.txt_error))
            txt_login_layout.error = "Incorrect login"
        } else if (login == this.login && password != this.password) {
            txt_password.requestFocus()
            txt_password.setSelection(txt_password.text?.length!!)
//            txt_password.setTextColor(resources.getColor(R.color.txt_error))
            txt_password_layout.error = "Incorrect password"
        } else {
            txt_login.requestFocus()
            txt_login.setSelection(txt_login.text?.length!!)
//            txt_login.setTextColor(resources.getColor(R.color.txt_error))
//            txt_password.setTextColor(resources.getColor(R.color.txt_error))
            txt_login_layout.error = "Incorrect login"
            txt_password_layout.error = "Incorrect password"
        }
//            ll_root.requestFocus()
//            val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            inputManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }


    private fun invalidateAuthData() {
        val login = txt_login.text.toString()
        val password = txt_password.text.toString()
        btn_authorization.isClickable = login.length >= 6 && password.length >= 6
                && password.any { it.isDigit() } && password.any { it.isLetter() }
    }

    private fun fillInputField() {
        txt_login.setText(this.login)
        txt_password.setText(this.password)
    }

}