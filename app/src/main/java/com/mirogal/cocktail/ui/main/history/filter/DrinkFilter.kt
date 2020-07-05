package com.mirogal.cocktail.ui.main.history.filter

import com.mirogal.cocktail.ui.main.history.filter.DrinkFilterType

interface DrinkFilter {

    val type: DrinkFilterType
    val key: String

}