package com.mirogal.cocktail.presentation.ui.auth

import android.app.Application
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.lifecycle.*
import com.mirogal.cocktail.data.repository.source.AuthRepository
import com.mirogal.cocktail.data.repository.source.UserRepository
import com.mirogal.cocktail.presentation.constant.AuthDataValidStatus
import com.mirogal.cocktail.presentation.mapper.UserModelMapper
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel

class AuthViewModel(
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository,
        private val userModelMapper: UserModelMapper,
        viewStateHandle: SavedStateHandle,
        application: Application
) : BaseViewModel(viewStateHandle, application) {

    val inputLoginLiveData: MutableLiveData<String?> by stateHandle(validLogin)
    val inputPasswordLiveData: MutableLiveData<String?> by stateHandle(validPassword)

    val isAuthDataCorrectLiveData: LiveData<Boolean?>
    val isAuthDataValidLiveData: LiveData<AuthDataValidStatus?>

    private val isAuthDataValidObserver: Observer<in AuthDataValidStatus?> = Observer { }

    companion object {
        const val validLogin = "MiroGal"
        const val validPassword = "Miro89"
        const val minLoginLength = 6
        const val minPasswordLength = 6
    }

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

        isAuthDataValidLiveData.observeForever(isAuthDataValidObserver)
    }

    override fun onCleared() {
        isAuthDataValidLiveData.removeObserver(isAuthDataValidObserver)
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