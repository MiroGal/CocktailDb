package com.mirogal.cocktail.presentation.databinding.converter

import androidx.databinding.InverseMethod
import com.mirogal.cocktail.presentation.constant.DrinkPage

object PageBindingConverter {

    @InverseMethod("pageToInt")
    @JvmStatic
    fun intToPage(page: Int): DrinkPage {
        return DrinkPage.values()[page]
    }

    @JvmStatic
    fun pageToInt(page: DrinkPage): Int {
        return page.ordinal
    }

}