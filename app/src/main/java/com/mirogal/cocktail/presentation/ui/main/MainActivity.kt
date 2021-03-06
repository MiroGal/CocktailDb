package com.mirogal.cocktail.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.mirogal.cocktail.R
import com.mirogal.cocktail.databinding.ActivityMainBinding
import com.mirogal.cocktail.presentation.constant.BottomNavTab
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
import com.mirogal.cocktail.presentation.ui.main.settings.dialog.LanguageDialogFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
        LogoutDialogFragment.OnActionListener,
        LanguageDialogFragment.OnActionListener,
        DayDrinkDialogFragment.OnActionListener {

    override val contentLayoutResId = R.layout.activity_main
    override val viewModel: MainViewModel by baseViewModels()

    override fun configureDataBinding(binding: ActivityMainBinding) {
        super.configureDataBinding(binding)
        dataBinding.viewmodel = viewModel
    }

    override fun configureView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            showFragment<DrinkPagerFragment>()
        }

        bottom_nav_view.selectedItemId = R.id.bottom_nav_drink
        bottom_nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                BottomNavTab.COCKTAIL.key -> viewModel.currentBottomNavTabLiveData.value = BottomNavTab.COCKTAIL
                BottomNavTab.PROFILE.key -> viewModel.currentBottomNavTabLiveData.value = BottomNavTab.PROFILE
                BottomNavTab.SETTING.key -> viewModel.currentBottomNavTabLiveData.value = BottomNavTab.SETTING
            }
            true
        }
    }

    private inline fun <reified T : BaseFragment<*, *>> showFragment() = with(supportFragmentManager) {
        val isFragmentFound: Boolean
        val targetFragment = findFragmentByTag(T::class.java.name).apply {
            isFragmentFound = this != null
        } ?: T::class.java.newInstance()
        commit(true) {
            setPrimaryNavigationFragment(targetFragment)
            when {
                isFragmentFound -> show(targetFragment)
                else -> add(R.id.fcv_container, targetFragment, T::class.java.name)
            }
            fragments
                    // skip dialogs
                    .filterIsInstance<BaseFragment<*, *>>()
                    // skip currently shown fragment
                    .filter { it != targetFragment && it != DrinkFilterFragment::class }
                    // hide other if needed
                    .forEach {
                        hide(it)
                    }
        }
    }

    override fun configureObserver() {
        viewModel.currentBottomNavTabLiveData.observe(this, {
            if (it != null && bottom_nav_view.selectedItemId != it.key) {
                bottom_nav_view.menu.findItem(it.key).isChecked = true
                when (it) {
                    BottomNavTab.COCKTAIL -> showFragment<DrinkPagerFragment>()
                    BottomNavTab.PROFILE -> showFragment<ProfileFragment>()
                    BottomNavTab.SETTING -> showFragment<SettingsFragment>()
                }
            }
        })
        viewModel.isBottomNavLabelShowLiveData.observe(this, {
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

    override fun onDialogLanguageBtnClick() {
        openMainActivity()
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

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
        Runtime.getRuntime().exit(0)
    }

    private fun openDrinkDetailActivity(cocktailId: Int, cocktailName: String?) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("cocktailId", cocktailId)
            putExtra("cocktailName", cocktailName)
        }
        startActivity(intent)
    }

}