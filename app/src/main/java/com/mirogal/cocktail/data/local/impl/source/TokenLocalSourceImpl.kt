package com.mirogal.cocktail.data.local.impl.source

import androidx.lifecycle.LiveData
import com.mirogal.cocktail.data.local.impl.SharedPrefsHelper
import com.mirogal.cocktail.data.local.impl.source.base.BaseLocalSourceImpl
import com.mirogal.cocktail.data.local.source.TokenLocalSource

class TokenLocalSourceImpl(
        preferences: SharedPrefsHelper
) : BaseLocalSourceImpl(preferences),
        TokenLocalSource {

    override val tokenLiveData: LiveData<String?> = sharedPrefLiveData(TOKEN, "")

    override var token: String? = sharedPrefsHelper.get(TOKEN, "")
        get() = sharedPrefsHelper.get(TOKEN, field)
        set(value) {
            sharedPrefsHelper.set(TOKEN, value)
        }

    companion object {
        const val TOKEN = "TOKEN"
    }

}