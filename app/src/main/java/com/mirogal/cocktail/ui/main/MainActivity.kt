package com.mirogal.cocktail.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.main.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.ui.main.filter.CategoryDrinkFilter
import com.mirogal.cocktail.ui.main.filter.DrinkFilterFragment
import com.mirogal.cocktail.ui.main.history.HistoryPagerFragment
import com.mirogal.cocktail.ui.main.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainViewModel>(),
        HistoryPagerFragment.OnFragmentActionListener,
        DrinkFilterFragment.OnFragmentActionListener {

    override val contentLayoutResId = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    override fun configureView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            addHistoryPagerFragment()
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
            }
            true
        }
    }


    override fun onToolbarBtnFilterClick() {
        addDrinkFilterFragment()
    }

    override fun onFilterActionButtonClick(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        val fragment = supportFragmentManager.findFragmentByTag(HistoryPagerFragment::class.java.simpleName) as HistoryPagerFragment
        fragment.setFilter(alcoholFilter, categoryFilter)
    }


    private fun addHistoryPagerFragment() {
        val newFragment = HistoryPagerFragment.newInstance()
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fcv_container, newFragment, HistoryPagerFragment::class.java.simpleName)
            commit()
        }
    }

    private fun addDrinkFilterFragment() {
        val newFragment = DrinkFilterFragment.newInstance()
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fcv_container, newFragment, DrinkFilterFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }

    private fun showHistoryPagerFragment() {
        val pagerFragment = supportFragmentManager.findFragmentByTag(HistoryPagerFragment::class.java.simpleName)
        val profileFragment = supportFragmentManager.findFragmentByTag(ProfileFragment::class.java.simpleName)
        if (pagerFragment != null && pagerFragment.isAdded) {
            supportFragmentManager.beginTransaction().apply {
                setPrimaryNavigationFragment(profileFragment)
                show(pagerFragment)
                commit()
            }
        }
        if (profileFragment != null && profileFragment.isAdded) {
            supportFragmentManager.beginTransaction().apply {
                hide(profileFragment)
                commit()
            }
        }
    }

    private fun showProfileFragment() {
        val pagerFragment = supportFragmentManager.findFragmentByTag(HistoryPagerFragment::class.java.simpleName)
        val profileFragment = supportFragmentManager.findFragmentByTag(ProfileFragment::class.java.simpleName)
        if (pagerFragment != null && pagerFragment.isAdded) {
            supportFragmentManager.beginTransaction().apply {
                hide(pagerFragment)
                commit()
            }
        }
        if (profileFragment != null && profileFragment.isAdded) {
            supportFragmentManager.beginTransaction().apply {
                setPrimaryNavigationFragment(profileFragment)
                show(profileFragment)
                commit()
            }
        } else {
            val newFragment = ProfileFragment.newInstance()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fcv_container, newFragment, ProfileFragment::class.java.simpleName)
                commit()
            }
        }
    }

}