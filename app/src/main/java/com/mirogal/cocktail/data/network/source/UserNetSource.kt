package com.mirogal.cocktail.data.network.source

import com.mirogal.cocktail.data.network.model.UserNetModel
import com.mirogal.cocktail.data.network.source.base.BaseNetSource
import java.io.File

interface UserNetSource : BaseNetSource {

    suspend fun getUser(): UserNetModel
    suspend fun updateUser(user: UserNetModel)
    suspend fun updateUserLogo(avatar: File)

}