package com.mirogal.cocktail.ui.main

import android.os.Bundle
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.main.history.DrinkHistoryContainerFragment
import com.mirogal.cocktail.ui.main.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.ui.main.filter.CategoryDrinkFilter
import com.mirogal.cocktail.ui.main.filter.DrinkFilterFragment
import com.mirogal.cocktail.ui.main.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(),
        DrinkHistoryContainerFragment.OnFragmentActionListener,
        DrinkFilterFragment.OnFragmentActionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            addSaveListFragment()
        }

        bottom_nav_view.selectedItemId = R.id.bottom_nav_drink

        bottom_nav_view.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.bottom_nav_drink -> {
                    showSaveListFragment()
                }
                R.id.bottom_nav_profile -> {
                    showProfileFragment()
                }
            }
            true
        }
    }


    override fun onToolbarButtonFilterClick(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        addFilterFragment(alcoholFilter, categoryFilter)
    }

    override fun onFilterButtonClick(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        val fragment = supportFragmentManager.findFragmentByTag(DrinkHistoryContainerFragment::class.java.simpleName) as DrinkHistoryContainerFragment
        fragment.setFilter(alcoholFilter, categoryFilter)
    }


    private fun addSaveListFragment() {
        val newFragment = DrinkHistoryContainerFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, newFragment, DrinkHistoryContainerFragment::class.java.simpleName)
        transaction.commit()
    }

    private fun addFilterFragment(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        val newFragment = DrinkFilterFragment.newInstance(alcoholFilter, categoryFilter)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, newFragment, DrinkFilterFragment::class.java.simpleName)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showSaveListFragment() {
        val saveListFragment = supportFragmentManager.findFragmentByTag(DrinkHistoryContainerFragment::class.java.simpleName)
        val profileFragment = supportFragmentManager.findFragmentByTag(ProfileFragment::class.java.simpleName)
        val transaction = supportFragmentManager.beginTransaction()
        if (saveListFragment != null && saveListFragment.isAdded) {
            transaction.show(saveListFragment)
        }
        if (profileFragment != null && profileFragment.isAdded) {
            transaction.hide(profileFragment)
        }
        transaction.commit()
    }

    private fun showProfileFragment() {
        val saveListFragment = supportFragmentManager.findFragmentByTag(DrinkHistoryContainerFragment::class.java.simpleName)
        val profileFragment = supportFragmentManager.findFragmentByTag(ProfileFragment::class.java.simpleName)
        val transaction = supportFragmentManager.beginTransaction()
        if (saveListFragment != null && saveListFragment.isAdded) {
            transaction.hide(saveListFragment)
        }
        if (profileFragment != null && profileFragment.isAdded) {
            transaction.show(profileFragment)
        } else {
            val newFragment = ProfileFragment.newInstance()
            transaction.add(R.id.fragment_container, newFragment, ProfileFragment::class.java.simpleName)
        }
        transaction.commit()
    }

}