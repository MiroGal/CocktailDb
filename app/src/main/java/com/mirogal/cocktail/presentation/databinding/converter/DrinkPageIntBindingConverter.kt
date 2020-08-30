package com.mirogal.cocktail.presentation.databinding.converter

import androidx.databinding.InverseMethod
import androidx.viewpager2.widget.ViewPager2
import com.mirogal.cocktail.presentation.constant.DrinkPage

object DrinkPageIntBindingConverter {

    @InverseMethod("intToPage")
    @JvmStatic
    fun intToPage(view: ViewPager2, intPage: Int): DrinkPage {
        return DrinkPage.values()[intPage]
    }

    @JvmStatic
    fun pageToInt(view: ViewPager2, page: DrinkPage): Int {
        return page.ordinal
    }

}