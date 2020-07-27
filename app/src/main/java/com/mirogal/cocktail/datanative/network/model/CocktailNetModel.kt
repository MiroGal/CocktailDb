package com.mirogal.cocktail.datanative.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CocktailNetModel : Serializable {

    @SerializedName("idDrink")
    var id = 0

    @SerializedName("strDrink")
    var name: String? = null

    @SerializedName("strDrinkAlternate")
    var nameAlternate: String? = null

    @SerializedName("strDrinkES")
    var nameEs: String? = null

    @SerializedName("strDrinkDE")
    var nameDe: String? = null

    @SerializedName("strDrinkFR")
    var nameFr: String? = null

    @SerializedName("strDrinkZH-HANS")
    var nameZhHans: String? = null

    @SerializedName("strDrinkZH-HANT")
    var nameZhHant: String? = null

    @SerializedName("strTags")
    var tags: String? = null

    @SerializedName("strVideo")
    var video: String? = null

    @SerializedName("strCategory")
    var category: String? = null

    @SerializedName("strIBA")
    var iba: String? = null

    @SerializedName("strAlcoholic")
    var alcoholic: String? = null

    @SerializedName("strGlass")
    var glass: String? = null

    @SerializedName("strInstructions")
    var instruction: String? = null

    @SerializedName("strInstructionsES")
    var instructionEs: String? = null

    @SerializedName("strInstructionsDE")
    var instructionDe: String? = null

    @SerializedName("strInstructionsFR")
    var instructionFr: String? = null

    @SerializedName("strInstructionsZH-HANS")
    var instructionZhHans: String? = null

    @SerializedName("strInstructionsZH-HANT")
    var instructionZhHant: String? = null

    @SerializedName("strDrinkThumb")
    var imagePath: String? = null

    @SerializedName("strIngredient1")
    var ingredient1: String? = null

    @SerializedName("strIngredient2")
    var ingredient2: String? = null

    @SerializedName("strIngredient3")
    var ingredient3: String? = null

    @SerializedName("strIngredient4")
    var ingredient4: String? = null

    @SerializedName("strIngredient5")
    var ingredient5: String? = null

    @SerializedName("strIngredient6")
    var ingredient6: String? = null

    @SerializedName("strIngredient7")
    var ingredient7: String? = null

    @SerializedName("strIngredient8")
    var ingredient8: String? = null

    @SerializedName("strIngredient9")
    var ingredient9: String? = null

    @SerializedName("strIngredient10")
    var ingredient10: String? = null

    @SerializedName("strIngredient11")
    var ingredient11: String? = null

    @SerializedName("strIngredient12")
    var ingredient12: String? = null

    @SerializedName("strIngredient13")
    var ingredient13: String? = null

    @SerializedName("strIngredient14")
    var ingredient14: String? = null

    @SerializedName("strIngredient15")
    var ingredient15: String? = null

    @SerializedName("strMeasure1")
    var measure1: String? = null

    @SerializedName("strMeasure2")
    var measure2: String? = null

    @SerializedName("strMeasure3")
    var measure3: String? = null

    @SerializedName("strMeasure4")
    var measure4: String? = null

    @SerializedName("strMeasure5")
    var measure5: String? = null

    @SerializedName("strMeasure6")
    var measure6: String? = null

    @SerializedName("strMeasure7")
    var measure7: String? = null

    @SerializedName("strMeasure8")
    var measure8: String? = null

    @SerializedName("strMeasure9")
    var measure9: String? = null

    @SerializedName("strMeasure10")
    var measure10: String? = null

    @SerializedName("strMeasure11")
    var measure11: String? = null

    @SerializedName("strMeasure12")
    var measure12: String? = null

    @SerializedName("strMeasure13")
    var measure13: String? = null

    @SerializedName("strMeasure14")
    var measure14: String? = null

    @SerializedName("strMeasure15")
    var measure15: String? = null

    @SerializedName("strCreativeCommonsConfirmed")
    var creativeCommonsConfirmed: String? = null

    @SerializedName("dateModified")
    var dateModified: String? = null

}