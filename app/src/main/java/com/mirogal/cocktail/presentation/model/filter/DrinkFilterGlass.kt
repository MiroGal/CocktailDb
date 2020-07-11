package com.mirogal.cocktail.presentation.model.filter

enum class DrinkFilterGlass(override val type: DrinkFilterType, override val key: String): DrinkFilter {

    DISABLE(DrinkFilterType.GLASS, "Disable"),
    HIGHBALL_Glass(DrinkFilterType.GLASS, "Highball glass"),
    COCKTAIL_Glass(DrinkFilterType.GLASS, "Cocktail glass"),
    OLD_FASHIONED_Glass(DrinkFilterType.GLASS, "Old-fashioned glass"),
    COLLINS_Glass(DrinkFilterType.GLASS, "Collins glass"),
    POUSSE_CAFE_Glass(DrinkFilterType.GLASS, "Pousse cafe glass"),
    CHAMPAGNE_FLUTE(DrinkFilterType.GLASS, "Champagne flute"),
    WHISKEY_SOUR_Glass(DrinkFilterType.GLASS, "Whiskey sour glass"),
    BRANDY_SNIFTER(DrinkFilterType.GLASS, "Brandy snifter"),
    WHITE_WINE_Glass(DrinkFilterType.GLASS, "White wine glass"),
    NICK_AND_NORA_Glass(DrinkFilterType.GLASS, "Nick and Nora Glass"),
    HURRICANE_Glass(DrinkFilterType.GLASS, "Hurricane glass"),
    COFFEE_MUG(DrinkFilterType.GLASS, "Coffee mug"),
    SHOT_Glass(DrinkFilterType.GLASS, "Shot glass"),
    JAR(DrinkFilterType.GLASS, "Jar"),
    IRISH_COFFEE_CUP(DrinkFilterType.GLASS, "Irish coffee cup"),
    PUNCH_BOWL(DrinkFilterType.GLASS, "Punch bowl"),
    PITCHER(DrinkFilterType.GLASS, "Pitcher"),
    PINT_Glass(DrinkFilterType.GLASS, "Pint glass"),
    COPPER_MUG(DrinkFilterType.GLASS, "Copper Mug"),
    WINE_Glass(DrinkFilterType.GLASS, "Wine Glass"),
    CORDIAL_Glass(DrinkFilterType.GLASS, "Cordial glass"),
    BEER_MUG(DrinkFilterType.GLASS, "Beer mug"),
    MARGARITA_COUPETTE_Glass(DrinkFilterType.GLASS, "Margarita/Coupette glass"),
    BEER_PILSNER(DrinkFilterType.GLASS, "Beer pilsner"),
    BEER_Glass(DrinkFilterType.GLASS, "Beer Glass"),
    PARFAIT_Glass(DrinkFilterType.GLASS, "Parfait glass"),
    MASON_JAR(DrinkFilterType.GLASS, "Mason jar"),
    MARGARITA_Glass(DrinkFilterType.GLASS, "Margarita glass"),
    MARTINI_Glass(DrinkFilterType.GLASS, "Martini Glass"),
    BALLOON_Glass(DrinkFilterType.GLASS, "Balloon Glass"),
    COUPE_Glass(DrinkFilterType.GLASS, "Coupe Glass")

}
