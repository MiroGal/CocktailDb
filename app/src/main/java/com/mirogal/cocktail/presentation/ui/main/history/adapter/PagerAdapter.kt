package com.mirogal.cocktail.presentation.ui.main.history.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mirogal.cocktail.presentation.ui.main.history.DrinkFavoriteFragment
import com.mirogal.cocktail.presentation.ui.main.history.DrinkHistoryFragment

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DrinkHistoryFragment.newInstance()
            else -> DrinkFavoriteFragment.newInstance()
        }
    }

}