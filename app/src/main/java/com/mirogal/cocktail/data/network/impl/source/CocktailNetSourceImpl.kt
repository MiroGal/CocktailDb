package com.mirogal.cocktail.data.network.impl.source

import com.mirogal.cocktail.data.network.impl.service.CocktailApiService
import com.mirogal.cocktail.data.network.impl.source.base.BaseNetSourceImpl
import com.mirogal.cocktail.data.network.model.cocktail.CocktailNetModel
import com.mirogal.cocktail.data.network.source.CocktailNetSource

class CocktailNetSourceImpl(
        apiService: CocktailApiService
) : BaseNetSourceImpl<CocktailApiService>(apiService),
        CocktailNetSource {

    override suspend fun getCocktailList(name: String): List<CocktailNetModel> {
        return performRequest {
            getCocktailContainer(name).cocktailList
        }
    }

}