package com.mirogal.cocktail.data.network.impl.deserializer.model

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.mirogal.cocktail.data.network.impl.extension.deserializeType
import com.mirogal.cocktail.data.network.model.cocktail.CocktailContainerNetModel
import com.mirogal.cocktail.data.network.model.cocktail.CocktailNetModel
import java.lang.reflect.Type

class CocktailContainerNetModelDeserializer : JsonDeserializer<CocktailContainerNetModel> {

    @Throws(JsonParseException::class)
    override fun deserialize(
            json: JsonElement?, type: Type,
            context: JsonDeserializationContext
    ): CocktailContainerNetModel {
        val drinksJsonObject: JsonElement? = json!!.asJsonObject!!.get("drinks")
        return if (drinksJsonObject!!.isJsonNull) {
            CocktailContainerNetModel()
        } else {
            val drinksJsonArray = drinksJsonObject.asJsonArray
            val list = mutableListOf<CocktailNetModel>()

            drinksJsonArray.forEach {
                list.add(context.deserialize(it, deserializeType<CocktailNetModel>()))
            }

            CocktailContainerNetModel(
                    cocktailList = list
            )
        }
    }

}