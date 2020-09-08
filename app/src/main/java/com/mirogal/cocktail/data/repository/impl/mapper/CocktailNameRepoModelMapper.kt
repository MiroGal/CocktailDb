package com.mirogal.cocktail.data.repository.impl.mapper

import com.mirogal.cocktail.data.db.model.cocktail.CocktailNameDbModel
import com.mirogal.cocktail.data.network.model.cocktail.LocalizedStringNetModel
import com.mirogal.cocktail.data.repository.impl.mapper.base.BaseRepoModelMapper
import com.mirogal.cocktail.data.repository.model.LocalizedStringRepoModel

class CocktailNameRepoModelMapper :
        BaseRepoModelMapper<LocalizedStringRepoModel, CocktailNameDbModel, LocalizedStringNetModel>() {

    override fun mapDbToRepo(dbCocktail: CocktailNameDbModel) = with(dbCocktail) {
        LocalizedStringRepoModel(
                default = baseValue,
                defaultAlternate = baseValueAlternate,
                es = es,
                de = de,
                fr = fr,
                zhHans = zhHans,
                zhHant = zhHant
        )
    }

    override fun mapRepoToDb(repo: LocalizedStringRepoModel) = with(repo) {
        CocktailNameDbModel(
                baseValue = default,
                baseValueAlternate = defaultAlternate,
                es = es,
                de = de,
                fr = fr,
                zhHans = zhHans,
                zhHant = zhHant
        )
    }

}