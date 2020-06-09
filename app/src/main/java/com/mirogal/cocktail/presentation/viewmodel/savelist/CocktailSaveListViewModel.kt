package com.mirogal.cocktail.presentation.viewmodel.savelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.CocktailRepository

class CocktailSaveListViewModel(application: Application) : AndroidViewModel(application) {

    val cocktailList: LiveData<PagedList<CocktailDbEntity?>>
    private val repository: CocktailRepository = CocktailRepository.getInstance(application)

    fun deleteCocktail(id: Int) {
        repository.deleteCocktail(id)
    }

    init {
        cocktailList = repository.saveCocktailList
    }

}