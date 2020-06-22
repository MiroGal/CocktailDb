package com.mirogal.cocktail.ui.savelist.filter

enum class AlcoholDrinkFilter(override val type: DrinkFilterType, override val key: String): DrinkFilter {

    ALCOHOLIC(DrinkFilterType.ALCOHOL, "alcoholic"),
    NON_ALCOHOLIC(DrinkFilterType.ALCOHOL, "non_alcoholic"),
    OPTIONAL_ALCOHOL(DrinkFilterType.ALCOHOL, "optional_alcohol"),
    UNABLE(DrinkFilterType.ALCOHOL, "unable"),

}
