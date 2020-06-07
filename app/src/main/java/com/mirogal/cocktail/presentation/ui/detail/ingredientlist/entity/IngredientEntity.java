package com.mirogal.cocktail.presentation.ui.detail.ingredientlist.entity;

public class IngredientEntity {

    private int id;

    private String name;

    private String measure;

    public IngredientEntity(int id, String name, String measure) {
        this.id = id;
        this.name = name;
        this.measure = measure;
    }

    //  All getters and setters for the completeness of the entity

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
