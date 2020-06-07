package com.mirogal.cocktail.data.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = CocktailDbEntity.TABLE_NAME)
public class CocktailDbEntity implements Serializable {

    //  Public column names

    public static final String TABLE_NAME = "cocktail_entity";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ALCOHOLIC = "alcoholic";
    public static final String COLUMN_GLASS = "glass";
    public static final String COLUMN_INSTRUCTION = "instruction";
    public static final String COLUMN_IMAGE_PATH = "image_path";

    public static final String COLUMN_INGREDIENT_1 = "ingredient_1";
    public static final String COLUMN_INGREDIENT_2 = "ingredient_2";
    public static final String COLUMN_INGREDIENT_3 = "ingredient_3";
    public static final String COLUMN_INGREDIENT_4 = "ingredient_4";
    public static final String COLUMN_INGREDIENT_5 = "ingredient_5";
    public static final String COLUMN_INGREDIENT_6 = "ingredient_6";
    public static final String COLUMN_INGREDIENT_7 = "ingredient_7";
    public static final String COLUMN_INGREDIENT_8 = "ingredient_8";
    public static final String COLUMN_INGREDIENT_9 = "ingredient_9";
    public static final String COLUMN_INGREDIENT_10 = "ingredient_10";
    public static final String COLUMN_INGREDIENT_11 = "ingredient_11";
    public static final String COLUMN_INGREDIENT_12 = "ingredient_12";
    public static final String COLUMN_INGREDIENT_13 = "ingredient_13";
    public static final String COLUMN_INGREDIENT_14 = "ingredient_14";
    public static final String COLUMN_INGREDIENT_15 = "ingredient_15";

    public static final String COLUMN_MEASURE_1 = "measure_1";
    public static final String COLUMN_MEASURE_2 = "measure_2";
    public static final String COLUMN_MEASURE_3 = "measure_3";
    public static final String COLUMN_MEASURE_4 = "measure_4";
    public static final String COLUMN_MEASURE_5 = "measure_5";
    public static final String COLUMN_MEASURE_6 = "measure_6";
    public static final String COLUMN_MEASURE_7 = "measure_7";
    public static final String COLUMN_MEASURE_8 = "measure_8";
    public static final String COLUMN_MEASURE_9 = "measure_9";
    public static final String COLUMN_MEASURE_10 = "measure_10";
    public static final String COLUMN_MEASURE_11 = "measure_11";
    public static final String COLUMN_MEASURE_12 = "measure_12";
    public static final String COLUMN_MEASURE_13 = "measure_13";
    public static final String COLUMN_MEASURE_14 = "measure_14";
    public static final String COLUMN_MEASURE_15 = "measure_15";

    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    private int id;

    @ColumnInfo(name = COLUMN_NAME)
    private String name;

    @ColumnInfo(name = COLUMN_ALCOHOLIC)
    private String alcoholic;

    @ColumnInfo(name = COLUMN_GLASS)
    private String glass;

    @ColumnInfo(name = COLUMN_INSTRUCTION)
    private String instruction;

    @ColumnInfo(name = COLUMN_IMAGE_PATH)
    private String imagePath;

    @ColumnInfo(name = COLUMN_INGREDIENT_1)
    private String ingredient1;
    @ColumnInfo(name = COLUMN_INGREDIENT_2)
    private String ingredient2;
    @ColumnInfo(name = COLUMN_INGREDIENT_3)
    private String ingredient3;
    @ColumnInfo(name = COLUMN_INGREDIENT_4)
    private String ingredient4;
    @ColumnInfo(name = COLUMN_INGREDIENT_5)
    private String ingredient5;
    @ColumnInfo(name = COLUMN_INGREDIENT_6)
    private String ingredient6;
    @ColumnInfo(name = COLUMN_INGREDIENT_7)
    private String ingredient7;
    @ColumnInfo(name = COLUMN_INGREDIENT_8)
    private String ingredient8;
    @ColumnInfo(name = COLUMN_INGREDIENT_9)
    private String ingredient9;
    @ColumnInfo(name = COLUMN_INGREDIENT_10)
    private String ingredient10;
    @ColumnInfo(name = COLUMN_INGREDIENT_11)
    private String ingredient11;
    @ColumnInfo(name = COLUMN_INGREDIENT_12)
    private String ingredient12;
    @ColumnInfo(name = COLUMN_INGREDIENT_13)
    private String ingredient13;
    @ColumnInfo(name = COLUMN_INGREDIENT_14)
    private String ingredient14;
    @ColumnInfo(name = COLUMN_INGREDIENT_15)
    private String ingredient15;

    @ColumnInfo(name = COLUMN_MEASURE_1)
    private String measure1;
    @ColumnInfo(name = COLUMN_MEASURE_2)
    private String measure2;
    @ColumnInfo(name = COLUMN_MEASURE_3)
    private String measure3;
    @ColumnInfo(name = COLUMN_MEASURE_4)
    private String measure4;
    @ColumnInfo(name = COLUMN_MEASURE_5)
    private String measure5;
    @ColumnInfo(name = COLUMN_MEASURE_6)
    private String measure6;
    @ColumnInfo(name = COLUMN_MEASURE_7)
    private String measure7;
    @ColumnInfo(name = COLUMN_MEASURE_8)
    private String measure8;
    @ColumnInfo(name = COLUMN_MEASURE_9)
    private String measure9;
    @ColumnInfo(name = COLUMN_MEASURE_10)
    private String measure10;
    @ColumnInfo(name = COLUMN_MEASURE_11)
    private String measure11;
    @ColumnInfo(name = COLUMN_MEASURE_12)
    private String measure12;
    @ColumnInfo(name = COLUMN_MEASURE_13)
    private String measure13;
    @ColumnInfo(name = COLUMN_MEASURE_14)
    private String measure14;
    @ColumnInfo(name = COLUMN_MEASURE_15)
    private String measure15;

    public CocktailDbEntity() {
    }

    @Ignore
    public CocktailDbEntity(int id, String name, String alcoholic, String glass, String instruction,
                            String imagePath, String ingredient1, String ingredient2,
                            String ingredient3, String ingredient4, String ingredient5,
                            String ingredient6, String ingredient7, String ingredient8,
                            String ingredient9, String ingredient10, String ingredient11,
                            String ingredient12, String ingredient13, String ingredient14,
                            String ingredient15, String measure1, String measure2, String measure3,
                            String measure4, String measure5, String measure6, String measure7,
                            String measure8, String measure9, String measure10, String measure11,
                            String measure12, String measure13, String measure14, String measure15) {
        this.id = id;
        this.name = name;
        this.alcoholic = alcoholic;
        this.glass = glass;
        this.instruction = instruction;
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

    public String[] getIngredientList() {
        return new String[] {ingredient1, ingredient2, ingredient3, ingredient4, ingredient5,
                ingredient6, ingredient7, ingredient8, ingredient9, ingredient10,
                ingredient11, ingredient12, ingredient13, ingredient14, ingredient15};
    }

    public String[] getMeasureList() {
        return new String[] {measure1, measure2, measure3, measure4, measure5,
                measure6, measure7, measure8, measure9, measure10,
                measure11, measure12, measure13, measure14, measure15};
    }
}
