package com.mirogal.cocktail.ui.savelist

import android.os.Bundle
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseActivity
import com.mirogal.cocktail.ui.savelist.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.ui.savelist.filter.CategoryDrinkFilter
import kotlinx.android.synthetic.main.activity_save_list.*

class SaveListActivity : BaseActivity(),
        SaveListFragment.OnFragmentActionListener,
        FilterFragment.OnFragmentActionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_list)

        bottom_nav_view.selectedItemId = R.id.bottom_nav_drink
        bottom_nav_view.setOnNavigationItemSelectedListener {item ->
            when (item.itemId) {
                R.id.bottom_nav_drink -> {
                    replaceSaveListFragment()
                }
                R.id.bottom_nav_profile -> {
                    replaceProfileFragment()
                }
            }
            true
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, SaveListFragment.newInstance(), SaveListFragment::class.java.simpleName)
                    .commit()
        }
    }


    override fun onToolbarBtnFilterClick(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        addFilterFragment(alcoholFilter, categoryFilter)
    }

    override fun onActionButtonClick(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        val fragment = supportFragmentManager.findFragmentByTag(SaveListFragment::class.java.simpleName) as SaveListFragment
        fragment.setFilter(alcoholFilter, categoryFilter)
    }


    private fun replaceSaveListFragment() {
        val newFragment = SaveListFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, newFragment, SaveListFragment::class.java.simpleName)
        transaction.commit()
    }

    private fun replaceProfileFragment() {
        val newFragment = ProfileFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, newFragment, ProfileFragment::class.java.simpleName)
        transaction.commit()
    }

    private fun addFilterFragment(alcoholFilter: AlcoholDrinkFilter?, categoryFilter: CategoryDrinkFilter?) {
        val newFragment = FilterFragment.newInstance(alcoholFilter, categoryFilter)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, newFragment, FilterFragment::class.java.simpleName)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}