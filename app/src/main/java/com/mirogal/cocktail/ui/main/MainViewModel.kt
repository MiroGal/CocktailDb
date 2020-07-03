package com.mirogal.cocktail.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.CocktailRepository
import com.mirogal.cocktail.ui.auth.AuthDataValidStatus
import com.mirogal.cocktail.ui.base.BaseViewModel
import com.mirogal.cocktail.ui.main.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.ui.main.filter.CategoryDrinkFilter


class MainViewModel(application: Application) : BaseViewModel(application) {

    private val repository = CocktailRepository.newInstance(application)

    val cocktailListLiveData: MediatorLiveData<List<CocktailDbEntity>?>
    private val saveCocktailListLiveData: LiveData<List<CocktailDbEntity>?> = repository.saveCocktailListLiveData
    val alcoholDrinkFilterLiveData: MutableLiveData<AlcoholDrinkFilter?> = MutableLiveData()
    val categoryDrinkFilterLiveData: MutableLiveData<CategoryDrinkFilter?> = MutableLiveData()

    private val observer: Observer<in List<CocktailDbEntity>?> = Observer {  }

    init {
        cocktailListLiveData = MediatorLiveData<List<CocktailDbEntity>?>().apply {
            addSource(saveCocktailListLiveData) {
                if (saveCocktailListLiveData.value != null) {
                    if (alcoholDrinkFilterLiveData.value == null || alcoholDrinkFilterLiveData.value == AlcoholDrinkFilter.DISABLE) {
                        value = saveCocktailListLiveData.value
                    } else {
                        value = saveCocktailListLiveData.value?.filter { it.alcoholic == alcoholDrinkFilterLiveData.value?.key }
                    }
                }
            }
            addSource(alcoholDrinkFilterLiveData) {
                if (saveCocktailListLiveData.value != null) {
                    if (alcoholDrinkFilterLiveData.value == null || alcoholDrinkFilterLiveData.value == AlcoholDrinkFilter.DISABLE) {
                        value = saveCocktailListLiveData.value
                    } else {
                        value = saveCocktailListLiveData.value?.filter { it.alcoholic == alcoholDrinkFilterLiveData.value?.key }
                    }
                }
            }
        }
        cocktailListLiveData.observeForever(observer)
    }

    override fun onCleared() {
        cocktailListLiveData.removeObserver(observer)
        super.onCleared()
    }

    fun deleteCocktailFromDb(id: Int) {
        repository.deleteCocktailFromDb(id)
    }

    fun switchCocktailFavoriteStatus(cocktailId: Int, isFavorite: Boolean) {
        if (isFavorite) {
            repository.setCocktailFavoriteStatus(cocktailId, false)
        } else {
            repository.setCocktailFavoriteStatus(cocktailId, true)
        }
    }

}