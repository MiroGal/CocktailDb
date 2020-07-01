package com.mirogal.cocktail.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.CocktailRepository
import com.mirogal.cocktail.ui.base.BaseViewModel
import com.mirogal.cocktail.ui.main.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.ui.main.filter.CategoryDrinkFilter


class MainViewModel(application: Application) : BaseViewModel(application) {

    private val repository = CocktailRepository.newInstance(application)

    val alcoholDrinkFilterLiveData: MutableLiveData<AlcoholDrinkFilter?> = MutableLiveData()
    val categoryDrinkFilterLiveData: MutableLiveData<CategoryDrinkFilter?> = MutableLiveData()

//    val cocktailListLiveData: LiveData<List<CocktailDbEntity>> = MediatorLiveData<List<CocktailDbEntity>>().apply {
//        addSource(alcoholDrinkFilterLiveData) {
//            value = repository.saveCocktailListLiveData.value
//        }
//        addSource(categoryDrinkFilterLiveData) {
//            value = repository.saveCocktailListLiveData.value
//        }
//    }

    val cocktailListLiveData = Transformations.switchMap(alcoholDrinkFilterLiveData) { it ->
        if (it == null || it == AlcoholDrinkFilter.DISABLE) {
            repository.saveCocktailListLiveData
        } else {
            when (it) {
                AlcoholDrinkFilter.ALCOHOLIC -> {
                    Transformations.map(repository.saveCocktailListLiveData) { list ->
                        list.filter { it.alcoholic!!.contains(AlcoholDrinkFilter.ALCOHOLIC.key) }
                    }
                }
                AlcoholDrinkFilter.NON_ALCOHOLIC -> {
                    Transformations.map(repository.saveCocktailListLiveData) { list ->
                        list.filter { it.alcoholic!!.contains(AlcoholDrinkFilter.NON_ALCOHOLIC.key) }
                    }
                }
                AlcoholDrinkFilter.OPTIONAL_ALCOHOL -> {
                    Transformations.map(repository.saveCocktailListLiveData) { list ->
                        list.filter { it.alcoholic!!.contains(AlcoholDrinkFilter.OPTIONAL_ALCOHOL.key) }
                    }
                }
                else -> repository.saveCocktailListLiveData
            }
        }
    }

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