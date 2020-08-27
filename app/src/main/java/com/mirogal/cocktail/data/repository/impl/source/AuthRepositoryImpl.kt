package com.mirogal.cocktail.data.repository.impl.source

import com.mirogal.cocktail.data.db.source.UserDbSource
import com.mirogal.cocktail.data.local.source.TokenLocalSource
import com.mirogal.cocktail.data.network.source.AuthNetSource
import com.mirogal.cocktail.data.network.source.UserNetSource
import com.mirogal.cocktail.data.repository.impl.mapper.UserRepoModelMapper
import com.mirogal.cocktail.data.repository.source.AuthRepository

class AuthRepositoryImpl(
        private val authNetSource: AuthNetSource,
        private val userNetSource: UserNetSource,
        private val userDbSource: UserDbSource,
        private val userModelMapper: UserRepoModelMapper,
        private val tokenLocalSource: TokenLocalSource
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): Boolean {
        return authNetSource.signIn(email, password)
                .let {
                    tokenLocalSource.token = it

                    //refresh user
                    userNetSource.getUser()
                            .run(userModelMapper::mapNetToDb)
                            .run { userDbSource.saveUser(this) }

                    tokenLocalSource.token != null
                }
    }

    override suspend fun signUp(
            name: String,
            lastName: String,
            email: String,
            password: String
    ): Boolean {
        return authNetSource
                .signUp(name, lastName, email, password)
                .let {
                    tokenLocalSource.token = it

                    //refresh user
                    userNetSource.getUser()
                            .run(userModelMapper::mapNetToDb)
                            .run { userDbSource.saveUser(this) }

                    tokenLocalSource.token != null
                }
    }

}