package com.mirogal.cocktail.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mirogal.cocktail.ui.base.BaseViewModel


class MainViewModel(application: Application) : BaseViewModel(application) {

    val isBottomNavLabelShowLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isBottomNavLabelShowLiveData.value = true
    }

}