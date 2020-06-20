package com.mirogal.cocktail.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import com.mirogal.cocktail.R

class AirplaneModReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (isAirplaneModeOn(context!!)) {
            Toast.makeText(context, context.getString(R.string.receiver_airplane_mod_message_on), Toast.LENGTH_LONG).show()
        }
    }

    private fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.Global.getInt(context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }

}