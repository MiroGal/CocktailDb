package com.mirogal.cocktail.presentation.ui.main.drink

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.mirogal.cocktail.data.db.model.CocktailDbModel
import com.mirogal.cocktail.data.repository.CocktailRepository
import com.mirogal.cocktail.presentation.model.drink.DrinkPage
import com.mirogal.cocktail.presentation.model.filter.*
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel
import java.util.*

class DrinkViewModel(application: Application) : BaseViewModel(application) {

    val context = application
    private val repository = CocktailRepository.newInstance(application)

    private val saveCocktailListLiveData: LiveData<List<CocktailDbModel>?> = repository.saveCocktailListLiveData

    val historyCocktailListLiveData: LiveData<List<CocktailDbModel>?>
    val favoriteCocktailListLiveData: LiveData<List<CocktailDbModel>?>

    val cocktailListSizeLiveData: LiveData<Pair<Int, Int>>

    val drinkFilterLiveData: MutableLiveData<HashMap<DrinkFilterType, DrinkFilter>> = MutableLiveData()
    val isDrinkFilterEnableLiveData: LiveData<Boolean>

    val drinkSortLiveData: MutableLiveData<DrinkSort> = MutableLiveData()
    val isDrinkSortEnableLiveData: LiveData<Boolean>

    val currentDrinkPage: MutableLiveData<DrinkPage> = MutableLiveData()

    private val observer: Observer<in List<CocktailDbModel>?> = Observer { }

    init {
        historyCocktailListLiveData = MediatorLiveData<List<CocktailDbModel>?>().apply {
            addSource(saveCocktailListLiveData) {
                if (saveCocktailListLiveData.value != null)
                    value = sortCocktailList(filterCocktailList(saveCocktailListLiveData.value))
            }
            addSource(drinkFilterLiveData) {
                if (saveCocktailListLiveData.value != null)
                    value = sortCocktailList(filterCocktailList(saveCocktailListLiveData.value))
            }
            addSource(drinkSortLiveData) {
                if (saveCocktailListLiveData.value != null)
                    value = sortCocktailList(filterCocktailList(saveCocktailListLiveData.value))
            }
        }

        favoriteCocktailListLiveData = historyCocktailListLiveData.map { list ->
            list?.filter { it.isFavorite }
        }

        cocktailListSizeLiveData = historyCocktailListLiveData.map { list ->
            (list?.size ?: 0) to (favoriteCocktailListLiveData.value?.size ?: 0)
        }

        drinkFilterLiveData.value = hashMapOf(
                Pair(DrinkFilterType.CATEGORY, DrinkFilterCategory.DISABLE),
                Pair(DrinkFilterType.ALCOHOL, DrinkFilterAlcohol.DISABLE),
                Pair(DrinkFilterType.INGREDIENT, DrinkFilterIngredient.DISABLE),
                Pair(DrinkFilterType.GLASS, DrinkFilterGlass.DISABLE))

        isDrinkFilterEnableLiveData = drinkFilterLiveData.map {
            it[DrinkFilterType.CATEGORY] == DrinkFilterCategory.DISABLE
                    && it[DrinkFilterType.ALCOHOL] == DrinkFilterAlcohol.DISABLE
                    && it[DrinkFilterType.INGREDIENT] == DrinkFilterIngredient.DISABLE
                    && it[DrinkFilterType.GLASS] == DrinkFilterGlass.DISABLE
        }.distinctUntilChanged()

        drinkSortLiveData.value = DrinkSort.DISABLE

        isDrinkSortEnableLiveData = drinkSortLiveData.map {
            it == DrinkSort.DISABLE
        }.distinctUntilChanged()

        // For correct work cocktailListSizeLiveData
        favoriteCocktailListLiveData.observeForever(observer)
    }

    override fun onCleared() {
        favoriteCocktailListLiveData.removeObserver(observer)
        super.onCleared()
    }

