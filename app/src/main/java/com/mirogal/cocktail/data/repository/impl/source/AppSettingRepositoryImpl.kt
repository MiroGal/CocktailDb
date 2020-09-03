package com.mirogal.cocktail.data.repository.impl.source

import androidx.lifecycle.MutableLiveData
import com.mirogal.cocktail.data.local.source.AppSettingLocalSource
import com.mirogal.cocktail.data.repository.source.AppSettingRepository

class AppSettingRepositoryImpl(
        private val appSettingLocalSource: AppSettingLocalSource
) : AppSettingRepository {

    override val isBottomNavLabelShowLiveData: MutableLiveData<Boolean>
            = appSettingLocalSource.isBottomNavLabelShowLiveData
    override val isBatteryIndicatorShowLiveData: MutableLiveData<Boolean>
            = appSettingLocalSource.isBatteryIndicatorShowLiveData
    override val appLanguageLiveData: MutableLiveData<String?>
            = appSettingLocalSource.appLanguageLiveData

}