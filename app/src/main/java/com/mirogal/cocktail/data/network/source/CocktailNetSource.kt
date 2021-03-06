package com.mirogal.cocktail.data.network.source

import com.mirogal.cocktail.data.network.model.cocktail.CocktailNetModel
import com.mirogal.cocktail.data.network.source.base.BaseNetSource

interface CocktailNetSource : BaseNetSource {

    suspend fun searchCocktail(query: String): List<CocktailNetModel>

}