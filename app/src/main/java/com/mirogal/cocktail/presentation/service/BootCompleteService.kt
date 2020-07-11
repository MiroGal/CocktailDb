package com.mirogal.cocktail.presentation.service

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.JobIntentService
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.auth.AuthActivity

class BootCompleteService : JobIntentService() {

    companion object {
        private const val JOB_ID = 1
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, BootCompleteService::class.java, JOB_ID, work)
        }
    }

    override fun onHandleWork(intent: Intent) {

        for (i in 0..2) {

            val builder = StringBuilder()
            builder.append("Додаток ")
                    .append(applicationContext.getString(R.string.app_name))
                    .append(" запуститься через: ")
                    .append((3 - i).toString())
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(applicationContext, builder.toString(), Toast.LENGTH_SHORT).show() }

            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        startActivity(Intent(this@BootCompleteService, AuthActivity::class.java))
    }

}