package com.mirogal.cocktail.data.network.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ContainerNetEntity implements Serializable {

    @SerializedName("drinks")
    private List<CocktailNetEntity> cocktailList;

    public ContainerNetEntity() {
    }

    public ContainerNetEntity(List<CocktailNetEntity> cocktailList) {
        this.cocktailList = cocktailList;
    }

    public List<CocktailNetEntity> getCocktailList() {
        return cocktailList;
    }

    public void setCocktailList(List<CocktailNetEntity> cocktailList) {
        this.cocktailList = cocktailList;
    }
}
