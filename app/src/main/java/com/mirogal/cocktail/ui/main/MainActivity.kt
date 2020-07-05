package com.mirogal.cocktail.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.main.filter.DrinkFilterFragment
import com.mirogal.cocktail.ui.main.history.HistoryPagerFragment
import com.mirogal.cocktail.ui.main.profile.ProfileFragment
import com.mirogal.cocktail.ui.main.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainViewModel>() {

    override val contentLayoutResId = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    override fun configureView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            showHistoryPagerFragment()
        }

        bottom_nav_view.selectedItemId = R.id.bottom_nav_drink

        bottom_nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_drink -> {
                    showHistoryPagerFragment()
                }
                R.id.bottom_nav_profile -> {
                    showProfileFragment()
                }
                R.id.bottom_nav_settings -> {
                    showSettingsFragment()
                }
            }
            true
        }

        setObserver()
    }

    private fun setObserver() {
        viewModel.isBottomNavLabelShowLiveData.observe(this, Observer {
            if (it) {
                bottom_nav_view.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            } else {
                bottom_nav_view.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
            }
        })
    }

    override fun onBackPressed() {
        val pagerFragment = supportFragmentManager.findFragmentByTag(HistoryPagerFragment::class.java.simpleName)
        val filterFragment = pagerFragment?.childFragmentManager?.findFragmentByTag(DrinkFilterFragment::class.java.simpleName)
        if (pagerFragment != null && pagerFragment.isVisible && filterFragment != null && filterFragment.isVisible) {
            pagerFragment.childFragmentManager.beginTransaction().apply {
                remove(filterFragment)
                commit()
            }
        } else {
            super.onBackPressed()
        }
    }


    private fun showHistoryPagerFragment() {
        val pagerFragment = supportFragmentManager.findFragmentByTag(HistoryPagerFragment::class.java.simpleName)
        val profileFragment = supportFragmentManager.findFragmentByTag(ProfileFragment::class.java.simpleName)
        val settingsFragment = supportFragmentManager.findFragmentByTag(SettingsFragment::class.java.simpleName)
        if (pagerFragment != null && pagerFragment.isAdded) {
            supportFragmentManager.beginTransaction().apply {
                setPrimaryNavigationFragment(pagerFragment)
                show(pagerFragment)
                if (profileFragment != null) {
                    hide(profileFragment)
                }
                if (settingsFragment != null) {
                    hide(settingsFragment)
                }
                commit()
            }
        } else {
            val newFragment = HistoryPagerFragment.newInstance()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fcv_container, newFragment, HistoryPagerFragment::class.java.simpleName)
                if (profileFragment != null) {
                    hide(profileFragment)
                }
                if (settingsFragment != null) {
                    hide(settingsFragment)
                }
                commit()
            }
        }
    }

    private fun showProfileFragment() {
        val pagerFragment = supportFragmentManager.findFragmentByTag(HistoryPagerFragment::class.java.simpleName)
        val profileFragment = supportFragmentManager.findFragmentByTag(ProfileFragment::class.java.simpleName)
        val settingsFragment = supportFragmentManager.findFragmentByTag(SettingsFragment::class.java.simpleName)
        if (profileFragment != null && profileFragment.isAdded) {
            supportFragmentManager.beginTransaction().apply {
                setPrimaryNavigationFragment(profileFragment)
                show(profileFragment)
                if (pagerFragment != null) {
                    hide(pagerFragment)
                }
                if (settingsFragment != null) {
                    hide(settingsFragment)
                }
                commit()
            }
        } else {
            val newFragment = ProfileFragment.newInstance()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fcv_container, newFragment, ProfileFragment::class.java.simpleName)
                if (pagerFragment != null) {
                    hide(pagerFragment)
                }
                if (settingsFragment != null) {
                    hide(settingsFragment)
                }
                commit()
            }
        }
    }

    private fun showSettingsFragment() {
        val pagerFragment = supportFragmentManager.findFragmentByTag(HistoryPagerFragment::class.java.simpleName)
        val profileFragment = supportFragmentManager.findFragmentByTag(ProfileFragment::class.java.simpleName)
        val settingsFragment = supportFragmentManager.findFragmentByTag(SettingsFragment::class.java.simpleName)
        if (settingsFragment != null && settingsFragment.isAdded) {
            supportFragmentManager.beginTransaction().apply {
                setPrimaryNavigationFragment(settingsFragment)
                show(settingsFragment)
                if (pagerFragment != null) {
                    hide(pagerFragment)
                }
                if (profileFragment != null) {
                    hide(profileFragment)
                }
                commit()
            }
        } else {
            val newFragment = SettingsFragment.newInstance()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fcv_container, newFragment, SettingsFragment::class.java.simpleName)
                if (pagerFragment != null) {
                    hide(pagerFragment)
                }
                if (profileFragment != null) {
                    hide(profileFragment)
                }
                commit()
            }
        }
    }

}