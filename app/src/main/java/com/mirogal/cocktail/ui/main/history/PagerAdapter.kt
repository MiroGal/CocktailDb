package com.mirogal.cocktail.ui.main.history

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                DrinkHistoryFragment.newInstance()
            }
            else -> {
                DrinkFavoriteFragment.newInstance()
            }
        }
    }

}