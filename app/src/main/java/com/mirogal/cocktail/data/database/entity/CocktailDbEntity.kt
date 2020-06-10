package com.mirogal.cocktail.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = CocktailDbEntity.TABLE_NAME)
class CocktailDbEntity : Serializable {

    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    var id = 0

    @ColumnInfo(name = COLUMN_NAME)
    var name: String? = null

    @ColumnInfo(name = COLUMN_ALCOHOLIC)
    var alcoholic: String? = null

    @ColumnInfo(name = COLUMN_GLASS)
    var glass: String? = null

    @ColumnInfo(name = COLUMN_INSTRUCTION)
    var instruction: String? = null

    @ColumnInfo(name = COLUMN_IMAGE_PATH)
    var imagePath: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_1)
    var ingredient1: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_2)
    var ingredient2: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_3)
    var ingredient3: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_4)
    var ingredient4: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_5)
    var ingredient5: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_6)
    var ingredient6: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_7)
    var ingredient7: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_8)
    var ingredient8: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_9)
    var ingredient9: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_10)
    var ingredient10: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_11)
    var ingredient11: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_12)
    var ingredient12: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_13)
    var ingredient13: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_14)
    var ingredient14: String? = null

    @ColumnInfo(name = COLUMN_INGREDIENT_15)
    var ingredient15: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_1)
    var measure1: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_2)
    var measure2: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_3)
    var measure3: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_4)
    var measure4: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_5)
    var measure5: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_6)
    var measure6: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_7)
    var measure7: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_8)
    var measure8: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_9)
    var measure9: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_10)
    var measure10: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_11)
    var measure11: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_12)
    var measure12: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_13)
    var measure13: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_14)
    var measure14: String? = null

    @ColumnInfo(name = COLUMN_MEASURE_15)
    var measure15: String? = null

    constructor()

    @Ignore
    constructor(id: Int, name: String?, alcoholic: String?, glass: String?, instruction: String?,
                imagePath: String?, ingredient1: String?, ingredient2: String?,
                ingredient3: String?, ingredient4: String?, ingredient5: String?,
                ingredient6: String?, ingredient7: String?, ingredient8: String?,
                ingredient9: String?, ingredient10: String?, ingredient11: String?,
                ingredient12: String?, ingredient13: String?, ingredient14: String?,
                ingredient15: String?, measure1: String?, measure2: String?, measure3: String?,
                measure4: String?, measure5: String?, measure6: String?, measure7: String?,
                measure8: String?, measure9: String?, measure10: String?, measure11: String?,
                measure12: String?, measure13: String?, measure14: String?, measure15: String?) {
        this.id = id
        this.name = name
        this.alcoholic = alcoholic
        this.glass = glass
        this.instruction = instruction
        this.imagePath = imagePath
        this.ingredient1 = ingredient1
        this.ingredient2 = ingredient2
        this.ingredient3 = ingredient3
        this.ingredient4 = ingredient4
        this.ingredient5 = ingredient5
        this.ingredient6 = ingredient6
        this.ingredient7 = ingredient7
        this.ingredient8 = ingredient8
        this.ingredient9 = ingredient9
        this.ingredient10 = ingredient10
        this.ingredient11 = ingredient11
        this.ingredient12 = ingredient12
        this.ingredient13 = ingredient13
        this.ingredient14 = ingredient14
        this.ingredient15 = ingredient15
        this.measure1 = measure1
        this.measure2 = measure2
        this.measure3 = measure3
        this.measure4 = measure4
        this.measure5 = measure5
        this.measure6 = measure6
        this.measure7 = measure7
        this.measure8 = measure8
        this.measure9 = measure9
        this.measure10 = measure10
        this.measure11 = measure11
        this.measure12 = measure12
        this.measure13 = measure13
        this.measure14 = measure14
        this.measure15 = measure15
    }

    val ingredientList: Array<String?>
        get() = arrayOf(ingredient1, ingredient2, ingredient3, ingredient4, ingredient5,
                ingredient6, ingredient7, ingredient8, ingredient9, ingredient10,
                ingredient11, ingredient12, ingredient13, ingredient14, ingredient15)

    val measureList: Array<String?>
        get() = arrayOf(measure1, measure2, measure3, measure4, measure5,
                measure6, measure7, measure8, measure9, measure10,
                measure11, measure12, measure13, measure14, measure15)

    companion object {
        const val TABLE_NAME = "cocktail_entity"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_ALCOHOLIC = "alcoholic"
        const val COLUMN_GLASS = "glass"
        const val COLUMN_INSTRUCTION = "instruction"
        const val COLUMN_IMAGE_PATH = "image_path"
        const val COLUMN_INGREDIENT_1 = "ingredient_1"
        const val COLUMN_INGREDIENT_2 = "ingredient_2"
        const val COLUMN_INGREDIENT_3 = "ingredient_3"
        const val COLUMN_INGREDIENT_4 = "ingredient_4"
        const val COLUMN_INGREDIENT_5 = "ingredient_5"
        const val COLUMN_INGREDIENT_6 = "ingredient_6"
        const val COLUMN_INGREDIENT_7 = "ingredient_7"
        const val COLUMN_INGREDIENT_8 = "ingredient_8"
        const val COLUMN_INGREDIENT_9 = "ingredient_9"
        const val COLUMN_INGREDIENT_10 = "ingredient_10"
        const val COLUMN_INGREDIENT_11 = "ingredient_11"
        const val COLUMN_INGREDIENT_12 = "ingredient_12"
        const val COLUMN_INGREDIENT_13 = "ingredient_13"
        const val COLUMN_INGREDIENT_14 = "ingredient_14"
        const val COLUMN_INGREDIENT_15 = "ingredient_15"
        const val COLUMN_MEASURE_1 = "measure_1"
        const val COLUMN_MEASURE_2 = "measure_2"
        const val COLUMN_MEASURE_3 = "measure_3"
        const val COLUMN_MEASURE_4 = "measure_4"
        const val COLUMN_MEASURE_5 = "measure_5"
        const val COLUMN_MEASURE_6 = "measure_6"
        const val COLUMN_MEASURE_7 = "measure_7"
        const val COLUMN_MEASURE_8 = "measure_8"
        const val COLUMN_MEASURE_9 = "measure_9"
        const val COLUMN_MEASURE_10 = "measure_10"
        const val COLUMN_MEASURE_11 = "measure_11"
        const val COLUMN_MEASURE_12 = "measure_12"
        const val COLUMN_MEASURE_13 = "measure_13"
        const val COLUMN_MEASURE_14 = "measure_14"
        const val COLUMN_MEASURE_15 = "measure_15"
    }

}