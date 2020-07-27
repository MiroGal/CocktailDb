package com.mirogal.cocktail.data.db.impl.source

import com.mirogal.cocktail.data.db.impl.dao.UserDao
import com.mirogal.cocktail.data.db.model.UserDbModel
import com.mirogal.cocktail.data.db.source.UserDbSource

class UserDbSourceImpl(
        private val userDao: UserDao
) : UserDbSource {

    override val userLiveData = userDao.userLiveData

    override suspend fun getUser(): UserDbModel? = getUser()

    override suspend fun saveUser(user: UserDbModel) = userDao.saveUser(user)

    override suspend fun hasUser() = getUser() != null

    override suspend fun deleteUser() = userDao.deleteUser()

}