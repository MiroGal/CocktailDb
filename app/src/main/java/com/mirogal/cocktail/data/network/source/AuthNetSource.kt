package com.mirogal.cocktail.data.network.source

import com.mirogal.cocktail.data.network.source.base.BaseNetSource

interface AuthNetSource : BaseNetSource {

    /**
     * @return login token
     */
    suspend fun signIn(email: String, password: String): String

    suspend fun signUp(name: String, lastName: String, email: String, password: String): String

}