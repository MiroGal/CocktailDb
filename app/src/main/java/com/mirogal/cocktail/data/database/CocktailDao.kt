package com.mirogal.cocktail.data.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity

@Dao
interface CocktailDao {

    @get:Query("SELECT * FROM " + CocktailDbEntity.TABLE_NAME)
    val cocktailListLiveData: LiveData<List<CocktailDbEntity>>

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