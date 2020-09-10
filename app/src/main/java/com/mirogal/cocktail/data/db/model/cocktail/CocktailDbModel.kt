package com.mirogal.cocktail.data.db.model.cocktail

import androidx.room.Embedded
import androidx.room.Relation

class CocktailDbModel(

        @Embedded
        val cocktailInfo: CocktailInfoDbModel = CocktailInfoDbModel(),

        @Relation(parentColumn = "id", entity = CocktailNameDbModel::class, entityColumn = "id")
        val cocktailNames: CocktailNameDbModel? = null,

        @Relation(parentColumn = "id", entity = CocktailInstructionDbModel::class, entityColumn = "id")
        val cocktailInstructions: CocktailInstructionDbModel? = null

)