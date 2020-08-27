package com.mirogal.cocktail.data.repository.model

class CocktailRepoModel(
        val id: Long = -1L,
        val names: LocalizedStringRepoModel = LocalizedStringRepoModel(),
        val category: String = "",
        val alcoholType: String = "",
        val glass: String = "",
        val image: String = "",
        val instructions: LocalizedStringRepoModel = LocalizedStringRepoModel(),
        val ingredientsWithMeasures: Map<String, String?> = emptyMap(),
        val isFavorite: Boolean/*,
        val date: Date = Date()*/
)