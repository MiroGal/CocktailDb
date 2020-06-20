package com.mirogal.cocktail.ui.constant

import com.mirogal.cocktail.ui.savelist.DrinkFilter

enum class AlcoholDrinkFilter(override val type: DrinkFilterType, override val key: String): DrinkFilter {

    ALCOHOLIC(DrinkFilterType.ALCOHOL, "Alcoholic"),
    NON_ALCOHOLIC(DrinkFilterType.ALCOHOL, "Non_alcoholic"),
    OPTIONAL_ALCOHOL(DrinkFilterType.ALCOHOL, "Optional_alcohol")

}
