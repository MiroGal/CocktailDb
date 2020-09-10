package com.mirogal.cocktail.data.repository.source

import androidx.lifecycle.MutableLiveData
import com.mirogal.cocktail.data.repository.source.base.BaseRepository

interface AppSettingRepository : BaseRepository {

    val isBottomNavLabelShowLiveData: MutableLiveData<Boolean>
    val isBatteryIndicatorShowLiveData: MutableLiveData<Boolean>
    val appLanguageLiveData: MutableLiveData<String?>

}