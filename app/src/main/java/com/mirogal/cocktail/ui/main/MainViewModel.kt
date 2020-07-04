package com.mirogal.cocktail.ui.main

import android.app.Application
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
        historyCocktailListLiveData = MediatorLiveData<List<CocktailDbEntity>?>().apply {
            addSource(saveCocktailListLiveData) {
                if (saveCocktailListLiveData.value != null)
                    value = filterCocktailList(saveCocktailListLiveData.value)
            }
            addSource(drinkFilterLiveData) {
                if (saveCocktailListLiveData.value != null)
                    value = filterCocktailList(saveCocktailListLiveData.value)
            }
        }

        favoriteCocktailListLiveData = Transformations.map(historyCocktailListLiveData) { list ->
            list?.filter { it.isFavorite }
        }

        drinkFilterLiveData.value = hashMapOf(
                Pair(DrinkFilterType.ALCOHOL, AlcoholDrinkFilter.DISABLE),
                Pair(DrinkFilterType.CATEGORY, CategoryDrinkFilter.DISABLE))

//        historyCocktailListLiveData.observeForever(observer)
//        favoriteCocktailListLiveData.observeForever(observer)
    }

    override fun onCleared() {
//        historyCocktailListLiveData.removeObserver(observer)
//        favoriteCocktailListLiveData.removeObserver(observer)
        super.onCleared()
    }

    private fun filterCocktailList(list: List<CocktailDbEntity>?): List<CocktailDbEntity>? {
        return list?.filter {
                if (drinkFilterLiveData.value?.get(DrinkFilterType.ALCOHOL) != AlcoholDrinkFilter.DISABLE) {
                    it.alcoholic == drinkFilterLiveData.value?.get(DrinkFilterType.ALCOHOL)?.key
                } else true
            }?.filter {
                if (drinkFilterLiveData.value?.get(DrinkFilterType.CATEGORY) != CategoryDrinkFilter.DISABLE) {
                    it.category == drinkFilterLiveData.value?.get(DrinkFilterType.CATEGORY)?.key
                } else true
            }
    }

    fun deleteCocktail(id: Int) {
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