package com.mirogal.cocktail.data.repository.impl.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mirogal.cocktail.data.db.source.CocktailDbSource
import com.mirogal.cocktail.data.network.source.CocktailNetSource
import com.mirogal.cocktail.data.repository.impl.mapper.CocktailRepoModelMapper
import com.mirogal.cocktail.data.repository.impl.source.base.BaseRepositoryImpl
import com.mirogal.cocktail.data.repository.model.CocktailRepoModel
import com.mirogal.cocktail.data.repository.source.CocktailRepository

class CocktailRepositoryImpl(
        private val dbSource: CocktailDbSource,
        private val netSource: CocktailNetSource,
        private val mapper: CocktailRepoModelMapper
) : BaseRepositoryImpl(),
        CocktailRepository {

    override val cocktailListLiveData: LiveData<List<CocktailRepoModel>> =
            dbSource.cocktailListLiveData.map(mapper::mapDbToRepo)

    override suspend fun hasCocktails() = dbSource.hasCocktails()

    override suspend fun getFirstCocktail(): CocktailRepoModel? {
        return dbSource.getFirstCocktail()?.run(mapper::mapDbToRepo)
    }

    override fun getCocktailByIdLiveData(id: Long): LiveData<CocktailRepoModel?> {
        return dbSource.getCocktailByIdLiveData(id).map { it?.run(mapper::mapDbToRepo) }
    }

    override suspend fun getCocktailById(id: Long): CocktailRepoModel? {
        return dbSource.getCocktailById(id)?.run(mapper::mapDbToRepo)
    }

    override suspend fun addOrReplaceCocktail(cocktail: CocktailRepoModel) {
        dbSource.addOrReplaceCocktail(cocktail.run(mapper::mapRepoToDb))
    }

    override suspend fun addOrReplaceCocktails(vararg cocktail: CocktailRepoModel) {
        dbSource.addOrReplaceCocktails(
                *cocktail.map(mapper::mapRepoToDb).toTypedArray()
        )
    }

    override suspend fun replaceAllCocktails(vararg cocktail: CocktailRepoModel) {
        dbSource.replaceAllCocktails(
                *cocktail.map(mapper::mapRepoToDb).toTypedArray()
        )
    }

    override suspend fun deleteCocktails(vararg cocktail: CocktailRepoModel) {
        dbSource.deleteCocktails(
                *cocktail.map(mapper::mapRepoToDb).toTypedArray()
        )
    }

    override suspend fun deleteAllCocktails() {
        dbSource.deleteAllCocktails()
    }

    override suspend fun searchCocktailRemote(query: String): List<CocktailRepoModel> {
        return netSource.searchCocktail(query).run(mapper::mapNetToRepo)
    }

    override suspend fun deleteCocktailById(id: Long) {
        dbSource.deleteCocktailById(id)
    }

    override suspend fun setCocktailFavorite(id: Long, isFavorite: Boolean) {
        dbSource.setCocktailFavorite(id, isFavorite)
    }

}