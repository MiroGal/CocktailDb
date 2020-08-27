package com.mirogal.cocktail.data.repository.impl.mapper

import com.mirogal.cocktail.data.db.model.LocalizedStringDbModel
import com.mirogal.cocktail.data.network.model.cocktail.LocalizedStringNetModel
import com.mirogal.cocktail.data.repository.impl.mapper.base.BaseRepoModelMapper
import com.mirogal.cocktail.data.repository.model.LocalizedStringRepoModel

class LocalizedStringRepoModelMapper :
        BaseRepoModelMapper<LocalizedStringRepoModel, LocalizedStringDbModel, LocalizedStringNetModel>() {

    override fun mapDbToRepo(db: LocalizedStringDbModel) = with(db) {
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
        LocalizedStringDbModel(
                baseValue = default,
                baseValueAlternate = defaultAlternate,
                es = es,
                de = de,
                fr = fr,
                zhHans = zhHans,
                zhHant = zhHant
        )
    }

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