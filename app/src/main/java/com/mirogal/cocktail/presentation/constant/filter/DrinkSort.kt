package com.mirogal.cocktail.presentation.constant.filter

enum class DrinkSort(val key: String) {

    DISABLE("Recent"),
    NAME_ASCENDING("Name (ascending)"),
    NAME_DESCENDING("Name (descending)"),
    ALCOHOL_FIRST("Alcohol first"),
    NON_ALCOHOL_FIRST("Non alcohol first"),
    INGREDIENT_COUNT_ASCENDING("Ingredient Count (ascending)"),
    INGREDIENT_COUNT_DESCENDING("Ingredient Count (descending)")

}
