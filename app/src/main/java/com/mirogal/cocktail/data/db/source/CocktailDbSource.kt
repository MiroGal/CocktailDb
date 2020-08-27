package com.mirogal.cocktail.data.db.source

import androidx.lifecycle.LiveData
import com.mirogal.cocktail.data.db.model.CocktailDbModel
import com.mirogal.cocktail.data.db.source.base.BaseDbSource

interface CocktailDbSource : BaseDbSource {

    val cocktailListLiveData: LiveData<List<CocktailDbModel>>

    suspend fun hasCocktails(): Boolean
    suspend fun getFirstCocktail(): CocktailDbModel?
    fun getCocktailByIdLiveData(id: Long): LiveData<CocktailDbModel?>
    suspend fun getCocktailById(id: Long): CocktailDbModel?
    suspend fun addOrReplaceCocktail(cocktail: CocktailDbModel)
    suspend fun addOrReplaceCocktails(vararg cocktail: CocktailDbModel)
    suspend fun replaceAllCocktails(vararg cocktail: CocktailDbModel)
    suspend fun deleteCocktails(vararg cocktail: CocktailDbModel)
    suspend fun deleteAllCocktails()
    suspend fun deleteCocktailById(id: Long)
    suspend fun setCocktailFavorite(id: Long, isFavorite: Boolean)

}