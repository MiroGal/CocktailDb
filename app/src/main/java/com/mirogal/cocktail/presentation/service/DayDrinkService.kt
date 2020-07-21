package com.mirogal.cocktail.presentation.service

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.mirogal.cocktail.R

class DayDrinkService : JobIntentService() {

    companion object {
        private const val KEY_IS_TIMER_FINISH = "KEY_IS_TIMER_FINISH"
        private const val JOB_ID = 1
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, DayDrinkService::class.java, JOB_ID, work)
        }
    }

    override fun onHandleWork(intent: Intent) {

        val sharedPreferences = applicationContext.getSharedPreferences(applicationContext.getString(R.string.app_name), Context.MODE_PRIVATE)
        val sharedPreferencesEditor = sharedPreferences.edit()

        for (i in 0..3) {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        sharedPreferencesEditor.putBoolean(KEY_IS_TIMER_FINISH, true).apply()
    }

}
