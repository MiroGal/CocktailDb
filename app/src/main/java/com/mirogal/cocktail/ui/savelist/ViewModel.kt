package com.mirogal.cocktail.ui.savelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.CocktailRepository

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CocktailRepository.getInstance(application)
    val cocktailList: LiveData<PagedList<CocktailDbEntity>>

    fun deleteCocktail(id: Int) {
        repository?.deleteCocktail(id)
    }

    init {
        cocktailList = repository?.saveCocktailList!!
    }

}