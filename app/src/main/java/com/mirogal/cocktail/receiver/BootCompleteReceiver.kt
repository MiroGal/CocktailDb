package com.mirogal.cocktail.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mirogal.cocktail.service.BootCompleteService

class BootCompleteReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        BootCompleteService.enqueueWork(context!!, Intent(context, BootCompleteService::class.java))
    }

}