package com.mirogal.cocktail.data.repository.mapper

import com.mirogal.cocktail.data.db.model.CocktailDbModel
import com.mirogal.cocktail.data.network.model.CocktailNetModel

object NetDbMapper {

    fun toDbEntity(netModel: CocktailNetModel): CocktailDbModel {
        return CocktailDbModel(
                netModel.id,
                netModel.name,
                false,
                netModel.category,
                netModel.alcoholic,
                netModel.glass,
                netModel.instruction,
                netModel.imagePath,
                netModel.ingredient1,
                netModel.ingredient2,
                netModel.ingredient3,
                netModel.ingredient4,
                netModel.ingredient5,
                netModel.ingredient6,
                netModel.ingredient7,
                netModel.ingredient8,
                netModel.ingredient9,
                netModel.ingredient10,
                netModel.ingredient11,
                netModel.ingredient12,
                netModel.ingredient13,
                netModel.ingredient14,
                netModel.ingredient15,
                netModel.measure1,
                netModel.measure2,
                netModel.measure3,
                netModel.measure4,
                netModel.measure5,
                netModel.measure6,
                netModel.measure7,
                netModel.measure8,
                netModel.measure9,
                netModel.measure10,
                netModel.measure11,
                netModel.measure12,
                netModel.measure13,
                netModel.measure14,
                netModel.measure15
        )
    }

}