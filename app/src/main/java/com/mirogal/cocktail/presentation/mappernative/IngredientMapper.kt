package com.mirogal.cocktail.presentation.mappernative

import com.mirogal.cocktail.presentation.modelnative.detail.IngredientModel
import java.util.*

object IngredientMapper {

    @JvmStatic
    fun toIngredientList(ingredientList: Array<String?>, measureList: Array<String?>): List<IngredientModel> {
        val ingredientModelList: MutableList<IngredientModel> = ArrayList()
        var position = 1
        for (i in ingredientList.indices) {
            if (ingredientList[i] != null) {
                ingredientModelList.add(IngredientModel(position, ingredientList[i], measureList[i]))
                position++
            }
        }
        return ingredientModelList
    }

}