package com.mirogal.cocktail.presentation.ui.mainnative

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.authnative.AuthActivity
import com.mirogal.cocktail.presentation.ui.basenative.BaseActivity
import com.mirogal.cocktail.presentation.ui.detailnative.DetailActivity
import com.mirogal.cocktail.presentation.ui.mainnative.drink.DrinkFilterFragment
import com.mirogal.cocktail.presentation.ui.mainnative.drink.DrinkPagerFragment
import com.mirogal.cocktail.presentation.ui.mainnative.drink.dialog.DayDrinkDialogFragment
import com.mirogal.cocktail.presentation.ui.mainnative.profile.ProfileFragment
import com.mirogal.cocktail.presentation.ui.mainnative.profile.dialog.LogoutDialogFragment
import com.mirogal.cocktail.presentation.ui.mainnative.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>(),
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

    override fun configureObserver(savedInstanceState: Bundle?) {
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