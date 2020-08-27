package com.mirogal.cocktail.presentation

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import com.mirogal.cocktail.di.Injector
import com.mirogal.cocktail.presentation.receiver.AirplaneModReceiver

class Application : Application() {

    private val airplaneModReceiver = AirplaneModReceiver()

    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
        registerReceiver(airplaneModReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    override fun onTerminate() {
        unregisterReceiver(airplaneModReceiver)
        super.onTerminate()
    }

}