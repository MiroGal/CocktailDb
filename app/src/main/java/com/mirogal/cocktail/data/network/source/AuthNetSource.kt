package com.mirogal.cocktail.data.network.source

import com.mirogal.cocktail.data.network.source.base.BaseNetSource

interface AuthNetSource : BaseNetSource {

    /**
     * @return login token
     */
    suspend fun signIn(email: String, password: String): String

}