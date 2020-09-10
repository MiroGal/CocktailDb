package com.mirogal.cocktail.data.local.impl.source

import androidx.lifecycle.MutableLiveData
import com.mirogal.cocktail.data.local.impl.SharedPrefsHelper
import com.mirogal.cocktail.data.local.impl.source.base.BaseLocalSourceImpl
import com.mirogal.cocktail.data.local.source.AppSettingLocalSource

class AppSettingLocalSourceImpl (
        preferences: SharedPrefsHelper
) : BaseLocalSourceImpl(preferences),
        AppSettingLocalSource {

    override val isBottomNavLabelShowLiveData: MutableLiveData<Boolean>
            = sharedPrefLiveData(EXTRA_KEY_IS_SHOW_NAVIGATION_TITLE, defaultValue = true)
    override val isBatteryIndicatorShowLiveData: MutableLiveData<Boolean>
            = sharedPrefLiveData(EXTRA_KEY_IS_SHOW_BATTERY_INDICATOR, defaultValue = false)
    override val appLanguageLiveData: MutableLiveData<String?>
            = sharedPrefLiveData(EXTRA_KEY_APP_LANGUAGE, "en")

    companion object {
        const val EXTRA_KEY_IS_SHOW_NAVIGATION_TITLE = "EXTRA_KEY_IS_SHOW_NAVIGATION_TITLE"
        const val EXTRA_KEY_IS_SHOW_BATTERY_INDICATOR = "EXTRA_KEY_IS_SHOW_BATTERY_INDICATOR"
        const val EXTRA_KEY_APP_LANGUAGE = "EXTRA_KEY_APP_LANGUAGE"
    }

}