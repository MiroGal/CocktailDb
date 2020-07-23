package com.mirogal.cocktail.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.LiveData

class BatteryChangeReceiverLiveData(val context: Context) : LiveData<Pair<Int, Int>?>() {

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val level: Int = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1

            val status: Int = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_NOT_CHARGING

            val isLow: Boolean = intent?.action.equals(Intent.ACTION_BATTERY_LOW)

            val state = if (isCharging) {
                1
            } else {
                if (isLow) {
                    3
                } else {
                    2
                }
            }

            value = Pair(level, state)
        }
    }

    override fun onActive() {
        context.registerReceiver(receiver,
                IntentFilter().apply {
                    addAction(Intent.ACTION_BATTERY_CHANGED)
                    addAction("android.intent.action.BATTERY_LOW")
                    addAction("android.intent.action.BATTERY_OK")
                })
    }

    override fun onInactive() {
        context.unregisterReceiver(receiver)
    }

}