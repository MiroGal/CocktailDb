package com.mirogal.cocktail.data.db.model.cocktail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mirogal.cocktail.data.db.Table

@Entity(tableName = Table.COCKTAIL_INSTRUCTION,
        foreignKeys = [ForeignKey(
                entity = CocktailInfoDbModel::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("cocktail_id"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)])
class CocktailInstructionDbModel(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Long = -1L,

        @ColumnInfo(name = "cocktail_id")
        val cocktailId: Long = -1L,

        @ColumnInfo(name = "default")
        var baseValue: String? = null,

        @ColumnInfo(name = "default_alternate")
        var baseValueAlternate: String? = null,

        @ColumnInfo(name = "es")
        var es: String? = null,

        @ColumnInfo(name = "de")
        var de: String? = null,

        @ColumnInfo(name = "fr")
        var fr: String? = null,

        @ColumnInfo(name = "zn_hans")
        var zhHans: String? = null,

        @ColumnInfo(name = "zn_hant")
        var zhHant: String? = null

)