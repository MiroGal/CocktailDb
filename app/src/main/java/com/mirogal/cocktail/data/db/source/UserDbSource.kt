package com.mirogal.cocktail.data.db.source

import androidx.lifecycle.LiveData
import com.mirogal.cocktail.data.db.model.UserDbModel
import com.mirogal.cocktail.data.db.source.base.BaseDbSource

interface UserDbSource : BaseDbSource {

    val userLiveData: LiveData<UserDbModel?>

    suspend fun getUser(): UserDbModel?
    suspend fun saveUser(user: UserDbModel)
    suspend fun hasUser(): Boolean
    suspend fun deleteUser()

}