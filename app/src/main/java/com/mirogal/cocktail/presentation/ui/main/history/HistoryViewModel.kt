package com.mirogal.cocktail.presentation.ui.main.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mirogal.cocktail.data.db.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.CocktailRepository
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel
import com.mirogal.cocktail.presentation.ui.main.history.constant.*


class HistoryViewModel(application: Application) : BaseViewModel(application) {

    private val repository = CocktailRepository.newInstance(application)

    private val saveCocktailListLiveData: LiveData<List<CocktailDbEntity>?> = repository.saveCocktailListLiveData

    val historyCocktailListLiveData: LiveData<List<CocktailDbEntity>?>
    val favoriteCocktailListLiveData: LiveData<List<CocktailDbEntity>?>
    val drinkFilterLiveData: MutableLiveData<HashMap<DrinkFilterType, DrinkFilter>?> = MutableLiveData()
    val isDrinkFilterEmptyLiveData: LiveData<Boolean>
    val currentHistoryPage: MutableLiveData<HistoryPage> = MutableLiveData()
    val filterResultStringLiveData: LiveData<String>

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

        isDrinkFilterEmptyLiveData = MediatorLiveData<Boolean>().apply {
            addSource(drinkFilterLiveData) {
                value = drinkFilterLiveData.value?.get(DrinkFilterType.ALCOHOL) == AlcoholDrinkFilter.DISABLE
                        && drinkFilterLiveData.value?.get(DrinkFilterType.CATEGORY) == CategoryDrinkFilter.DISABLE
            }
        }

        filterResultStringLiveData = MediatorLiveData<String>().apply {
            addSource(currentHistoryPage) {
                value = getFilterResultString()
            }
            addSource(historyCocktailListLiveData) {
                value = getFilterResultString()
            }
            addSource(favoriteCocktailListLiveData) {
                value = getFilterResultString()
            }
        }
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

    private fun getFilterResultString(): String {
        return if (currentHistoryPage.value == HistoryPage.HISTORY) {
            if (historyCocktailListLiveData.value != null && historyCocktailListLiveData.value!!.isNotEmpty()) {
                "Результати ${historyCocktailListLiveData.value!!.size}"
            } else {
                "Не знайдено"
            }
        } else {
            if (favoriteCocktailListLiveData.value != null && favoriteCocktailListLiveData.value!!.isNotEmpty()) {
                "Результати ${favoriteCocktailListLiveData.value!!.size}"
            } else {
                "Не знайдено"
            }
        }
    }

    fun resetDrinkFilter() {
        val drinkFilter = drinkFilterLiveData.value
        drinkFilter?.put(DrinkFilterType.ALCOHOL, AlcoholDrinkFilter.DISABLE)
        drinkFilter?.put(DrinkFilterType.CATEGORY, CategoryDrinkFilter.DISABLE)
        drinkFilterLiveData.value = drinkFilter
    }

    fun deleteCocktail(id: Int) {
        repository.deleteCocktailFromDb(id)
    }

    fun switchCocktailStateFavorite(cocktailId: Int, isFavorite: Boolean) {
        if (isFavorite) {
            repository.setCocktailStateFavorite(cocktailId, false)
        } else {
            repository.setCocktailStateFavorite(cocktailId, true)
        }
    }

}