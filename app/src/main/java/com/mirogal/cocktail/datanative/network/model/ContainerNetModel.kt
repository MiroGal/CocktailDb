package com.mirogal.cocktail.datanative.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ContainerNetModel(@field:SerializedName("drinks") var cocktailList: List<CocktailNetModel>) : Serializable