package com.mirogal.cocktail.presentation.model.filter

enum class DrinkFilterAlcohol(override val type: DrinkFilterType, override val key: String): DrinkFilter {

    DISABLE(DrinkFilterType.ALCOHOL, "Disable"),
    ALCOHOLIC(DrinkFilterType.ALCOHOL, "Alcoholic"),
    NON_ALCOHOLIC(DrinkFilterType.ALCOHOL, "Non alcoholic"),
    OPTIONAL_Alcohol(DrinkFilterType.ALCOHOL, "Optional alcohol")

}
