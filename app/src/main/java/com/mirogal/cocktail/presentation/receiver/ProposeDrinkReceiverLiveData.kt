package com.mirogal.cocktail.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData

class ProposeDrinkReceiverLiveData(val context: Context) : LiveData<Long?>() {

    private var startId: Long = -1

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val isTimerFinish: Boolean = intent?.getBooleanExtra("isTimerFinish", false) ?: false
            if (isTimerFinish) {
                val finishId: Long = intent?.getLongExtra("finishCocktailId", -2L) ?: -2L
                if (startId == finishId) {
                    value = finishId
                    value = null
                }
            } else {
                startId = intent?.getLongExtra("startCocktailId", -1L) ?: -1L
            }
        }
    }

    override fun onActive() {
        context.registerReceiver(receiver,
                IntentFilter().apply {
                    addAction("ACTION_SNACKBAR_TIMER_START")
                    addAction("ACTION_SNACKBAR_TIMER_FINISH")
                })
    }

    override fun onInactive() {
        context.unregisterReceiver(receiver)
    }

}