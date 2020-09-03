package com.mirogal.cocktail.presentation.ui.main.settings

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mirogal.cocktail.data.repository.source.AppSettingRepository
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel

class SettingsViewModel(
        private val appSettingRepository: AppSettingRepository,
        viewStateHandle: SavedStateHandle,
        application: Application
) : BaseViewModel(viewStateHandle, application) {

    val appLanguageLiveData: MutableLiveData<String?>
            = appSettingRepository.appLanguageLiveData

}