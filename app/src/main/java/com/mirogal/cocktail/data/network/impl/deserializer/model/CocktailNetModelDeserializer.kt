package com.mirogal.cocktail.data.network.impl.deserializer.model

import com.google.gson.*
import com.mirogal.cocktail.data.network.impl.extension.deserializeType
import com.mirogal.cocktail.data.network.impl.extension.getMemberStringOrEmpty
import com.mirogal.cocktail.data.network.model.cocktail.CocktailNetModel
import com.mirogal.cocktail.data.network.model.cocktail.LocalizedStringNetModel
import java.lang.reflect.Type
import java.util.*

class CocktailNetModelDeserializer : JsonDeserializer<CocktailNetModel> {

    private val dateType = deserializeType<Date>()

    @Throws(JsonParseException::class)
    override fun deserialize(
            json: JsonElement?,
            typeOfT: Type,
            context: JsonDeserializationContext
    ): CocktailNetModel {
        return json!!.asJsonObject!!.let { jsonObject ->
            CocktailNetModel(
                    id = jsonObject.get("idDrink").asLong,
                    names = LocalizedStringNetModel(
                            default = jsonObject.getMemberStringOrEmpty("strDrink"),
                            defaultAlternate = jsonObject.getMemberStringOrEmpty("strDrinkAlternate"),
                            es = jsonObject.getMemberStringOrEmpty("strDrinkES"),
                            de = jsonObject.getMemberStringOrEmpty("strDrinkDE"),
                            fr = jsonObject.getMemberStringOrEmpty("strDrinkFR"),
                            zhHans = jsonObject.getMemberStringOrEmpty("strDrinkZH-HANS"),
                            zhHant = jsonObject.getMemberStringOrEmpty("strDrinkZH-HANT")
                    ),
                    category = jsonObject.getMemberStringOrEmpty("strCategory"),
                    alcoholType = jsonObject.getMemberStringOrEmpty("strAlcoholic"),
                    glass = jsonObject.getMemberStringOrEmpty("strGlass"),
                    image = jsonObject.getMemberStringOrEmpty("strDrinkThumb"),
                    instructions = LocalizedStringNetModel(
                            default = jsonObject.getMemberStringOrEmpty("strInstructions"),
                            defaultAlternate = jsonObject.getMemberStringOrEmpty("strInstructionsAlternate"),
                            es = jsonObject.getMemberStringOrEmpty("strInstructionsES"),
                            de = jsonObject.getMemberStringOrEmpty("strInstructionsDE"),
                            fr = jsonObject.getMemberStringOrEmpty("strInstructionsFR"),
                            zhHans = jsonObject.getMemberStringOrEmpty("strInstructionsZH-HANS"),
                            zhHant = jsonObject.getMemberStringOrEmpty("strInstructionsZH-HANT")
                    ),
                    ingredientsWithMeasures = HashMap<String, String?>().apply {
                        (1..15).forEach { index ->
                            tryAddIngredientWithMeasure(jsonObject, index)
                        }
                    },
                    date = when {
                        jsonObject.has("dateModified") && !jsonObject.get("dateModified").isJsonNull -> {
                            context.deserialize(jsonObject.get("dateModified"), dateType)
                        }
                        else -> Date()
                    }
            )
        }
    }

    private fun HashMap<String, String?>.tryAddIngredientWithMeasure(
            drinkJsonObject: JsonObject,
            index: Int
    ) {
        drinkJsonObject
                .getMemberStringOrEmpty("strIngredient$index")
                .takeIf { it.isNotEmpty() }
                ?.apply { put(this, drinkJsonObject.getMemberStringOrEmpty("strMeasure$index")) }
    }

}