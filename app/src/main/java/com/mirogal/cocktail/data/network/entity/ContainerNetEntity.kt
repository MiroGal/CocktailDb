package com.mirogal.cocktail.data.network.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ContainerNetEntity(@field:SerializedName("drinks") var cocktailList: List<CocktailNetEntity>) : Serializable