package com.mirogal.cocktail.data.db.model.cocktail

import androidx.room.Embedded
import androidx.room.Relation

class CocktailDbModel(

        @Embedded()
        val cocktailInfo: CocktailInfoDbModel = CocktailInfoDbModel(),

        @Relation(parentColumn = "id", entity = CocktailNameDbModel::class, entityColumn = "cocktail_id")
        val cocktailNames: List<CocktailNameDbModel> = arrayListOf(),

        @Relation(parentColumn = "id", entity = CocktailInstructionDbModel::class, entityColumn = "cocktail_id")
        val cocktailInstructions: List<CocktailInstructionDbModel> = arrayListOf()

)