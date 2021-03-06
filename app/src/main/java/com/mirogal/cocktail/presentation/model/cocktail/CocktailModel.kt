package com.mirogal.cocktail.presentation.model.cocktail

import java.util.*

data class CocktailModel(
        val id: Long = -1L,
        val names: LocalizedStringModel = LocalizedStringModel(),
        val category: CocktailCategory = CocktailCategory.UNDEFINED,
        val alcoholType: CocktailAlcoholType = CocktailAlcoholType.UNDEFINED,
        val glass: CocktailGlass = CocktailGlass.UNDEFINED,
        val image: String = "",
        val instructions: LocalizedStringModel = LocalizedStringModel(),
        val ingredientsWithMeasures: Map<String, String?> = emptyMap(),
        val isFavorite: Boolean,
        val date: Date? = null
)