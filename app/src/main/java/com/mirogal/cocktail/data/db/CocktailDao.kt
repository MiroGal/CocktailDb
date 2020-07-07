package com.mirogal.cocktail.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mirogal.cocktail.data.db.entity.CocktailDbEntity

@Dao
interface CocktailDao {

    @get:Query("SELECT * FROM " + CocktailDbEntity.TABLE_NAME)
    val cocktailListLiveData: LiveData<List<CocktailDbEntity>?>

    @Query("SELECT * FROM " + CocktailDbEntity.TABLE_NAME
            + " WHERE " + CocktailDbEntity.COLUMN_ID + " = :cocktailId")
    fun getCocktailById(cocktailId: Int): LiveData<CocktailDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCocktail(cocktail: CocktailDbEntity)

    @Query("DELETE FROM " + CocktailDbEntity.TABLE_NAME
            + " WHERE " + CocktailDbEntity.COLUMN_ID + " = :cocktailId")
    fun deleteCocktail(cocktailId: Int)

    @Query("UPDATE " + CocktailDbEntity.TABLE_NAME
            + " SET " + CocktailDbEntity.COLUMN_FAVORITE + " = :isFavorite"
            + " WHERE " + CocktailDbEntity.COLUMN_ID + " = :cocktailId")
    fun setFavorite(cocktailId: Int, isFavorite: Boolean)

}