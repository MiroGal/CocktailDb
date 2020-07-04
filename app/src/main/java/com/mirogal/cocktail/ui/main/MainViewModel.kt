package com.mirogal.cocktail.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.CocktailRepository
import com.mirogal.cocktail.ui.base.BaseViewModel
import com.mirogal.cocktail.ui.main.filter.AlcoholDrinkFilter
import com.mirogal.cocktail.ui.main.filter.CategoryDrinkFilter
import com.mirogal.cocktail.ui.main.filter.DrinkFilter
import com.mirogal.cocktail.ui.main.filter.DrinkFilterType


class MainViewModel(application: Application) : BaseViewModel(application) {

    private val repository = CocktailRepository.newInstance(application)

    private val saveCocktailListLiveData: LiveData<List<CocktailDbEntity>?> = repository.saveCocktailListLiveData
    val historyCocktailListLiveData: LiveData<List<CocktailDbEntity>?>
    val favoriteCocktailListLiveData: LiveData<List<CocktailDbEntity>?>
    val drinkFilterLiveData: MutableLiveData<HashMap<DrinkFilterType, DrinkFilter>?> = MutableLiveData()

//    private val observer: Observer<in List<CocktailDbEntity>?> = Observer {  }

    init {
        drinkFilterLiveData.value = hashMapOf(
                Pair(DrinkFilterType.ALCOHOL, AlcoholDrinkFilter.DISABLE),
                Pair(DrinkFilterType.CATEGORY, CategoryDrinkFilter.DISABLE))

        historyCocktailListLiveData = MediatorLiveData<List<CocktailDbEntity>?>().apply {
            addSource(saveCocktailListLiveData) {
                if (saveCocktailListLiveData.value != null) {
                    if (drinkFilterLiveData.value == null || drinkFilterLiveData.value?.get(DrinkFilterType.ALCOHOL) == AlcoholDrinkFilter.DISABLE) {
                        value = saveCocktailListLiveData.value
                    } else {
                        value = saveCocktailListLiveData.value?.filter { it.alcoholic == drinkFilterLiveData.value?.get(DrinkFilterType.ALCOHOL)?.key }
                    }
                }
            }
            addSource(drinkFilterLiveData) {
                if (saveCocktailListLiveData.value != null) {
                    if (drinkFilterLiveData.value == null || drinkFilterLiveData.value?.get(DrinkFilterType.ALCOHOL) == AlcoholDrinkFilter.DISABLE) {
                        value = saveCocktailListLiveData.value
                    } else {
                        value = saveCocktailListLiveData.value?.filter { it.alcoholic == drinkFilterLiveData.value?.get(DrinkFilterType.ALCOHOL)?.key }
                    }
                }
            }
        }

        favoriteCocktailListLiveData = MediatorLiveData<List<CocktailDbEntity>?>().apply {
            addSource(historyCocktailListLiveData) {
                if (historyCocktailListLiveData.value != null) {
                    value = historyCocktailListLiveData.value?.filter { it.isFavorite }
                }
            }
        }
//        historyCocktailListLiveData.observeForever(observer)
//        favoriteCocktailListLiveData.observeForever(observer)
    }

    override fun onCleared() {
//        historyCocktailListLiveData.removeObserver(observer)
//        favoriteCocktailListLiveData.removeObserver(observer)
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