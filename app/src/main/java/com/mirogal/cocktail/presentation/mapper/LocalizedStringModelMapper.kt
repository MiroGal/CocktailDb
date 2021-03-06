package com.mirogal.cocktail.presentation.mapper

import com.mirogal.cocktail.data.repository.model.LocalizedStringRepoModel
import com.mirogal.cocktail.presentation.mapper.base.BaseModelMapper
import com.mirogal.cocktail.presentation.model.cocktail.LocalizedStringModel

/**
 * You could sometimes create object instead of class in case there is no args
 */
class LocalizedStringModelMapper : BaseModelMapper<LocalizedStringModel, LocalizedStringRepoModel>() {

    override fun mapFrom(model: LocalizedStringModel) = with(model) {
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

    override fun mapTo(model: LocalizedStringRepoModel) = with(model) {
        LocalizedStringModel(
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