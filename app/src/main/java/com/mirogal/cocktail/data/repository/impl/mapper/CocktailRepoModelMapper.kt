package com.mirogal.cocktail.data.repository.impl.mapper

import android.util.Log
import com.mirogal.cocktail.data.db.model.CocktailDbModel
import com.mirogal.cocktail.data.network.model.cocktail.CocktailNetModel
import com.mirogal.cocktail.data.repository.impl.mapper.base.BaseRepoModelMapper
import com.mirogal.cocktail.data.repository.model.CocktailRepoModel
import com.mirogal.cocktail.data.repository.model.LocalizedStringRepoModel

class CocktailRepoModelMapper(
        private val localizedStringRepoModelMapper: LocalizedStringRepoModelMapper
) : BaseRepoModelMapper<CocktailRepoModel, CocktailDbModel, CocktailNetModel>() {

    override fun mapDbToRepo(db: CocktailDbModel): CocktailRepoModel = with(db) {
        Log.d("<--", "class: ${db.names}")
        CocktailRepoModel(
                id = id,
                names = names?.run(localizedStringRepoModelMapper::mapDbToRepo) ?: LocalizedStringRepoModel(),
                category = category,
                alcoholType = alcoholType,
                glass = glass,
                image = image,
                instructions = instructions?.run(localizedStringRepoModelMapper::mapDbToRepo) ?: LocalizedStringRepoModel(),
                ingredientsWithMeasures = ingredients.mapIndexed { index, ingredient -> ingredient to measures[index] }.toMap(),
                isFavorite = isFavorite/*,
                date = date*/
        )
    }

    override fun mapRepoToDb(repo: CocktailRepoModel): CocktailDbModel = with(repo) {
        Log.d("-->", "class: ${repo.names}")
        CocktailDbModel(
                id = id,
                names = names.run(localizedStringRepoModelMapper::mapRepoToDb),
                category = category,
                alcoholType = alcoholType,
                glass = glass,
                image = image,
                instructions = instructions.run(localizedStringRepoModelMapper::mapRepoToDb),
                ingredients = ingredientsWithMeasures.keys.toList(),
                measures = ingredientsWithMeasures.values.toList(),
                isFavorite = isFavorite/*,
                date = date*/
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