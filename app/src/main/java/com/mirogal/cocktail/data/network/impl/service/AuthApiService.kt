package com.mirogal.cocktail.data.network.impl.service

import com.google.gson.JsonObject
import com.mirogal.cocktail.data.network.model.response.TokenNetModel
import retrofit2.http.Body
import retrofit2.http.POST

@JvmSuppressWildcards
interface AuthApiService {

    @POST("login")
    suspend fun signIn(@Body jsonObject: JsonObject): TokenNetModel

}