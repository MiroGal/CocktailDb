package com.mirogal.cocktail.presentation.service

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import java.util.concurrent.TimeUnit

class ProposeDrinkService : JobIntentService() {

    companion object {
        private const val JOB_ID = 1
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, ProposeDrinkService::class.java, JOB_ID, work)
        }
    }

    override fun onHandleWork(intent: Intent) {

        val intentStart = Intent()
        intentStart.action = "ACTION_SNACKBAR_TIMER_START"
        intentStart.putExtra("startCocktailId", intent.getLongExtra("cocktailId", -2L))
        baseContext.sendBroadcast(intentStart)

        Thread {
            Thread.sleep(TimeUnit.SECONDS.toMillis(3))

            val newIntent = Intent()
            newIntent.action = "ACTION_SNACKBAR_TIMER_FINISH"
            newIntent.putExtra("isTimerFinish", true)
            newIntent.putExtra("finishCocktailId", intent.getLongExtra("cocktailId", -3L))
            baseContext.sendBroadcast(newIntent)

        }.start()
    }

}
