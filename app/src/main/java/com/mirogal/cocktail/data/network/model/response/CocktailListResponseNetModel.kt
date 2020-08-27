package com.mirogal.cocktail.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.mirogal.cocktail.data.network.model.cocktail.CocktailNetModel

class CocktailListResponseNetModel(
        @SerializedName("drinks")
        val drinks: List<CocktailNetModel>?
)