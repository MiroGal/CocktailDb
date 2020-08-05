package com.mirogal.cocktail.data.repository.source

import androidx.lifecycle.LiveData
import com.mirogal.cocktail.data.repository.model.CocktailRepoModel
import com.mirogal.cocktail.data.repository.source.base.BaseRepository

interface CocktailRepository : BaseRepository {

    val cocktailListLiveData: LiveData<List<CocktailRepoModel>>
    suspend fun hasCocktails(): Boolean
    suspend fun getFirstCocktail(): CocktailRepoModel?
    fun getCocktailByIdLiveData(id: Long): LiveData<CocktailRepoModel?>
    suspend fun getCocktailById(id: Long): CocktailRepoModel?
    suspend fun addOrReplaceCocktail(cocktail: CocktailRepoModel)
    suspend fun addOrReplaceCocktails(vararg cocktail: CocktailRepoModel)
    suspend fun replaceAllCocktails(vararg cocktail: CocktailRepoModel)
    suspend fun deleteCocktails(vararg cocktail: CocktailRepoModel)
    suspend fun deleteAllCocktails()

    suspend fun searchCocktailRemote(query: String): List<CocktailRepoModel>

}