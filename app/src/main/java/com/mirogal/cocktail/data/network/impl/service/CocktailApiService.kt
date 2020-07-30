package com.mirogal.cocktail.data.network.impl.service

import com.mirogal.cocktail.data.network.model.cocktail.CocktailContainerNetModel
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface CocktailApiService {

    @GET("api/json/v1/1/search.php")
    suspend fun getCocktailContainer(@Query("s") name: String? = null): CocktailContainerNetModel

}