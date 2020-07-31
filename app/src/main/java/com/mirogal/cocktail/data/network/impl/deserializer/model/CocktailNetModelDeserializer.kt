package com.mirogal.cocktail.data.network.impl.deserializer.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.mirogal.cocktail.data.network.impl.extension.getMemberStringOrEmpty
import com.mirogal.cocktail.data.network.model.cocktail.CocktailNetModel
import com.mirogal.cocktail.data.network.model.cocktail.LocalizedStringNetModel
import java.lang.reflect.Type
import java.util.*

class CocktailNetModelDeserializer : JsonDeserializer<CocktailNetModel> {

    @Throws(JsonParseException::class)
    override fun deserialize(
            json: JsonElement?, type: Type,
            context: JsonDeserializationContext
    ): CocktailNetModel {

        return json!!.asJsonObject!!.let { jsonObject ->

            CocktailNetModel(
                    id = jsonObject.get("idDrink").asLong,
                    names = LocalizedStringNetModel(
                            jsonObject.getMemberStringOrEmpty("strDrink")
                    ),
                    category = jsonObject.get("strCategory").asString,
                    alcoholType = jsonObject.get("strAlcoholic").asString,
                    glass = jsonObject.get("strGlass").asString,
                    image = jsonObject.get("strDrinkThumb").asString,
                    instructions = LocalizedStringNetModel(
                            jsonObject.getMemberStringOrEmpty("strInstructions")
                    ),
//                               ingredientsWithMeasures = jsonObject.get("ingredientsWithMeasures") Map<String, String?> = emptyMap(),
//                               date = jsonObject.get("dateModified") Date = Date()
                    ingredientsWithMeasures = emptyMap(),
                    date = Date()
            )
        }
    }

}