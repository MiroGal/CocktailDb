package com.mirogal.cocktail.presentation.mapper

import com.mirogal.cocktail.presentation.model.detail.IngredientEntity
import java.util.*

object IngredientMapper {

    @JvmStatic
    fun toIngredientList(ingredientList: Array<String?>, measureList: Array<String?>): List<IngredientEntity> {
        val ingredientEntityList: MutableList<IngredientEntity> = ArrayList()
        var position = 1
        for (i in ingredientList.indices) {
            if (ingredientList[i] != null) {
                ingredientEntityList.add(IngredientEntity(position, ingredientList[i], measureList[i]))
                position++
            }
        }
        return ingredientEntityList
    }

}