package com.mirogal.cocktail.presentation.mapper

import com.mirogal.cocktail.data.repository.model.UserRepoModel
import com.mirogal.cocktail.presentation.mapper.base.BaseModelMapper
import com.mirogal.cocktail.presentation.model.UserModel

class UserModelMapper : BaseModelMapper<UserModel, UserRepoModel>() {

    override fun mapFrom(model: UserModel) = with(model) {
        UserRepoModel(
                id = id,
                name = name,
                lastName = lastName,
                avatar = avatar
        )
    }

    override fun mapTo(model: UserRepoModel)= with(model) {
        UserModel(
                id = id,
                name = name,
                lastName = lastName,
                avatar = avatar
        )
    }

}