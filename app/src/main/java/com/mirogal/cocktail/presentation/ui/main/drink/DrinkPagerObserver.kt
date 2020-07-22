package com.mirogal.cocktail.presentation.ui.main.drink

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.main.drink.dialog.DayDrinkDialogFragment

class DrinkPagerObserver(
        val context: AppCompatActivity
) : LifecycleObserver {

    private val sharedPreferences = context.applicationContext
            .getSharedPreferences(context.applicationContext.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val sharedPreferencesEditor = sharedPreferences.edit()

    companion object {
        private const val KEY_IS_TIMER_FINISH = "KEY_IS_TIMER_FINISH"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
//        val intent = Intent(context, DayDrinkService::class.java)
//        DayDrinkService.enqueueWork(context, intent)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        if (sharedPreferences.getBoolean(KEY_IS_TIMER_FINISH, false)) {
            sharedPreferencesEditor.putBoolean(KEY_IS_TIMER_FINISH, false).apply()
            val pagerFragment = context.supportFragmentManager.findFragmentByTag(DrinkPagerFragment::class.java.simpleName) as DrinkPagerFragment?
            val dialogFragment = pagerFragment?.childFragmentManager?.findFragmentByTag(DayDrinkDialogFragment::class.java.simpleName)
            if (pagerFragment != null && pagerFragment.isVisible && dialogFragment == null) {
                pagerFragment.showDayDrinkDialog(12768, "Frappe")
            }
        }
    }

}