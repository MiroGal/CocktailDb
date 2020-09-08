package com.mirogal.cocktail.data.db.model.cocktail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mirogal.cocktail.data.db.Table

@Entity(tableName = Table.COCKTAIL_INFO)
class CocktailInfoDbModel(

        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Long = -1L,

        @ColumnInfo(name = "category")
        val category: String = "",

        @ColumnInfo(name = "alcohol_type")
        val alcoholType: String = "",

        @ColumnInfo(name = "glass")
        val glass: String = "",

        @ColumnInfo(name = "image")
        val image: String = "",

        @ColumnInfo(name = "ingredients")
        val ingredients: List<String> = emptyList(),

        @ColumnInfo(name = "measures")
        val measures: List<String?> = emptyList(),

        @ColumnInfo(name = "is_favorite")
        val isFavorite: Boolean = false/*,

        @ColumnInfo(name = "date")
        val date: Date = Date()*/

)