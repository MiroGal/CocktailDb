package com.mirogal.cocktail.presentation.ui.detail.ingredientlist;

import com.mirogal.cocktail.presentation.ui.detail.ingredientlist.entity.IngredientEntity;

import java.util.ArrayList;
import java.util.List;

public class IngredientMapper {

    public static List<IngredientEntity> toIngredientList(String[] ingredientList, String[] measureList) {

        List<IngredientEntity> ingredientEntityList = new ArrayList<>();

        int position = 1;
        for (int i = 0; i < ingredientList.length; i++) {
            if (ingredientList[i] != null) {
                ingredientEntityList.add(new IngredientEntity(position, ingredientList[i], measureList[i]));
                position++;
            }
        }

        return ingredientEntityList;
    }
}
