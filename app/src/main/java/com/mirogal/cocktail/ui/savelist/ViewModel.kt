package com.mirogal.cocktail.ui.savelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.CocktailRepository

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CocktailRepository.getInstance(application)
    val cocktailList: LiveData<List<CocktailDbEntity>>

    init {
        cocktailList = repository?.saveCocktailList!!
    }

    fun deleteCocktail(id: Int) {
        repository?.deleteCocktail(id)
    }

    fun switchFavorite(cocktail: CocktailDbEntity?) {
        if (cocktail!!.isFavorite) {
            repository?.setFavorite(cocktail.id, false)
        } else {
            repository?.setFavorite(cocktail.id, true)
        }
    }

}