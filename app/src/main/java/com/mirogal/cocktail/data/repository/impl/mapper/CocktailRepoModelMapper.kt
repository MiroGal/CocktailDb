package com.mirogal.cocktail.data.repository.impl.mapper

import com.mirogal.cocktail.data.db.model.CocktailDbModel
import com.mirogal.cocktail.data.repository.impl.mapper.base.BaseRepoModelMapper
import com.mirogal.cocktail.data.repository.model.CocktailRepoModel

class CocktailRepoModelMapper(
        private val localizedStringRepoModelMapper: LocalizedStringRepoModelMapper
) : BaseRepoModelMapper<CocktailRepoModel, CocktailDbModel, Any /*CocktailNetModel*/>() {

    override fun mapDbToRepo(db: CocktailDbModel): CocktailRepoModel = with(db) {
        CocktailRepoModel(
                id = id,
                names = names.run(localizedStringRepoModelMapper::mapDbToRepo),
                category = category,
                alcoholType = alcoholType,
                glass = glass,
                image = image,
                instructions = instructions.run(localizedStringRepoModelMapper::mapDbToRepo),
                ingredientsWithMeasures = ingredients.mapIndexed { index, ingredient -> ingredient to measures[index] }.toMap()
        )
    }

    override fun mapRepoToDb(repo: CocktailRepoModel): CocktailDbModel = with(repo) {
        CocktailDbModel(
                id = id,
                names = names.run(localizedStringRepoModelMapper::mapRepoToDb),
                category = category,
                alcoholType = alcoholType,
                glass = glass,
                image = image,
                instructions = instructions.run(localizedStringRepoModelMapper::mapRepoToDb),
                ingredients = ingredientsWithMeasures.keys.toList(),
                measures = ingredientsWithMeasures.values.toList()
        )
    }

}