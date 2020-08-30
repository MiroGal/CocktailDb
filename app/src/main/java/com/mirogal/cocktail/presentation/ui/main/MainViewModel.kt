package com.mirogal.cocktail.presentation.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mirogal.cocktail.data.repository.source.AppSettingRepository
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel

class MainViewModel(
        private val appSettingRepository: AppSettingRepository,
        viewStateHandle: SavedStateHandle,
        application: Application
) : BaseViewModel(viewStateHandle, application) {

    val isBottomNavLabelShowLiveData: MutableLiveData<Boolean>
            = appSettingRepository.isBottomNavLabelShowLiveData
    val isBatteryIndicatorShowLiveData: MutableLiveData<Boolean>
            = appSettingRepository.isBatteryIndicatorShowLiveData

}