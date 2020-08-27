package com.mirogal.cocktail.presentation.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel

class MainViewModel(
        viewStateHandle: SavedStateHandle,
        application: Application
) : BaseViewModel(viewStateHandle, application) {

    val isBottomNavLabelVisibleLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isBatteryIndicatorVisibleLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isBottomNavLabelVisibleLiveData.value = true
        isBatteryIndicatorVisibleLiveData.value = true
    }

}