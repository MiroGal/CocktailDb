package com.mirogal.cocktail.data.network.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CocktailNetEntity implements Serializable {

    @SerializedName("idDrink")
    private int id;

    @SerializedName("strDrink")
    private String name;

    @SerializedName("strDrinkAlternate")
    private String nameAlternate;

    @SerializedName("strDrinkES")
    private String nameEs;

    @SerializedName("strDrinkDE")
    private String nameDe;

    @SerializedName("strDrinkFR")
    private String nameFr;

    @SerializedName("strDrinkZH-HANS")
    private String nameZhHans;

    @SerializedName("strDrinkZH-HANT")
    private String nameZhHant;

    @SerializedName("strTags")
    private String tags;

    @SerializedName("strVideo")
    private String video;

    @SerializedName("strCategory")
    private String category;

    @SerializedName("strIBA")
    private String iba;

    @SerializedName("strAlcoholic")
    private String alcoholic;

    @SerializedName("strGlass")
    private String glass;

    @SerializedName("strInstructions")
    private String instruction;

    @SerializedName("strInstructionsES")
    private String instructionEs;

    @SerializedName("strInstructionsDE")
    private String instructionDe;

    @SerializedName("strInstructionsFR")
    private String instructionFr;

    @SerializedName("strInstructionsZH-HANS")
    private String instructionZhHans;

    @SerializedName("strInstructionsZH-HANT")
    private String instructionZhHant;

    @SerializedName("strDrinkThumb")
    private String imagePath;

    @SerializedName("strIngredient1")
    private String ingredient1;
    @SerializedName("strIngredient2")
    private String ingredient2;
    @SerializedName("strIngredient3")
    private String ingredient3;
    @SerializedName("strIngredient4")
    private String ingredient4;
    @SerializedName("strIngredient5")
    private String ingredient5;
    @SerializedName("strIngredient6")
    private String ingredient6;
    @SerializedName("strIngredient7")
    private String ingredient7;
    @SerializedName("strIngredient8")
    private String ingredient8;
    @SerializedName("strIngredient9")
    private String ingredient9;
    @SerializedName("strIngredient10")
    private String ingredient10;
    @SerializedName("strIngredient11")
    private String ingredient11;
    @SerializedName("strIngredient12")
    private String ingredient12;
    @SerializedName("strIngredient13")
    private String ingredient13;
    @SerializedName("strIngredient14")
    private String ingredient14;
    @SerializedName("strIngredient15")
    private String ingredient15;

    @SerializedName("strMeasure1")
    private String measure1;
    @SerializedName("strMeasure2")
    private String measure2;
    @SerializedName("strMeasure3")
    private String measure3;
    @SerializedName("strMeasure4")
    private String measure4;
    @SerializedName("strMeasure5")
    private String measure5;
    @SerializedName("strMeasure6")
    private String measure6;
    @SerializedName("strMeasure7")
    private String measure7;
    @SerializedName("strMeasure8")
    private String measure8;
    @SerializedName("strMeasure9")
    private String measure9;
    @SerializedName("strMeasure10")
    private String measure10;
    @SerializedName("strMeasure11")
    private String measure11;
    @SerializedName("strMeasure12")
    private String measure12;
    @SerializedName("strMeasure13")
    private String measure13;
    @SerializedName("strMeasure14")
    private String measure14;
    @SerializedName("strMeasure15")
    private String measure15;

    @SerializedName("strCreativeCommonsConfirmed")
    private String creativeCommonsConfirmed;

    @SerializedName("dateModified")
    private String dateModified;

    public CocktailNetEntity() {
    }

    public CocktailNetEntity(int id, String name, String nameAlternate, String nameEs, String nameDe,
                             String nameFr, String nameZhHans, String nameZhHant, String tags,
                             String video, String category, String iba, String alcoholic, String glass,
                             String instruction, String instructionEs, String instructionDe,
                             String instructionFr, String instructionZhHans, String instructionZhHant,
                             String imagePath, String ingredient1, String ingredient2,
                             String ingredient3, String ingredient4, String ingredient5,
                             String ingredient6, String ingredient7, String ingredient8,
                             String ingredient9, String ingredient10, String ingredient11,
                             String ingredient12, String ingredient13, String ingredient14,
                             String ingredient15, String measure1, String measure2, String measure3,
                             String measure4, String measure5, String measure6, String measure7,
                             String measure8, String measure9, String measure10, String measure11,
                             String measure12, String measure13, String measure14, String measure15,
                             String creativeCommonsConfirmed, String dateModified) {
        this.id = id;
        this.name = name;
        this.nameAlternate = nameAlternate;
        this.nameEs = nameEs;
        this.nameDe = nameDe;
        this.nameFr = nameFr;
        this.nameZhHans = nameZhHans;
        this.nameZhHant = nameZhHant;
        this.tags = tags;
        this.video = video;
        this.category = category;
        this.iba = iba;
        this.alcoholic = alcoholic;
        this.glass = glass;
        this.instruction = instruction;
        this.instructionEs = instructionEs;
        this.instructionDe = instructionDe;
        this.instructionFr = instructionFr;
        this.instructionZhHans = instructionZhHans;
        this.instructionZhHant = instructionZhHant;
        this.imagePath = imagePath;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
        this.ingredient4 = ingredient4;
        this.ingredient5 = ingredient5;
        this.ingredient6 = ingredient6;
        this.ingredient7 = ingredient7;
        this.ingredient8 = ingredient8;
        this.ingredient9 = ingredient9;
        this.ingredient10 = ingredient10;
        this.ingredient11 = ingredient11;
        this.ingredient12 = ingredient12;
        this.ingredient13 = ingredient13;
        this.ingredient14 = ingredient14;
        this.ingredient15 = ingredient15;
        this.measure1 = measure1;
        this.measure2 = measure2;
        this.measure3 = measure3;
        this.measure4 = measure4;
        this.measure5 = measure5;
        this.measure6 = measure6;
        this.measure7 = measure7;
        this.measure8 = measure8;
        this.measure9 = measure9;
        this.measure10 = measure10;
        this.measure11 = measure11;
        this.measure12 = measure12;
        this.measure13 = measure13;
        this.measure14 = measure14;
        this.measure15 = measure15;
        this.creativeCommonsConfirmed = creativeCommonsConfirmed;
        this.dateModified = dateModified;
    }

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

    public String getNameAlternate() {
        return nameAlternate;
    }

    public void setNameAlternate(String nameAlternate) {
        this.nameAlternate = nameAlternate;
    }

    public String getNameEs() {
        return nameEs;
    }

    public void setNameEs(String nameEs) {
        this.nameEs = nameEs;
    }

    public String getNameDe() {
        return nameDe;
    }

    public void setNameDe(String nameDe) {
        this.nameDe = nameDe;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getNameZhHans() {
        return nameZhHans;
    }

    public void setNameZhHans(String nameZhHans) {
        this.nameZhHans = nameZhHans;
    }

    public String getNameZhHant() {
        return nameZhHant;
    }

    public void setNameZhHant(String nameZhHant) {
        this.nameZhHant = nameZhHant;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIba() {
        return iba;
    }

    public void setIba(String iba) {
        this.iba = iba;
    }

    public String getAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(String alcoholic) {
        this.alcoholic = alcoholic;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getInstructionEs() {
        return instructionEs;
    }

    public void setInstructionEs(String instructionEs) {
        this.instructionEs = instructionEs;
    }

    public String getInstructionDe() {
        return instructionDe;
    }

    public void setInstructionDe(String instructionDe) {
        this.instructionDe = instructionDe;
    }

    public String getInstructionFr() {
        return instructionFr;
    }

    public void setInstructionFr(String instructionFr) {
        this.instructionFr = instructionFr;
    }

    public String getInstructionZhHans() {
        return instructionZhHans;
    }

    public void setInstructionZhHans(String instructionZhHans) {
        this.instructionZhHans = instructionZhHans;
    }

    public String getInstructionZhHant() {
        return instructionZhHant;
    }

    public void setInstructionZhHant(String instructionZhHant) {
        this.instructionZhHant = instructionZhHant;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getIngredient1() {
        return ingredient1;
    }

    public void setIngredient1(String ingredient1) {
        this.ingredient1 = ingredient1;
    }

    public String getIngredient2() {
        return ingredient2;
    }

    public void setIngredient2(String ingredient2) {
        this.ingredient2 = ingredient2;
    }

    public String getIngredient3() {
        return ingredient3;
    }

    public void setIngredient3(String ingredient3) {
        this.ingredient3 = ingredient3;
    }

    public String getIngredient4() {
        return ingredient4;
    }

    public void setIngredient4(String ingredient4) {
        this.ingredient4 = ingredient4;
    }

    public String getIngredient5() {
        return ingredient5;
    }

    public void setIngredient5(String ingredient5) {
        this.ingredient5 = ingredient5;
    }

    public String getIngredient6() {
        return ingredient6;
    }

    public void setIngredient6(String ingredient6) {
        this.ingredient6 = ingredient6;
    }

    public String getIngredient7() {
        return ingredient7;
    }

    public void setIngredient7(String ingredient7) {
        this.ingredient7 = ingredient7;
    }

    public String getIngredient8() {
        return ingredient8;
    }

    public void setIngredient8(String ingredient8) {
        this.ingredient8 = ingredient8;
    }

    public String getIngredient9() {
        return ingredient9;
    }

    public void setIngredient9(String ingredient9) {
        this.ingredient9 = ingredient9;
    }

    public String getIngredient10() {
        return ingredient10;
    }

    public void setIngredient10(String ingredient10) {
        this.ingredient10 = ingredient10;
    }

    public String getIngredient11() {
        return ingredient11;
    }

    public void setIngredient11(String ingredient11) {
        this.ingredient11 = ingredient11;
    }

    public String getIngredient12() {
        return ingredient12;
    }

    public void setIngredient12(String ingredient12) {
        this.ingredient12 = ingredient12;
    }

    public String getIngredient13() {
        return ingredient13;
    }

    public void setIngredient13(String ingredient13) {
        this.ingredient13 = ingredient13;
    }

    public String getIngredient14() {
        return ingredient14;
    }

    public void setIngredient14(String ingredient14) {
        this.ingredient14 = ingredient14;
    }

    public String getIngredient15() {
        return ingredient15;
    }

    public void setIngredient15(String ingredient15) {
        this.ingredient15 = ingredient15;
    }

    public String getMeasure1() {
        return measure1;
    }

    public void setMeasure1(String measure1) {
        this.measure1 = measure1;
    }

    public String getMeasure2() {
        return measure2;
    }

    public void setMeasure2(String measure2) {
        this.measure2 = measure2;
    }

    public String getMeasure3() {
        return measure3;
    }

    public void setMeasure3(String measure3) {
        this.measure3 = measure3;
    }

    public String getMeasure4() {
        return measure4;
    }

    public void setMeasure4(String measure4) {
        this.measure4 = measure4;
    }

    public String getMeasure5() {
        return measure5;
    }

    public void setMeasure5(String measure5) {
        this.measure5 = measure5;
    }

    public String getMeasure6() {
        return measure6;
    }

    public void setMeasure6(String measure6) {
        this.measure6 = measure6;
    }

    public String getMeasure7() {
        return measure7;
    }

    public void setMeasure7(String measure7) {
        this.measure7 = measure7;
    }

    public String getMeasure8() {
        return measure8;
    }

    public void setMeasure8(String measure8) {
        this.measure8 = measure8;
    }

    public String getMeasure9() {
        return measure9;
    }

    public void setMeasure9(String measure9) {
        this.measure9 = measure9;
    }

    public String getMeasure10() {
        return measure10;
    }

    public void setMeasure10(String measure10) {
        this.measure10 = measure10;
    }

    public String getMeasure11() {
        return measure11;
    }

    public void setMeasure11(String measure11) {
        this.measure11 = measure11;
    }

    public String getMeasure12() {
        return measure12;
    }

    public void setMeasure12(String measure12) {
        this.measure12 = measure12;
    }

    public String getMeasure13() {
        return measure13;
    }

    public void setMeasure13(String measure13) {
        this.measure13 = measure13;
    }

    public String getMeasure14() {
        return measure14;
    }

    public void setMeasure14(String measure14) {
        this.measure14 = measure14;
    }

    public String getMeasure15() {
        return measure15;
    }

    public void setMeasure15(String measure15) {
        this.measure15 = measure15;
    }

    public String getCreativeCommonsConfirmed() {
        return creativeCommonsConfirmed;
    }

    public void setCreativeCommonsConfirmed(String creativeCommonsConfirmed) {
        this.creativeCommonsConfirmed = creativeCommonsConfirmed;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
}
