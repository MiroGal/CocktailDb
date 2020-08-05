package com.mirogal.cocktail.data.network.impl.service

import com.mirogal.cocktail.data.network.model.response.CocktailListResponseNetModel
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface CocktailApiService {

    @GET("search.php")
//    suspend fun searchCocktail(@Query("s") query: String): List<CocktailNetModel>
    suspend fun searchCocktail(@Query("s") query: String): CocktailListResponseNetModel

}