package com.mirogal.cocktail.presentation.service

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class ProposeDrinkService : JobIntentService() {

    companion object {
        private const val JOB_ID = 1
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, ProposeDrinkService::class.java, JOB_ID, work)
        }
    }

    override fun onHandleWork(intent: Intent) {

        Thread(Runnable {
            for (i in 0..3) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            val newIntent = Intent()
            newIntent.action = "ACTION_SNACKBAR_TIMER_FINISH"
            newIntent.putExtra("isTimerFinish", true)
            newIntent.putExtra("finishCocktailId", intent.getIntExtra("finishCocktailId", -2))
            baseContext.sendBroadcast(newIntent)

        }).start()
    }

}
