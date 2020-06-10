package com.mirogal.cocktail.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktail(cocktail: CocktailDbEntity)

    @get:Query("SELECT * FROM " + CocktailDbEntity.TABLE_NAME)
    val cocktailList: DataSource.Factory<Int, CocktailDbEntity>

    @Query("DELETE FROM " + CocktailDbEntity.TABLE_NAME
            + " WHERE " + CocktailDbEntity.COLUMN_ID + " = :cocktailId")
    fun deleteCocktail(cocktailId: Int)

}