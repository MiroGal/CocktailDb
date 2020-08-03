package com.mirogal.cocktail.data.repository.impl.mapper

import com.mirogal.cocktail.data.db.model.CocktailDbModel
import com.mirogal.cocktail.data.db.model.LocalizedStringDbModel
import com.mirogal.cocktail.data.network.model.cocktail.CocktailNetModel
import com.mirogal.cocktail.data.network.model.cocktail.LocalizedStringNetModel
import com.mirogal.cocktail.data.repository.impl.mapper.base.BaseRepoModelMapper
import com.mirogal.cocktail.data.repository.model.CocktailRepoModel
import com.mirogal.cocktail.data.repository.model.LocalizedStringRepoModel
import java.util.*

class CocktailRepoModelMapper(
        private val localizedStringRepoModelMapper: LocalizedStringRepoModelMapper
) : BaseRepoModelMapper<CocktailRepoModel, CocktailDbModel, CocktailNetModel>() {

    override fun mapDbToRepo(db: CocktailDbModel): CocktailRepoModel = with(db) {
        CocktailRepoModel(
                id = id,
                names = names?.run(localizedStringRepoModelMapper::mapDbToRepo) ?: LocalizedStringRepoModel(),
                category = category,
                alcoholType = alcoholType,
                glass = glass,
                image = image,
                instructions = instructions?.run(localizedStringRepoModelMapper::mapDbToRepo) ?: LocalizedStringRepoModel(),
                ingredientsWithMeasures = ingredients.mapIndexed { index, ingredient -> ingredient to measures[index] }.toMap()
        )
    }

    override fun mapRepoToDb(repo: CocktailRepoModel): CocktailDbModel = with(repo) {
        CocktailDbModel(
                id = id,
                names = names?.run(localizedStringRepoModelMapper::mapRepoToDb) ?: LocalizedStringDbModel(),
                category = category,
                alcoholType = alcoholType,
                glass = glass,
                image = image,
                instructions = instructions?.run(localizedStringRepoModelMapper::mapRepoToDb) ?: LocalizedStringDbModel(),
                ingredients = ingredientsWithMeasures.keys.toList(),
                measures = ingredientsWithMeasures.values.toList()
        )
    }

    override fun mapNetToRepo(net: CocktailNetModel): CocktailRepoModel = with(net) {
        CocktailRepoModel(
                id = id,
                names = names?.run(localizedStringRepoModelMapper::mapNetToRepo) ?: LocalizedStringRepoModel(),
                category = category,
                alcoholType = alcoholType,
                glass = glass,
                image = image,
                instructions = instructions?.run(localizedStringRepoModelMapper::mapNetToRepo) ?: LocalizedStringRepoModel(),
                ingredientsWithMeasures = emptyMap()
        )
    }

    override fun mapRepoToNet(repo: CocktailRepoModel): CocktailNetModel = with(repo) {
        CocktailNetModel(
                id = id,
                names = names?.run(localizedStringRepoModelMapper::mapRepoToNet) ?: LocalizedStringNetModel(),
                category = category,
                alcoholType = alcoholType,
                glass = glass,
                image = image,
                instructions = instructions?.run(localizedStringRepoModelMapper::mapRepoToNet) ?: LocalizedStringNetModel(),
                ingredientsWithMeasures = emptyMap(),
                date = Date()
        )
    }

}