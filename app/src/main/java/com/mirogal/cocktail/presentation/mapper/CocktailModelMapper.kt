package com.mirogal.cocktail.presentation.mapper

import com.mirogal.cocktail.data.repository.model.CocktailRepoModel
import com.mirogal.cocktail.presentation.mapper.base.BaseModelMapper
import com.mirogal.cocktail.presentation.model.cocktail.*

class CocktailModelMapper(
        private val localizedStringModelMapper: LocalizedStringModelMapper
) : BaseModelMapper<CocktailModel, CocktailRepoModel>() {

    override fun mapFrom(model: CocktailModel) = with(model) {
        CocktailRepoModel(
                id = id,
                names = names.run(localizedStringModelMapper::mapFrom),
                category = category.key,
                alcoholType = alcoholType.key,
                glass = glass.key,
                image = image,
                instructions = instructions.run(localizedStringModelMapper::mapFrom),
                ingredientsWithMeasures = ingredientsWithMeasures.mapKeys { it.key.key }/*,
                date = date*/
        )
    }

    override fun mapTo(model: CocktailRepoModel)= with(model) {
        CocktailModel(
                id = id,
                names = names.run(localizedStringModelMapper::mapTo),
                category = CocktailCategory.values().firstOrNull { it.key == category } ?: CocktailCategory.UNDEFINED,
                alcoholType = CocktailAlcoholType.values().firstOrNull { it.key == alcoholType } ?: CocktailAlcoholType.UNDEFINED,
                glass = CocktailGlass.values().firstOrNull { it.key == glass } ?: CocktailGlass.UNDEFINED,
                image = image,
                instructions = instructions.run(localizedStringModelMapper::mapTo),
                ingredientsWithMeasures = ingredientsWithMeasures.mapKeys {
                    keyValue -> CocktailIngredient.values().firstOrNull { it.key == keyValue.key } ?: CocktailIngredient.UNDEFINED
                }/*,
                date = date*/
        )
    }

}