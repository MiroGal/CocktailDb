package com.mirogal.cocktail.presentation.ui.constant;

import org.jetbrains.annotations.NotNull;

public enum IntentTag {

    COCKTAIL_ENTITY("cocktail_entity");

    private final String stringValue;

    IntentTag(final String s) {
        stringValue = s;
    }

    @NotNull
    public String toString() {
        return stringValue;
    }
}
