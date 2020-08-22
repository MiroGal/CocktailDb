package com.mirogal.cocktail.presentation.ui.auth

import android.app.Application
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mirogal.cocktail.presentation.model.auth.AuthDataValidStatus
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel

class AuthViewModel(application: Application) : BaseViewModel(application) {

    private val validLogin = "MiroGal"
    private val validPassword = "Miro89"

    private val minLoginLength = 6
    private val minPasswordLength = 6

    val inputLoginLiveData: MutableLiveData<String?> = MutableLiveData()
    val inputPasswordLiveData: MutableLiveData<String?> = MutableLiveData()
    val isAuthDataCorrectLiveData: LiveData<Boolean?>
    val isAuthDataValidLiveData: LiveData<AuthDataValidStatus?>

    private val observer: Observer<in AuthDataValidStatus?> = Observer { }

    init {
        isAuthDataCorrectLiveData = MediatorLiveData<Boolean>().apply {
            addSource(inputLoginLiveData) {
                if (inputLoginLiveData.value != null && inputPasswordLiveData.value != null)
                    value = isAuthDataCorrect()
            }
            addSource(inputPasswordLiveData) {
                if (inputLoginLiveData.value != null && inputPasswordLiveData.value != null)
                    value = isAuthDataCorrect()
            }
        }

        isAuthDataValidLiveData = MediatorLiveData<AuthDataValidStatus>().apply {
            addSource(inputLoginLiveData) {
                if (inputLoginLiveData.value != null && inputPasswordLiveData.value != null)
                    value = isAuthDataValid()
            }
            addSource(inputPasswordLiveData) {
                if (inputLoginLiveData.value != null && inputPasswordLiveData.value != null)
                    value = isAuthDataValid()
            }
        }

        isAuthDataValidLiveData.observeForever(observer)

        fillInputField()
    }

    override fun onCleared() {
        isAuthDataValidLiveData.removeObserver(observer)
        super.onCleared()
    }

    private fun isAuthDataCorrect(): Boolean {
        val login = inputLoginLiveData.value
        val password = inputPasswordLiveData.value
        return login!!.length >= minLoginLength && password!!.length >= minPasswordLength
                && password.any { it.isDigit() } && password.any { it.isLetter() }
    }

    private fun isAuthDataValid(): AuthDataValidStatus {
        val login = inputLoginLiveData.value
        val password = inputPasswordLiveData.value
        return when {
            login == validLogin && password == validPassword -> AuthDataValidStatus.LOGIN_VALID_PASSWORD_VALID
            login == validLogin && password != validPassword -> AuthDataValidStatus.LOGIN_VALID_PASSWORD_INVALID
            login != validLogin && password == validPassword -> AuthDataValidStatus.LOGIN_INVALID_PASSWORD_VALID
            else -> AuthDataValidStatus.LOGIN_INVALID_PASSWORD_INVALID
        }
    }

    // Temporary method
    private fun fillInputField() {
        inputLoginLiveData.value = validLogin
        inputPasswordLiveData.value = validPassword
    }

    val loginTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            inputLoginLiveData.value = s.toString()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    val passwordTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            inputPasswordLiveData.value = s.toString()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    val inputFilter = InputFilter { source, start, end, dest, dstart, dend ->
        for (i in start until end) {
            if (Character.isWhitespace(source[i])) {
                return@InputFilter ""
            }
        }
        return@InputFilter null
    }

}