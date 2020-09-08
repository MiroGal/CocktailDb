package com.mirogal.cocktail.data.repository.impl.mapper

import com.mirogal.cocktail.data.db.model.cocktail.CocktailInfoDbModel
import com.mirogal.cocktail.data.db.model.cocktail.CocktailDbModel
import com.mirogal.cocktail.data.network.model.cocktail.CocktailNetModel
import com.mirogal.cocktail.data.repository.impl.mapper.base.BaseRepoModelMapper
import com.mirogal.cocktail.data.repository.model.CocktailRepoModel
import com.mirogal.cocktail.data.repository.model.LocalizedStringRepoModel

class CocktailRepoModelMapper(
        private val localizedStringRepoModelMapper: LocalizedStringRepoModelMapper,
        private val cocktailNameRepoModelMapper: CocktailNameRepoModelMapper,
        private val cocktailInstructionRepoModelMapper: CocktailInstructionRepoModelMapper
) : BaseRepoModelMapper<CocktailRepoModel, CocktailDbModel, CocktailNetModel>() {

    override fun mapDbToRepo(db: CocktailDbModel): CocktailRepoModel = with(db) {
        CocktailRepoModel(
                id = cocktailInfo.id,
                names = LocalizedStringRepoModel(),
//                names = names?.run(cocktailNameRepoModelMapper::mapDbToRepo) ?: LocalizedStringRepoModel(),
                category = cocktailInfo.category,
                alcoholType = cocktailInfo.alcoholType,
                glass = cocktailInfo.glass,
                image = cocktailInfo.image,
                instructions = LocalizedStringRepoModel(),
//                instructions = instructions?.run(cocktailInstructionRepoModelMapper::mapDbToRepo) ?: LocalizedStringRepoModel(),
                ingredientsWithMeasures = cocktailInfo.ingredients.mapIndexed { index, ingredient -> ingredient to cocktailInfo.measures[index] }.toMap(),
                isFavorite = cocktailInfo.isFavorite/*,
                date = date*/
        )
    }

    override fun mapRepoToDb(repo: CocktailRepoModel): CocktailDbModel = with(repo) {
        CocktailDbModel(
                cocktailInfo = CocktailInfoDbModel(
                        id = id,
                        category = category,
                        alcoholType = alcoholType,
                        glass = glass,
                        image = image,
                        ingredients = ingredientsWithMeasures.keys.toList(),
                        measures = ingredientsWithMeasures.values.toList(),
                        isFavorite = isFavorite/*,
                        date = date*/
                ),
                cocktailNames = arrayListOf(),
//                names = names.run(cocktailNameRepoModelMapper::mapRepoToDb),
                cocktailInstructions = arrayListOf()
//                instructions = instructions.run(cocktailInstructionRepoModelMapper::mapRepoToDb)
        )
    }

    override fun mapNetToRepo(net: CocktailNetModel): CocktailRepoModel  = with(net) {
        CocktailRepoModel(
                id = id,
                names = names.run(localizedStringRepoModelMapper::mapNetToRepo),
                category = category,
                alcoholType = alcoholType,
                glass = glass,
                image = image,
                instructions = instructions.run(localizedStringRepoModelMapper::mapNetToRepo),
                ingredientsWithMeasures = ingredientsWithMeasures,
                isFavorite = false/*,
                date = date*/
        )
    }

}