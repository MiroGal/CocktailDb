package com.mirogal.cocktail.ui.main.history.constant

enum class AlcoholDrinkFilter(override val type: DrinkFilterType, override val key: String): DrinkFilter {

    ALCOHOLIC(DrinkFilterType.ALCOHOL, "Alcoholic"),
    NON_ALCOHOLIC(DrinkFilterType.ALCOHOL, "Non alcoholic"),
    OPTIONAL_ALCOHOL(DrinkFilterType.ALCOHOL, "Optional alcohol"),
    DISABLE(DrinkFilterType.ALCOHOL, "Disable")

}
