package com.mirogal.cocktail.data.db.impl.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mirogal.cocktail.data.db.Table
import com.mirogal.cocktail.data.db.impl.dao.base.BaseDao
import com.mirogal.cocktail.data.db.model.cocktail.CocktailDbModel
import com.mirogal.cocktail.data.db.model.cocktail.CocktailInfoDbModel
import com.mirogal.cocktail.data.db.model.cocktail.CocktailInstructionDbModel
import com.mirogal.cocktail.data.db.model.cocktail.CocktailNameDbModel

@Dao
interface CocktailDao : BaseDao<CocktailDbModel> {

    @get:Query("SELECT * FROM ${Table.COCKTAIL_INFO}")
    val cocktailListLiveData: LiveData<List<CocktailDbModel>>

    @get:Query("SELECT * FROM ${Table.COCKTAIL_INFO} WHERE is_favorite = 1")
    val favouriteCocktailListLiveData: LiveData<List<CocktailDbModel>>

    @Transaction
    @Query("SELECT * FROM ${Table.COCKTAIL_INFO} LIMIT 1")
    fun getFirstCocktail(): CocktailDbModel?

    @Transaction
    @Query("SELECT * FROM ${Table.COCKTAIL_INFO} WHERE id = :id")
    fun getCocktailByIdLiveData(id: Long): LiveData<CocktailDbModel?>

    @Transaction
    @Query("SELECT * FROM ${Table.COCKTAIL_INFO} WHERE id = :id")
    fun getCocktailById(id: Long): CocktailDbModel?

    @Transaction
    fun addOrReplaceCocktails(vararg cocktails: CocktailDbModel) {
        cocktails.forEach { cocktail ->
            insertCocktailInfo(cocktail.cocktailInfo)
            insertCocktailName(cocktail.cocktailNames)
            insertCocktailInstruction(cocktail.cocktailInstructions)
        }
    }

    @Transaction
    fun addOrReplaceCocktail(cocktail: CocktailDbModel) {
        insertCocktailInfo(cocktail.cocktailInfo)
        insertCocktailName(cocktail.cocktailNames)
        insertCocktailInstruction(cocktail.cocktailInstructions)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktailInfo(cocktailInfo: CocktailInfoDbModel?): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktailName(cocktailName: CocktailNameDbModel?): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktailInstruction(cocktailInstruction: CocktailInstructionDbModel?): Long

    @Transaction
    fun replaceAllCocktails(vararg cocktail: CocktailDbModel) {
        deleteAllCocktails()
        addOrReplaceCocktails(*cocktail)
    }

    // Crash method!!!
//    @Transaction
//    @Delete
//    fun deleteCocktails(vararg cocktail: NewCocktailDbModel)

    @Transaction
    @Query("DELETE FROM ${Table.COCKTAIL_INFO}")
    fun deleteAllCocktails()

    @Transaction
    @Query("DELETE FROM ${Table.COCKTAIL_INFO} WHERE id = :id")
    fun deleteCocktailById(id: Long)

    @Query("UPDATE ${Table.COCKTAIL_INFO} SET is_favorite = :isFavorite WHERE id = :id")
    fun setCocktailFavorite(id: Long, isFavorite: Boolean)

}