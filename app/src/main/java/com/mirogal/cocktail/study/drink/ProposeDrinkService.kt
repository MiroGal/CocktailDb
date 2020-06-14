package com.mirogal.cocktail.study.drink

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

        for (i in 0..3) {

            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            Log.d(this.javaClass.simpleName, i.toString())
        }

        val newIntent = Intent()
        newIntent.action = "ACTION_SNACKBAR"
        newIntent.putExtra("KEY", intent.getIntExtra("KEY", 50))
        baseContext.sendBroadcast(newIntent)
    }

}