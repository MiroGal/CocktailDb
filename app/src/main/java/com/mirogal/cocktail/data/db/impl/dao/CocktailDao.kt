package com.mirogal.cocktail.data.db.impl.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mirogal.cocktail.data.db.Table
import com.mirogal.cocktail.data.db.impl.dao.base.BaseDao
import com.mirogal.cocktail.data.db.model.CocktailDbModel

@Dao
interface CocktailDao : BaseDao<CocktailDbModel> {

    @get:Query("SELECT * FROM ${Table.COCKTAIL}")
    val cocktailListLiveData: LiveData<List<CocktailDbModel>>

    @get:Query("SELECT * FROM ${Table.COCKTAIL} WHERE is_favorite = 1")
    val favouriteCocktailListLiveData: LiveData<List<CocktailDbModel>>

    @Query("SELECT * FROM ${Table.COCKTAIL} LIMIT 1")
    fun getFirstCocktail(): CocktailDbModel?

    @Query("SELECT * FROM ${Table.COCKTAIL} WHERE id = :id")
    fun getCocktailByIdLiveData(id: Long): LiveData<CocktailDbModel?>

    @Query("SELECT * FROM ${Table.COCKTAIL} WHERE id = :id")
    fun getCocktailById(id: Long): CocktailDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrReplaceCocktail(cocktail: CocktailDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrReplaceCocktails(vararg cocktail: CocktailDbModel)

    @Transaction
    fun replaceAllCocktails(vararg cocktail: CocktailDbModel) {
        deleteAllCocktails()
        addOrReplaceCocktails(*cocktail)
    }

    @Delete
    fun deleteCocktails(vararg cocktail: CocktailDbModel)

    @Query("DELETE FROM ${Table.COCKTAIL}")
    fun deleteAllCocktails()

}