package com.mirogal.cocktail.data.network.model.cocktail

import java.util.*

data class CocktailNetModel(
        val id: Long = -1L,
        val names: LocalizedStringNetModel = LocalizedStringNetModel(),
        val category: String = "",
        val alcoholType: String = "",
        val glass: String = "",
        val image: String = "",
        val instructions: LocalizedStringNetModel = LocalizedStringNetModel(),
        val ingredientsWithMeasures: Map<String, String?> = emptyMap(),
        val date: Date = Date()
)