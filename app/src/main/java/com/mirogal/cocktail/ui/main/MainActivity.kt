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
import com.mirogal.cocktail.ui.main.profile.TestFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(),
        HistoryPagerFragment.OnFragmentActionListener,
        DrinkFilterFragment.OnFragmentActionListener,
        ProfileFragment.OnFragmentActionListener,
        TestFragment.OnFragmentActionListener {

    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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


    override fun onToolbarFilterButtonClick(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        addDrinkFilterFragment(alcoholFilter, categoryFilter)
    }

    override fun onFilterActionButtonClick(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        val fragment = supportFragmentManager.findFragmentByTag(HistoryPagerFragment::class.java.simpleName) as HistoryPagerFragment
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


    private fun addHistoryPagerFragment() {
        val newFragment = HistoryPagerFragment.newInstance()
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fcv_container, newFragment, HistoryPagerFragment::class.java.simpleName)
            commit()
        }
    }

    private fun addDrinkFilterFragment(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        val newFragment = DrinkFilterFragment.newInstance(alcoholFilter, categoryFilter)
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fcv_container, newFragment, DrinkFilterFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }

    private fun addTestFragment(number: Int, message: String?) {
        val newFragment = TestFragment.newInstance(number, message)
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fcv_container, newFragment, TestFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }

    private fun deleteTestFragment() {
        val testFragment = supportFragmentManager.findFragmentByTag(TestFragment::class.java.simpleName)
        supportFragmentManager.beginTransaction().apply {
            remove(testFragment!!)
//            addToBackStack(null)
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