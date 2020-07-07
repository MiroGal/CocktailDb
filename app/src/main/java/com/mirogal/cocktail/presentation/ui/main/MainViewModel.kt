package com.mirogal.cocktail.presentation.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel


class MainViewModel(application: Application) : BaseViewModel(application) {

    val isBottomNavLabelShowLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isBottomNavLabelShowLiveData.value = true
    }

}