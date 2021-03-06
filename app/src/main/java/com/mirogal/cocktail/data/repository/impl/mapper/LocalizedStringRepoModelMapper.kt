package com.mirogal.cocktail.data.repository.impl.mapper

import com.mirogal.cocktail.data.db.model.cocktail.CocktailNameDbModel
import com.mirogal.cocktail.data.network.model.cocktail.LocalizedStringNetModel
import com.mirogal.cocktail.data.repository.impl.mapper.base.BaseRepoModelMapper
import com.mirogal.cocktail.data.repository.model.LocalizedStringRepoModel

class LocalizedStringRepoModelMapper :
        BaseRepoModelMapper<LocalizedStringRepoModel, CocktailNameDbModel, LocalizedStringNetModel>() {

    override fun mapNetToRepo(net: LocalizedStringNetModel): LocalizedStringRepoModel = with(net) {
        LocalizedStringRepoModel(
                default = default,
                defaultAlternate = defaultAlternate,
                es = es,
                de = de,
                fr = fr,
                zhHans = zhHans,
                zhHant = zhHant
        )
    }

}