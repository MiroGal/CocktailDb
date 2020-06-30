package com.mirogal.cocktail.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.activity_auth.*


class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val login = "MiroGal"
    private val password = "Miro89"

    private val minLoginLength = 6
    private val minPasswordLength = 6

    val inputLoginLiveData: MutableLiveData<String?> = MutableLiveData()
    val inputPasswordLiveData: MutableLiveData<String?> = MutableLiveData()

    val isAuthDataValidLiveData: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(inputLoginLiveData) {
            value = invalidateAuthData()
        }
        addSource(inputPasswordLiveData) {
            value = invalidateAuthData()
        }
    }

//    private fun invalidateAuthData(): Boolean {
//        return login == inputLoginLiveData.value && password == inputLoginLiveData.value
//    }

    private fun invalidateAuthData(): Boolean {
        val login = inputLoginLiveData.value
        val password = inputLoginLiveData.value
        return login!!.length >= minLoginLength && password!!.length >= minPasswordLength
                && password.any { it.isDigit() } && password.any { it.isLetter() }
    }


}