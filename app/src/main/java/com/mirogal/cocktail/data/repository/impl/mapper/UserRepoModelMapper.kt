package com.mirogal.cocktail.data.repository.impl.mapper

import com.mirogal.cocktail.data.db.model.UserDbModel
import com.mirogal.cocktail.data.network.model.UserNetModel
import com.mirogal.cocktail.data.repository.impl.mapper.base.BaseRepoModelMapper
import com.mirogal.cocktail.data.repository.model.UserRepoModel

class UserRepoModelMapper : BaseRepoModelMapper<UserRepoModel, UserDbModel, UserNetModel>() {

    override fun mapDbToRepo(db: UserDbModel): UserRepoModel = with(db) {
        UserRepoModel(
                id = id,
                name = name,
                lastName = lastName,
                avatar = avatar
        )
    }

    override fun mapRepoToDb(repo: UserRepoModel) = with(repo) {
        UserDbModel(
                id = id,
                name = name,
                lastName = lastName,
                avatar = avatar
        )
    }

    override fun mapNetToRepo(net: UserNetModel) = with(net) {
        UserRepoModel(
                id = id,
                name = name,
                lastName = lastName,
                avatar = avatar
        )
    }

    override fun mapNetToDb(net: UserNetModel) = with(net) {
        UserDbModel(
                id = id,
                name = name,
                lastName = lastName,
                avatar = avatar
        )
    }

}