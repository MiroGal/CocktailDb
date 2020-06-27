package com.mirogal.cocktail.ui.main

import android.os.Bundle
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.main.history.DrinkHistoryPagerFragment
import com.mirogal.cocktail.ui.main.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.ui.main.filter.CategoryDrinkFilter
import com.mirogal.cocktail.ui.main.filter.DrinkFilterFragment
import com.mirogal.cocktail.ui.main.profile.ProfileFragment
import com.mirogal.cocktail.ui.main.profile.TestFragment
import kotlinx.android.synthetic.main.activity_navigation.*


class MainActivity : BaseActivity(),
        DrinkHistoryPagerFragment.OnFragmentActionListener,
        DrinkFilterFragment.OnFragmentActionListener,
        ProfileFragment.OnFragmentActionListener,
        TestFragment.OnFragmentActionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        if (savedInstanceState == null) {
            addDrinkHistoryPagerFragment()
        }

        bottom_nav_view.selectedItemId = R.id.bottom_nav_drink

        bottom_nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_drink -> {
                    showDrinkHistoryPagerFragment()
                }
                R.id.bottom_nav_profile -> {
                    showProfileFragment()
                }
            }
            true
        }
    }


    override fun onToolbarFilterButtonClick(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        addFilterFragment(alcoholFilter, categoryFilter)
    }

    override fun onFilterActionButtonClick(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        val fragment = supportFragmentManager.findFragmentByTag(DrinkHistoryPagerFragment::class.java.simpleName) as DrinkHistoryPagerFragment
        fragment.setFilter(alcoholFilter, categoryFilter)
    }

    override fun onStartTestButtonClick() {
        addTestFragment(1, null)
    }

    override fun onTestActionButtonClick(number: Int, message: String?) {
        if (message != null) {
            deleteTestFragment()
            return
        }
        when (number) {
            1 -> {
                addTestFragment(number + 1, null)
            }
            2 -> {
                addTestFragment(number + 1, null)
                addTestFragment(number + 2, null)
            }
            4 -> {
                addTestFragment(number + 1, null)
                addTestFragment(number + 1, "END")
            }
        }
    }


    private fun addDrinkHistoryPagerFragment() {
        val newFragment = DrinkHistoryPagerFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, newFragment, DrinkHistoryPagerFragment::class.java.simpleName)
        transaction.commit()
    }

    private fun addFilterFragment(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        val newFragment = DrinkFilterFragment.newInstance(alcoholFilter, categoryFilter)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, newFragment, DrinkFilterFragment::class.java.simpleName)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun addTestFragment(number: Int, message: String?) {
        val newFragment = TestFragment.newInstance(number, message)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, newFragment, TestFragment::class.java.simpleName)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun deleteTestFragment() {
        val testFragment = supportFragmentManager.findFragmentByTag(TestFragment::class.java.simpleName)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.detach(testFragment!!)
//        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showDrinkHistoryPagerFragment() {
        val containerFragment = supportFragmentManager.findFragmentByTag(DrinkHistoryPagerFragment::class.java.simpleName)
        val profileFragment = supportFragmentManager.findFragmentByTag(ProfileFragment::class.java.simpleName)
        val transaction = supportFragmentManager.beginTransaction()
        if (containerFragment != null && containerFragment.isAdded) {
            transaction.setPrimaryNavigationFragment(profileFragment)
            transaction.show(containerFragment)
        }
        if (profileFragment != null && profileFragment.isAdded) {
            transaction.hide(profileFragment)
        }
        transaction.commit()
    }

    private fun showProfileFragment() {
        val containerFragment = supportFragmentManager.findFragmentByTag(DrinkHistoryPagerFragment::class.java.simpleName)
        val profileFragment = supportFragmentManager.findFragmentByTag(ProfileFragment::class.java.simpleName)
        val transaction = supportFragmentManager.beginTransaction()
        if (containerFragment != null && containerFragment.isAdded) {
            transaction.hide(containerFragment)
        }
        if (profileFragment != null && profileFragment.isAdded) {
            transaction.setPrimaryNavigationFragment(profileFragment)
            transaction.show(profileFragment)
        } else {
            val newFragment = ProfileFragment.newInstance()
            transaction.add(R.id.fragment_container, newFragment, ProfileFragment::class.java.simpleName)
        }
        transaction.commit()
    }

}