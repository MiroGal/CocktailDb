package com.mirogal.cocktail.data.database;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;

@Dao
public interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCocktail(CocktailDbEntity cocktail);

    @Query("SELECT * FROM " + CocktailDbEntity.TABLE_NAME)
    DataSource.Factory<Integer, CocktailDbEntity> getCocktailList();

    @Query("DELETE FROM " + CocktailDbEntity.TABLE_NAME
            + " WHERE " + CocktailDbEntity.COLUMN_ID + " = :cocktailId")
    void deleteCocktail(int cocktailId);
}
