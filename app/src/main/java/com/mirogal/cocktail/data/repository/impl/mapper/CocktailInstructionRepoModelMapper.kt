package com.mirogal.cocktail.data.repository.impl.mapper

import com.mirogal.cocktail.data.db.model.cocktail.CocktailInstructionDbModel
import com.mirogal.cocktail.data.network.model.cocktail.LocalizedStringNetModel
import com.mirogal.cocktail.data.repository.impl.mapper.base.BaseRepoModelMapper
import com.mirogal.cocktail.data.repository.model.LocalizedStringRepoModel

class CocktailInstructionRepoModelMapper :
        BaseRepoModelMapper<LocalizedStringRepoModel, CocktailInstructionDbModel, LocalizedStringNetModel>() {

    override fun mapDbToRepo(dbCocktail: CocktailInstructionDbModel) = with(dbCocktail) {
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
        CocktailInstructionDbModel(
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