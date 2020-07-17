package com.mirogal.cocktail.presentation.service

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class DrinkOfDayService : JobIntentService(), LifecycleObserver {

    companion object {
        private const val JOB_ID = 1
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, DrinkOfDayService::class.java, JOB_ID, work)
        }
    }

    override fun onHandleWork(intent: Intent) {

//        startTimer()
        sentMessage(intent)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun startTimer() {
        for (i in 0..5) {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun sentMessage(intent: Intent) {
        val intentStart = Intent()
        intentStart.action = "ACTION_SNACKBAR_TIMER_START"
        intentStart.putExtra("startCocktailId", intent.getIntExtra("cocktailId", -2))
        baseContext.sendBroadcast(intentStart)
    }

}
