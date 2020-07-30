package com.mirogal.cocktail.data.network.impl.deserializer.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.mirogal.cocktail.data.network.impl.extension.getMemberStringOrEmpty
import com.mirogal.cocktail.data.network.model.cocktail.CocktailContainerNetModel
import com.mirogal.cocktail.data.network.model.cocktail.CocktailNetModel
import com.mirogal.cocktail.data.network.model.cocktail.LocalizedStringNetModel
import java.lang.reflect.Type
import java.util.*

class CocktailNetModelDeserializer : JsonDeserializer<CocktailContainerNetModel> {

    @Throws(JsonParseException::class)
    override fun deserialize(
            json: JsonElement?, type: Type,
            context: JsonDeserializationContext
    ): CocktailContainerNetModel {

        return json!!.asJsonObject!!.let { jsonObject ->
            val drinksJsonArray = jsonObject.getAsJsonArray("drinks")
            val list = mutableListOf<CocktailNetModel>()

            drinksJsonArray.forEach {
                val drinkJsonObject = it.asJsonObject

                list.add(
                        CocktailNetModel(
                                id = drinkJsonObject.get("idDrink").asLong,
                                names = LocalizedStringNetModel(
                                        drinkJsonObject.getMemberStringOrEmpty("strDrink")
                                ),
                                category = drinkJsonObject.get("strCategory").asString,
                                alcoholType = drinkJsonObject.get("strAlcoholic").asString,
                                glass = drinkJsonObject.get("strGlass").asString,
                                image = drinkJsonObject.get("strDrinkThumb").asString,
                                instructions = LocalizedStringNetModel(
                                        drinkJsonObject.getMemberStringOrEmpty("strInstructions")
                                ),
//                                ingredientsWithMeasures = drinkJsonObject.get("ingredientsWithMeasures") Map<String, String?> = emptyMap(),
//                                date = drinkJsonObject.get("dateModified") Date = Date()
                                ingredientsWithMeasures = emptyMap(),
                                date = Date()
                        )
                )
//                list.add(context.deserialize(it, deserializeType<CocktailNetModel>()))
            }

            CocktailContainerNetModel(
                    cocktailList = list
            )
        }
    }

}