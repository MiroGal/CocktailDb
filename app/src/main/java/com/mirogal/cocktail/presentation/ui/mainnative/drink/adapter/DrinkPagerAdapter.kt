package com.mirogal.cocktail.presentation.ui.mainnative.drink.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mirogal.cocktail.presentation.ui.mainnative.drink.DrinkFavoriteFragment
import com.mirogal.cocktail.presentation.ui.mainnative.drink.DrinkHistoryFragment

class DrinkPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DrinkHistoryFragment.newInstance()
            else -> DrinkFavoriteFragment.newInstance()
        }
    }

}