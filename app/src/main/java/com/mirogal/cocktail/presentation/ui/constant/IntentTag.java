package com.mirogal.cocktail.presentation.ui.constant;

public enum IntentTag {

    COCKTAIL_ENTITY("cocktail_entity");

    private final String stringValue;

    IntentTag(final String s) {
        stringValue = s;
    }

    public String toString() {
        return stringValue;
    }
}
