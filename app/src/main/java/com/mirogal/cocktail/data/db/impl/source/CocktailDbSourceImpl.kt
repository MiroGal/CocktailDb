package com.mirogal.cocktail.data.db.impl.source

import androidx.lifecycle.LiveData
import com.mirogal.cocktail.data.db.impl.dao.CocktailDao
import com.mirogal.cocktail.data.db.model.CocktailDbModel
import com.mirogal.cocktail.data.db.source.CocktailDbSource

class CocktailDbSourceImpl(
        private val cocktailDao: CocktailDao
) : CocktailDbSource {

    override val cocktailListLiveData: LiveData<List<CocktailDbModel>> =
            cocktailDao.cocktailListLiveData

    override suspend fun hasCocktails() = cocktailDao.getFirstCocktail() != null

    override suspend fun getFirstCocktail() = cocktailDao.getFirstCocktail()

    override fun getCocktailByIdLiveData(id: Long): LiveData<CocktailDbModel?> {
        return cocktailDao.getCocktailByIdLiveData(id)
    }

    override suspend fun getCocktailById(id: Long) = cocktailDao.getCocktailById(id)

    override suspend fun addOrReplaceCocktail(cocktail: CocktailDbModel) {
        cocktailDao.addOrReplaceCocktail(cocktail)
    }

    override suspend fun addOrReplaceCocktails(vararg cocktail: CocktailDbModel) {
        cocktailDao.addOrReplaceCocktails(*cocktail)
    }

    override suspend fun replaceAllCocktails(vararg cocktail: CocktailDbModel) {
        cocktailDao.replaceAllCocktails(*cocktail)
    }

    override suspend fun deleteCocktails(vararg cocktail: CocktailDbModel) {
        cocktailDao.deleteCocktails(*cocktail)
    }

    override suspend fun deleteAllCocktails() {
        cocktailDao.deleteAllCocktails()
    }

    override suspend fun deleteCocktailById(id: Long) {
        cocktailDao.deleteCocktailById(id)
    }

    override suspend fun setCocktailFavorite(id: Long, isFavorite: Boolean) {
        cocktailDao.setCocktailFavorite(id, isFavorite)
    }

}