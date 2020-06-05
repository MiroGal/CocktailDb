package com.mirogal.cocktail.data.repository;

import androidx.annotation.NonNull;

import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;
import com.mirogal.cocktail.data.network.entity.CocktailNetEntity;

public class NetDbMapper {

    public static CocktailDbEntity toDbEntity(@NonNull CocktailNetEntity netEntity) {
        return new CocktailDbEntity(
                netEntity.getId(),
                netEntity.getName(),
                netEntity.getAlcoholic(),
                netEntity.getGlass(),
                netEntity.getInstruction(),
                netEntity.getImagePath(),

                netEntity.getIngredient1(),
                netEntity.getIngredient2(),
                netEntity.getIngredient3(),
                netEntity.getIngredient4(),
                netEntity.getIngredient5(),
                netEntity.getIngredient6(),
                netEntity.getIngredient7(),
                netEntity.getIngredient8(),
                netEntity.getIngredient9(),
                netEntity.getIngredient10(),
                netEntity.getIngredient11(),
                netEntity.getIngredient12(),
                netEntity.getIngredient13(),
                netEntity.getIngredient14(),
                netEntity.getIngredient15(),

                netEntity.getMeasure1(),
                netEntity.getMeasure2(),
                netEntity.getMeasure3(),
                netEntity.getMeasure4(),
                netEntity.getMeasure5(),
                netEntity.getMeasure6(),
                netEntity.getMeasure7(),
                netEntity.getMeasure8(),
                netEntity.getMeasure9(),
                netEntity.getMeasure10(),
                netEntity.getMeasure11(),
                netEntity.getMeasure12(),
                netEntity.getMeasure13(),
                netEntity.getMeasure14(),
                netEntity.getMeasure15()
        );
    }
}
