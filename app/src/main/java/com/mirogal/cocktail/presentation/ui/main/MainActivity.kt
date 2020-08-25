package com.mirogal.cocktail.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.mirogal.cocktail.R
import com.mirogal.cocktail.databinding.ActivityMainBinding
import com.mirogal.cocktail.presentation.ui.auth.AuthActivity
import com.mirogal.cocktail.presentation.ui.base.BaseActivity2
import com.mirogal.cocktail.presentation.ui.detail.DetailActivity
import com.mirogal.cocktail.presentation.ui.main.drink.DrinkFilterFragment
import com.mirogal.cocktail.presentation.ui.main.drink.DrinkPagerFragment
import com.mirogal.cocktail.presentation.ui.main.drink.dialog.DayDrinkDialogFragment
import com.mirogal.cocktail.presentation.ui.main.profile.ProfileFragment
import com.mirogal.cocktail.presentation.ui.main.profile.dialog.LogoutDialogFragment
import com.mirogal.cocktail.presentation.ui.main.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity2<MainViewModel, ActivityMainBinding>(),
        LogoutDialogFragment.OnActionListener,
        DayDrinkDialogFragment.OnActionListener {

    override val contentLayoutResId = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    override fun configureView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            showHistoryPagerFragment()
        }

        bottom_nav_view.selectedItemId = R.id.bottom_nav_drink
        bottom_nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_drink -> showHistoryPagerFragment()
                R.id.bottom_nav_profile -> showProfileFragment()
                R.id.bottom_nav_settings -> showSettingsFragment()
            }
            true
        }
    }

    override fun configureDataBinding(binding: ActivityMainBinding) {
        super.configureDataBinding(binding)
        dataBinding.viewmodel = viewModel
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
        val pagerFragment = supportFragmentManager.findFragmentByTag(DrinkPagerFragment::class.java.simpleName)
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

    private fun showHistoryPagerFragment() {
        val pagerFragment = supportFragmentManager.findFragmentByTag(DrinkPagerFragment::class.java.simpleName)
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
            val newFragment = DrinkPagerFragment.newInstance()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fcv_container, newFragment, DrinkPagerFragment::class.java.simpleName)
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
        val pagerFragment = supportFragmentManager.findFragmentByTag(DrinkPagerFragment::class.java.simpleName)
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
        val pagerFragment = supportFragmentManager.findFragmentByTag(DrinkPagerFragment::class.java.simpleName)
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