package com.mirogal.cocktail.data.network.impl.source

import com.google.gson.JsonObject
import com.mirogal.cocktail.data.network.impl.service.AuthApiService
import com.mirogal.cocktail.data.network.impl.source.base.BaseNetSourceImpl
import com.mirogal.cocktail.data.network.source.AuthNetSource

class AuthNetSourceImpl(
        apiService: AuthApiService
) : BaseNetSourceImpl<AuthApiService>(apiService),
        AuthNetSource {

    override suspend fun signIn(email: String, password: String): String {
        return performRequest {
            signIn(
                    JsonObject().apply {
                        addProperty("email", email)
                        addProperty("password", password)
                    }
            ).token
        }
    }

}