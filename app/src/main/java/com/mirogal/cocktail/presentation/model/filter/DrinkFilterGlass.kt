package com.mirogal.cocktail.presentation.model.filter

enum class DrinkFilterGlass(override val type: DrinkFilterType, override val key: String): DrinkFilter {

    DISABLE(DrinkFilterType.GLASS, "Disable"),
    HIGHBALL(DrinkFilterType.GLASS, "Highball glass"),
    COCKTAIL(DrinkFilterType.GLASS, "Cocktail glass"),
    OLD_FASHIONED(DrinkFilterType.GLASS, "Old-fashioned glass"),
    COLLINS(DrinkFilterType.GLASS, "Collins glass"),
    POUSSE_CAFE(DrinkFilterType.GLASS, "Pousse cafe glass"),
    CHAMPAGNE_FLUTE(DrinkFilterType.GLASS, "Champagne flute"),
    WHISKEY_SOUR(DrinkFilterType.GLASS, "Whiskey sour glass"),
    BRANDY_SNIFTER(DrinkFilterType.GLASS, "Brandy snifter"),
    WHITE_WINE(DrinkFilterType.GLASS, "White wine glass"),
    NICK_AND_NORA(DrinkFilterType.GLASS, "Nick and Nora Glass"),
    HURRICANE(DrinkFilterType.GLASS, "Hurricane glass"),
    COFFEE_MUG(DrinkFilterType.GLASS, "Coffee mug"),
    SHOT(DrinkFilterType.GLASS, "Shot glass"),
    JAR(DrinkFilterType.GLASS, "Jar"),
    IRISH_COFFEE_CUP(DrinkFilterType.GLASS, "Irish coffee cup"),
    PUNCH_BOWL(DrinkFilterType.GLASS, "Punch bowl"),
    PITCHER(DrinkFilterType.GLASS, "Pitcher"),
    PINT(DrinkFilterType.GLASS, "Pint glass"),
    COPPER(DrinkFilterType.GLASS, "Copper Mug"),
    WINE(DrinkFilterType.GLASS, "Wine Glass"),
    CORDIAL(DrinkFilterType.GLASS, "Cordial glass"),
    BEER_MUG(DrinkFilterType.GLASS, "Beer mug"),
    MARGARITA_COUPETTE(DrinkFilterType.GLASS, "Margarita/Coupette glass"),
    BEER_PILSNER(DrinkFilterType.GLASS, "Beer pilsner"),
    BEER(DrinkFilterType.GLASS, "Beer Glass"),
    PARFAIT(DrinkFilterType.GLASS, "Parfait glass"),
    MASON_JAR(DrinkFilterType.GLASS, "Mason jar"),
    MARGARITA(DrinkFilterType.GLASS, "Margarita glass"),
    MARTINI(DrinkFilterType.GLASS, "Martini Glass"),
    BALLOON(DrinkFilterType.GLASS, "Balloon Glass"),
    COUPE(DrinkFilterType.GLASS, "Coupe Glass")

}
