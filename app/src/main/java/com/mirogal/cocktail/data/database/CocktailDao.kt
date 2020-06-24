package com.mirogal.cocktail.data.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktail(cocktail: CocktailDbEntity)

    @get:Query("SELECT * FROM " + CocktailDbEntity.TABLE_NAME)
    val dsCocktailList: DataSource.Factory<Int, CocktailDbEntity>

    @get:Query("SELECT * FROM " + CocktailDbEntity.TABLE_NAME)
    val lvCocktailList: LiveData<List<CocktailDbEntity>>

    @Query("DELETE FROM " + CocktailDbEntity.TABLE_NAME
            + " WHERE " + CocktailDbEntity.COLUMN_ID + " = :cocktailId")
    fun deleteCocktail(cocktailId: Int)

    @Query("UPDATE " + CocktailDbEntity.TABLE_NAME
            + " SET " + CocktailDbEntity.COLUMN_FAVORITE + " = :isFavorite"
            + " WHERE " + CocktailDbEntity.COLUMN_ID + " = :cocktailId")
    fun setFavorite(cocktailId: Int, isFavorite: Boolean)

}