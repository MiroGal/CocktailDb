package com.mirogal.cocktail.ui.main.history

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var fragment1: DrinkHistoryFragment? = null
    var fragment2: DrinkFavoriteFragment? = null

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                fragment1 = DrinkHistoryFragment.newInstance()
                fragment1 as DrinkHistoryFragment
            }
            else -> {
                fragment2 = DrinkFavoriteFragment.newInstance()
                fragment2 as DrinkFavoriteFragment
            }
        }
    }

}