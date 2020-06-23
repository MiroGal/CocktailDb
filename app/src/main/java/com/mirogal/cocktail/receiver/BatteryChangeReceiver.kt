package com.mirogal.cocktail.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BATTERY_LOW
import android.os.BatteryManager


class BatteryChangeReceiver : BroadcastReceiver() {

    private var listener: OnBatteryChangeListener? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        val level: Int = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1

        val status: Int = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_NOT_CHARGING

        val isLow: Boolean = intent!!.action.equals(ACTION_BATTERY_LOW)

        val state = if (isCharging) {
            1
        } else {
            if (isLow) {
                3
            } else {
                2
            }
        }

        listener!!.onBatteryChange(level, state)
    }

    fun setBatteryChangeListener(context: Context?) {
        listener = context as OnBatteryChangeListener?
    }

    interface OnBatteryChangeListener {
        fun onBatteryChange(level: Int, state: Int)
    }

}