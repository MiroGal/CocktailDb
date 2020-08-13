package com.mirogal.cocktail.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.firebase.analytics.FirebaseAnalytics
import com.mirogal.cocktail.R
import com.mirogal.cocktail.firebase.AnalyticEvents
import com.mirogal.cocktail.presentation.extension.baseViewModels
import com.mirogal.cocktail.presentation.ui.auth.AuthActivity
import com.mirogal.cocktail.presentation.ui.base.BaseActivity
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.detail.DetailActivity
import com.mirogal.cocktail.presentation.ui.main.drink.DrinkFilterFragment
import com.mirogal.cocktail.presentation.ui.main.drink.DrinkPagerFragment
import com.mirogal.cocktail.presentation.ui.main.drink.dialog.DayDrinkDialogFragment
import com.mirogal.cocktail.presentation.ui.main.profile.ProfileFragment
import com.mirogal.cocktail.presentation.ui.main.profile.dialog.LogoutDialogFragment
import com.mirogal.cocktail.presentation.ui.main.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>(),
        LogoutDialogFragment.OnActionListener,
        DayDrinkDialogFragment.OnActionListener {

    override val contentLayoutResId = R.layout.activity_main
    override val viewModel: MainViewModel by baseViewModels()

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(this)

    override fun configureView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            showFragment<DrinkPagerFragment>()
        }

        bottom_nav_view.selectedItemId = R.id.bottom_nav_drink
        bottom_nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_drink -> {
                    showFragment<DrinkPagerFragment>()
                    firebaseAnalytics.logEvent(AnalyticEvents.MAIN_TAB_CHANGE,
                            bundleOf(AnalyticEvents.MAIN_TAB_CHANGE_KEY to "drink")
                    )
                }
                R.id.bottom_nav_profile -> {
                    showFragment<ProfileFragment>()
                    firebaseAnalytics.logEvent(AnalyticEvents.MAIN_TAB_CHANGE,
                            bundleOf(AnalyticEvents.MAIN_TAB_CHANGE_KEY to "profile")
                    )
                }
                R.id.bottom_nav_settings -> {
                    showFragment<SettingsFragment>()
                    firebaseAnalytics.logEvent(AnalyticEvents.MAIN_TAB_CHANGE,
                            bundleOf(AnalyticEvents.MAIN_TAB_CHANGE_KEY to "settings")
                    )
                }
            }
            true
        }
    }

    override fun configureObserver() {
        viewModel.isBottomNavLabelVisibleLiveData.observe(this, Observer {
            if (it) {
                bottom_nav_view.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            } else {
                bottom_nav_view.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
            }
        })
    }

    override fun onBackPressed() {
        val pagerFragment = supportFragmentManager.findFragmentByTag(DrinkPagerFragment::class.java.name)
        val filterFragment = pagerFragment?.childFragmentManager?.findFragmentByTag(DrinkFilterFragment::class.java.name)
        if (pagerFragment != null && pagerFragment.isVisible && filterFragment != null && filterFragment.isVisible) {
            pagerFragment.childFragmentManager.beginTransaction().apply {
                remove(filterFragment)
                commit()
            }
        } else {
            super.onBackPressed()
        }
    }


    override fun onDialogLogoutBtnLogoutClick() {
        openAuthActivity()
    }

    override fun onDialogDayDrinkBtnOkClick(cocktailId: Int, cocktailName: String?) {
        openDrinkDetailActivity(cocktailId, cocktailName)
    }

    private fun openAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

    private fun openDrinkDetailActivity(cocktailId: Int, cocktailName: String?) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("cocktailId", cocktailId)
            putExtra("cocktailName", cocktailName)
        }
        startActivity(intent)
    }

    private inline fun <reified T : BaseFragment<*>> showFragment() = with(supportFragmentManager) {
        val isFragmentFound: Boolean
        val targetFragment = findFragmentByTag(T::class.java.name)
                .apply { isFragmentFound = this != null }
                ?: T::class.java.newInstance()
        commit(true) {
            setPrimaryNavigationFragment(targetFragment)
            when {
                isFragmentFound -> show(targetFragment)
                else -> add(R.id.fcv_container, targetFragment, T::class.java.name)
            }
            fragments
                    // skip dialogs
                    .filterIsInstance<BaseFragment<*>>()
                    // skip currently shown fragment
                    .filter { it != targetFragment }
                    // hide other if needed
                    .forEach {
                        hide(it)
                    }
        }
    }

}