package com.mirogal.cocktail.study.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class DrinkService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Text", Toast.LENGTH_SHORT).show()
        try {
            for(i in 0..3) {
                Thread.sleep(3000)
                Toast.makeText(this, "Text", Toast.LENGTH_SHORT).show()
            }
        } catch (ex: java.lang.Exception) {

        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

}