package com.mirogal.cocktail.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.CocktailRepository
import com.mirogal.cocktail.ui.base.BaseViewModel


class MainViewModel(application: Application) : BaseViewModel(application) {

    private val repository = CocktailRepository.newInstance(application)

    val cocktailListViewModel: LiveData<List<CocktailDbEntity>> = repository.saveCocktailListLiveData

    fun deleteCocktailFromDb(id: Int) {
        repository.deleteCocktailFromDb(id)
    }

    fun switchCocktailFavoriteStatus(cocktail: CocktailDbEntity?) {
        if (cocktail!!.isFavorite) {
            repository.setCocktailFavoriteStatus(cocktail.id, false)
        } else {
            repository.setCocktailFavoriteStatus(cocktail.id, true)
        }
    }

}