package com.mirogal.cocktail.ui.savelist

import com.mirogal.cocktail.ui.constant.DrinkFilterType

interface DrinkFilter {

    val type: DrinkFilterType
    val key: String

}