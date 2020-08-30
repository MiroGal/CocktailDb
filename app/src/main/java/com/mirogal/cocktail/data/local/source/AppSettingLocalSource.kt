package com.mirogal.cocktail.data.local.source

import androidx.lifecycle.MutableLiveData

interface AppSettingLocalSource {

    val isBottomNavLabelShowLiveData: MutableLiveData<Boolean>
    val isBatteryIndicatorShowLiveData: MutableLiveData<Boolean>

}