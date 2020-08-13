package com.mirogal.cocktail.firebase

object AnalyticEvents {

    const val MAIN_TAB_CHANGE = "main_tab_change"
    const val MAIN_TAB_CHANGE_KEY = "main_tab_name"

    const val COCKTAIL_FAVORITE_ADD = "cocktail_favorite_add"
    const val COCKTAIL_FAVORITE_REMOVE = "cocktail_favorite_remove"
    const val COCKTAIL_FAVORITE_KEY = "cocktail_id"

    const val COCKTAIL_DETAIL_OPEN = "cocktail_detail_open"
    const val COCKTAIL_DETAIL_OPEN_KEY = "cocktail_id"

    const val COCKTAIL_FILTER_APPLY = "cocktail_filter_apply"
    const val COCKTAIL_FILTER_APPLY_ALCOHOL_KEY = "filter_alcohol"
    const val COCKTAIL_FILTER_APPLY_FILTER_TYPE_KEY = "filter_cocktail_type"
    const val COCKTAIL_FILTER_APPLY_CATEGORY_KEY = "filter_category"
    const val COCKTAIL_FILTER_APPLY_INGREDIENT_KEY = "filter_ingredients"

}