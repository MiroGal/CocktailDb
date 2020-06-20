package com.mirogal.cocktail

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import com.mirogal.cocktail.receiver.AirplaneModReceiver

class Application : Application() {

    private val airplaneModReceiver = AirplaneModReceiver()

    override fun onCreate() {
        super.onCreate()
        registerReceiver(airplaneModReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    override fun onTerminate() {
        unregisterReceiver(airplaneModReceiver)
        super.onTerminate()
    }

}