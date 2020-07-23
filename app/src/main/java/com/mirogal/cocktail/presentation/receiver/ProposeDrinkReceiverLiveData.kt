package com.mirogal.cocktail.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData

class ProposeDrinkReceiverLiveData(val context: Context) : LiveData<Int?>() {

    private var startId: Int = -1

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val isTimerFinish: Boolean = intent?.getBooleanExtra("isTimerFinish", false) ?: false
            if (isTimerFinish) {
                val finishId: Int = intent?.getIntExtra("finishCocktailId", -2) ?: -2
                if (startId == finishId) {
                    value = finishId
                    value = null
                }
            } else {
                startId = intent?.getIntExtra("startCocktailId", -1) ?: -1
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