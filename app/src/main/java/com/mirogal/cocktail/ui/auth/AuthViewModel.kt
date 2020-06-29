package com.mirogal.cocktail.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData


class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val login = "MiroGal"
    private val password = "Miro89"

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

    private fun invalidateAuthData(): Boolean {
        return login == inputLoginLiveData.value && password == inputLoginLiveData.value
    }


}