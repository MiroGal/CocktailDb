package com.mirogal.cocktail.data.db.model

import androidx.room.ColumnInfo

class LocalizedStringDbModel(
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