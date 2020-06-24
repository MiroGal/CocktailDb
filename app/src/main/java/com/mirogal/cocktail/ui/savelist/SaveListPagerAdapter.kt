package com.mirogal.cocktail.ui.savelist

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SaveListPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var fragment1: DrinkHistoryFragment? = null
    var fragment2: FavoriteDrinkFragment? = null

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                fragment1 = DrinkHistoryFragment.newInstance()
                fragment1 as DrinkHistoryFragment
            }
            else -> {
                fragment2 = FavoriteDrinkFragment.newInstance()
                fragment2 as FavoriteDrinkFragment
            }
        }
    }

}