package com.mirogal.cocktail.datanative.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mirogal.cocktail.datanative.db.model.CocktailDbModel

@Dao
interface CocktailDao {

    @get:Query("SELECT * FROM " + CocktailDbModel.TABLE_NAME)
    val cocktailListLiveData: LiveData<List<CocktailDbModel>?>

    @Query("SELECT * FROM " + CocktailDbModel.TABLE_NAME
            + " WHERE " + CocktailDbModel.COLUMN_ID + " = :cocktailId")
    fun getCocktailById(cocktailId: Int): LiveData<CocktailDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCocktail(cocktail: CocktailDbModel)

    @Query("DELETE FROM " + CocktailDbModel.TABLE_NAME
            + " WHERE " + CocktailDbModel.COLUMN_ID + " = :cocktailId")
    fun deleteCocktail(cocktailId: Int)

    @Query("UPDATE " + CocktailDbModel.TABLE_NAME
            + " SET " + CocktailDbModel.COLUMN_FAVORITE + " = :isFavorite"
            + " WHERE " + CocktailDbModel.COLUMN_ID + " = :cocktailId")
    fun setFavorite(cocktailId: Int, isFavorite: Boolean)

}