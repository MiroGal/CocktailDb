package com.mirogal.cocktail.presentation

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.mirogal.cocktail.data.local.impl.source.AppSettingLocalSourceImpl
import com.mirogal.cocktail.di.Injector
import com.mirogal.cocktail.presentation.receiver.AirplaneModReceiver
import com.mirogal.cocktail.presentation.ui.base.BaseActivity
import java.util.*

class Application : Application() {

    private val airplaneModReceiver = AirplaneModReceiver()

    override fun onCreate() {
        super.onCreate()
        setLocale()
        Injector.init(this)
        registerReceiver(airplaneModReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    override fun onTerminate() {
        unregisterReceiver(airplaneModReceiver)
        super.onTerminate()
    }

    private fun setLocale() {
        val sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString(AppSettingLocalSourceImpl.EXTRA_KEY_APP_LANGUAGE, "en")
        BaseActivity.dLocale = Locale(language ?: "")
    }

}