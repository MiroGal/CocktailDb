package com.mirogal.cocktail.data.network.impl.source

import com.mirogal.cocktail.data.network.impl.service.UserApiService
import com.mirogal.cocktail.data.network.impl.source.base.BaseNetSourceImpl
import com.mirogal.cocktail.data.network.model.UserNetModel
import com.mirogal.cocktail.data.network.source.UserNetSource
import java.io.File

class UserNetSourceImpl(
        apiService: UserApiService
) : BaseNetSourceImpl<UserApiService>(apiService),
        UserNetSource {

    override suspend fun getUser(): UserNetModel {
        return performRequest {
            getUser()
        }
    }

    override suspend fun updateUserLogo(avatar: File) {
        //TODO multipart
//        return performRequest {
//            getUser()
//        }
    }

    override suspend fun updateUser(user: UserNetModel) {
        return performRequest {
            updateUser(user)
        }
    }

}