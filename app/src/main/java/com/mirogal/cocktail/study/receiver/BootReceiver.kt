package com.mirogal.cocktail.study.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import com.mirogal.cocktail.R
import com.mirogal.cocktail.study.service.BootService
import com.mirogal.cocktail.study.service.DrinkService

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        BootService.enqueueWork(context!!, Intent(context, BootService::class.java))
    }

}