package com.mirogal.cocktail.data.repository

import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.network.entity.CocktailNetEntity

object NetDbMapper {

    fun toDbEntity(netEntity: CocktailNetEntity): CocktailDbEntity {
        return CocktailDbEntity(
                netEntity.id,
                netEntity.name,
                netEntity.alcoholic,
                netEntity.glass,
                netEntity.instruction,
                netEntity.imagePath,
                netEntity.ingredient1,
                netEntity.ingredient2,
                netEntity.ingredient3,
                netEntity.ingredient4,
                netEntity.ingredient5,
                netEntity.ingredient6,
                netEntity.ingredient7,
                netEntity.ingredient8,
                netEntity.ingredient9,
                netEntity.ingredient10,
                netEntity.ingredient11,
                netEntity.ingredient12,
                netEntity.ingredient13,
                netEntity.ingredient14,
                netEntity.ingredient15,
                netEntity.measure1,
                netEntity.measure2,
                netEntity.measure3,
                netEntity.measure4,
                netEntity.measure5,
                netEntity.measure6,
                netEntity.measure7,
                netEntity.measure8,
                netEntity.measure9,
                netEntity.measure10,
                netEntity.measure11,
                netEntity.measure12,
                netEntity.measure13,
                netEntity.measure14,
                netEntity.measure15
        )
    }

}