package com.mirogal.cocktail.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.savelist.SaveListActivity
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : BaseActivity() {

    private val login = "MiroGal"
    private val password = "110589"

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme_NoActionBar)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val textWatcher: TextWatcher = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                invalidateAuthData()
            }

        }

        txt_login.addTextChangedListener(textWatcher)
        txt_password.addTextChangedListener(textWatcher)

        btn_authorization.setOnClickListener {
            val intent = Intent(this@AuthActivity, SaveListActivity::class.java)
            startActivity(intent)
        }

        fillInputField()
    }

    override fun onResume() {
        super.onResume()
        invalidateAuthData()
    }


    private fun invalidateAuthData() {
        val login = txt_login.text.toString()
        val password = txt_password.text.toString()
        btn_authorization.isEnabled = login == this.login && password == this.password
    }

    private fun fillInputField() {
        txt_login.setText(this.login)
        txt_password.setText(this.password)
    }

}