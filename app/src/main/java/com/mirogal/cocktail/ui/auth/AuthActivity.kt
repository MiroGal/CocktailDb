package com.mirogal.cocktail.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.savelist.SaveListActivity
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

        btn_authorization.setOnClickListener(onClickListener)

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

    private val textWatcherLogin: TextWatcher = object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            invalidateAuthData()
            txt_login.setTextColor(resources.getColor(R.color.txt_body))
        }
    }

    private val textWatcherPassword: TextWatcher = object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            invalidateAuthData()
            txt_password.setTextColor(resources.getColor(R.color.txt_body))
        }
    }

    private val onClickListener = View.OnClickListener {
        val login = txt_login.text.toString()
        val password = txt_password.text.toString()
        if (login == this.login && password == this.password) {
            startActivity(Intent(this@AuthActivity, SaveListActivity::class.java))
        } else if (login != this.login && password == this.password) {
            txt_login.requestFocus()
            txt_login.setSelection(txt_login.text?.length!!)
            txt_login.setTextColor(resources.getColor(R.color.txt_error))
        } else if (login == this.login && password != this.password) {
            txt_password.requestFocus()
            txt_password.setSelection(txt_password.text?.length !!)
            txt_password.setTextColor(resources.getColor(R.color.txt_error))
        } else {
            txt_login.requestFocus()
            txt_login.setSelection(txt_login.text?.length!!)
            txt_login.setTextColor(resources.getColor(R.color.txt_error))
            txt_password.setTextColor(resources.getColor(R.color.txt_error))
        }
//            ll_root.requestFocus()
//            val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            inputManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }


    private fun invalidateAuthData() {
        val login = txt_login.text.toString()
        val password = txt_password.text.toString()
        var isDigit = false
        var isLetter = false
        for (c in password.toCharArray()) {
            if (Character.isDigit(c)) {
                isDigit = true
            }
        }
        for (c in password.toCharArray()) {
            if (Character.isLetter(c)) {
                isLetter = true
            }
        }
        btn_authorization.isClickable = login.length >= 6 && password.length >= 6 && isDigit && isLetter
    }

    private fun fillInputField() {
        txt_login.setText(this.login)
        txt_password.setText(this.password)
    }

}