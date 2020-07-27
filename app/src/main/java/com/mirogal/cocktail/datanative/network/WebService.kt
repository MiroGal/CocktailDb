package com.mirogal.cocktail.datanative.network

import com.mirogal.cocktail.datanative.network.model.ContainerNetModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("api/json/v1/1/search.php")
    fun loadCocktailList(@Query("s") name: String? = null): Call<ContainerNetModel?>?

    companion object Factory {
        fun create(): WebService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://www.thecocktaildb.com/")
                    .build()

            return retrofit.create(WebService::class.java)
        }
    }

}