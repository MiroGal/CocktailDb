package com.mirogal.cocktail.study.boot

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.JobIntentService
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.savelist.SaveListActivity


class BootCompleteService : JobIntentService() {

    companion object {
        private const val JOB_ID = 1
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, BootCompleteService::class.java, JOB_ID, work)
        }
    }

    override fun onHandleWork(intent: Intent) {

        for (i in 0..3) {

            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val builder = StringBuilder()
            builder.append("Додаток ")
                    .append(applicationContext.getString(R.string.app_name))
                    .append(" запуститься через: ")
                    .append((3 - i).toString())
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(applicationContext, builder.toString(), Toast.LENGTH_SHORT).show() }
        }
        startActivity(Intent(this@BootCompleteService, SaveListActivity::class.java))
    }

}