    private fun filterCocktailList(list: List<CocktailDbModel>?): List<CocktailDbModel>? {
        return list?.filter {
            if (drinkFilterLiveData.value?.get(DrinkFilterType.CATEGORY) != DrinkFilterCategory.DISABLE) {
                it.category?.toLowerCase(Locale.ROOT) == drinkFilterLiveData.value?.get(DrinkFilterType.CATEGORY)?.key?.toLowerCase(Locale.ROOT)
            } else true
        }?.filter {
            if (drinkFilterLiveData.value?.get(DrinkFilterType.ALCOHOL) != DrinkFilterAlcohol.DISABLE) {
                it.alcoholic?.toLowerCase(Locale.ROOT) == drinkFilterLiveData.value?.get(DrinkFilterType.ALCOHOL)?.key?.toLowerCase(Locale.ROOT)
            } else true
        }?.filter {
            if (drinkFilterLiveData.value?.get(DrinkFilterType.INGREDIENT) != DrinkFilterIngredient.DISABLE) {
                it.ingredientList.contains(drinkFilterLiveData.value?.get(DrinkFilterType.INGREDIENT)?.key)
            } else true
        }?.filter {
            if (drinkFilterLiveData.value?.get(DrinkFilterType.GLASS) != DrinkFilterGlass.DISABLE) {
                it.glass?.toLowerCase(Locale.ROOT) == drinkFilterLiveData.value?.get(DrinkFilterType.GLASS)?.key?.toLowerCase(Locale.ROOT)
            } else true
        }
    }

    private fun sortCocktailList(list: List<CocktailDbModel>?): List<CocktailDbModel>? {
        return when(drinkSortLiveData.value) {
            DrinkSort.NAME_ASCENDING -> list?.sortedBy { it.name }
            DrinkSort.NAME_DESCENDING -> list?.sortedByDescending { it.name }
            DrinkSort.ALCOHOL_FIRST -> {
                list?.sortedWith(compareBy(
                        { it.alcoholic?.toLowerCase(Locale.ROOT) == DrinkFilterAlcohol.NON_ALCOHOLIC.key.toLowerCase(Locale.ROOT) },
                        { it.alcoholic?.toLowerCase(Locale.ROOT) == DrinkFilterAlcohol.OPTIONAL_ALCOHOL.key.toLowerCase(Locale.ROOT) },
                        { it.alcoholic?.toLowerCase(Locale.ROOT) == DrinkFilterAlcohol.ALCOHOLIC.key.toLowerCase(Locale.ROOT) }))
            }
            DrinkSort.NON_ALCOHOL_FIRST -> {
                list?.sortedWith(compareBy(
                        { it.alcoholic?.toLowerCase(Locale.ROOT) == DrinkFilterAlcohol.ALCOHOLIC.key.toLowerCase(Locale.ROOT) },
                        { it.alcoholic?.toLowerCase(Locale.ROOT) == DrinkFilterAlcohol.OPTIONAL_ALCOHOL.key.toLowerCase(Locale.ROOT) },
                        { it.alcoholic?.toLowerCase(Locale.ROOT) == DrinkFilterAlcohol.NON_ALCOHOLIC.key.toLowerCase(Locale.ROOT) }))
            }
            DrinkSort.INGREDIENT_COUNT_ASCENDING -> list?.sortedBy { it.ingredientList.filterNotNull().size }
            DrinkSort.INGREDIENT_COUNT_DESCENDING -> list?.sortedByDescending { it.ingredientList.filterNotNull().size }
            else -> list
        }
    }

    fun resetDrinkFilter() {
        drinkFilterLiveData.value = hashMapOf(
                Pair(DrinkFilterType.CATEGORY, DrinkFilterCategory.DISABLE),
                Pair(DrinkFilterType.ALCOHOL, DrinkFilterAlcohol.DISABLE),
                Pair(DrinkFilterType.INGREDIENT, DrinkFilterIngredient.DISABLE),
                Pair(DrinkFilterType.GLASS, DrinkFilterGlass.DISABLE))
    }

    fun deleteCocktail(id: Int) {
        repository.deleteCocktailFromDb(id)
    }

    fun setCocktailStateFavorite(cocktailId: Int, isFavorite: Boolean) {
        repository.setCocktailStateFavorite(cocktailId, isFavorite)
    }

    fun switchCocktailStateFavorite(cocktailId: Int, isFavorite: Boolean) {
        if (isFavorite) {
            repository.setCocktailStateFavorite(cocktailId, false)
        } else {
            repository.setCocktailStateFavorite(cocktailId, true)
        }
    }

}