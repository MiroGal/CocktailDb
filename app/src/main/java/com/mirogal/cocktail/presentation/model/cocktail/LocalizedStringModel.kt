package com.mirogal.cocktail.presentation.model.cocktail

data class LocalizedStringModel(
        val baseValue: String? = null,
        val baseValueAlternate: String? = null,
        val es: String? = null,
        val de: String? = null,
        val fr: String? = null,
        val zhHans: String? = null,
        val zhHant: String? = null
)