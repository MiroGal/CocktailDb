package com.mirogal.cocktail.presentation.ui.main.drink

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.mirogal.cocktail.data.repository.source.CocktailRepository
import com.mirogal.cocktail.presentation.constant.DrinkPage
import com.mirogal.cocktail.presentation.constant.filter.*
import com.mirogal.cocktail.presentation.mapper.CocktailModelMapper
import com.mirogal.cocktail.presentation.model.cocktail.CocktailModel
import com.mirogal.cocktail.presentation.receiver.BatteryChangeReceiverLiveData
import com.mirogal.cocktail.presentation.receiver.ProposeDrinkReceiverLiveData
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel
import java.util.*

class DrinkViewModel(
        private val cocktailRepository: CocktailRepository,
        private val cocktailModelMapper: CocktailModelMapper,
        viewStateHandle: SavedStateHandle,
        application: Application
) : BaseViewModel(viewStateHandle, application) {

    private val savedCocktailListLiveData: LiveData<List<CocktailModel>> =
            cocktailRepository.cocktailListLiveData.map(cocktailModelMapper::mapTo)

    val historyCocktailListLiveData: LiveData<List<CocktailModel>?>
    val favoriteCocktailListLiveData: LiveData<List<CocktailModel>?>

    val cocktailListSizeLiveData: LiveData<Pair<Int, Int>>

    val drinkFilterLiveData: MutableLiveData<HashMap<DrinkFilterType, DrinkFilter>> by stateHandle(drinkFilterInitialValue)
    private val savedDrinkFilterLiveData: MutableLiveData<HashMap<DrinkFilterType, DrinkFilter>> by stateHandle(drinkFilterInitialValue)
    val drinkSortLiveData: MutableLiveData<DrinkSort> by stateHandle(drinkSortInitialValue)

    val isDrinkFilterEnableLiveData: LiveData<Boolean>
    val isDrinkSortEnableLiveData: LiveData<Boolean>

    val currentDrinkPageLiveData: MutableLiveData<DrinkPage> by stateHandle(currentDrinkPageInitialValue)

    val proposeDrinkReceiverLiveData = ProposeDrinkReceiverLiveData(application)
    val batteryChangeReceiverLiveData = BatteryChangeReceiverLiveData(application)

    private val favoriteCocktailListObserver: Observer<in List<CocktailModel>?> = Observer { }

    companion object {
        val currentDrinkPageInitialValue: DrinkPage = DrinkPage.HISTORY
        val drinkSortInitialValue: DrinkSort = DrinkSort.DISABLE
        val drinkFilterInitialValue: HashMap<DrinkFilterType, DrinkFilter> = hashMapOf(
                Pair(DrinkFilterType.CATEGORY, DrinkFilterCategory.DISABLE),
                Pair(DrinkFilterType.ALCOHOL, DrinkFilterAlcohol.DISABLE),
                Pair(DrinkFilterType.INGREDIENT, DrinkFilterIngredient.DISABLE),
                Pair(DrinkFilterType.GLASS, DrinkFilterGlass.DISABLE))
    }

    init {
        historyCocktailListLiveData = MediatorLiveData<List<CocktailModel>?>().apply {
            addSource(savedCocktailListLiveData) {
                if (savedCocktailListLiveData.value != null)
                    value = sortCocktailList(filterCocktailList(savedCocktailListLiveData.value))
            }
            addSource(drinkFilterLiveData) {
                if (savedCocktailListLiveData.value != null)
                    value = sortCocktailList(filterCocktailList(savedCocktailListLiveData.value))
            }
            addSource(drinkSortLiveData) {
                if (savedCocktailListLiveData.value != null)
                    value = sortCocktailList(filterCocktailList(savedCocktailListLiveData.value))
            }
        }

        favoriteCocktailListLiveData = historyCocktailListLiveData.map { list ->
            list?.filter { it.isFavorite }
        }

        cocktailListSizeLiveData = historyCocktailListLiveData.map { list ->
            (list?.size ?: 0) to (favoriteCocktailListLiveData.value?.size ?: 0)
        }

        isDrinkFilterEnableLiveData = drinkFilterLiveData.map {
            it[DrinkFilterType.CATEGORY] == DrinkFilterCategory.DISABLE
                    && it[DrinkFilterType.ALCOHOL] == DrinkFilterAlcohol.DISABLE
                    && it[DrinkFilterType.INGREDIENT] == DrinkFilterIngredient.DISABLE
                    && it[DrinkFilterType.GLASS] == DrinkFilterGlass.DISABLE
        }.distinctUntilChanged()

        isDrinkSortEnableLiveData = drinkSortLiveData.map {
            it == DrinkSort.DISABLE
        }.distinctUntilChanged()

        //for correct work cocktailListSizeLiveData
        favoriteCocktailListLiveData.observeForever(favoriteCocktailListObserver)
    }

    override fun onCleared() {
        favoriteCocktailListLiveData.removeObserver(favoriteCocktailListObserver)
        super.onCleared()
    }

    private fun filterCocktailList(list: List<CocktailModel>?): List<CocktailModel>? {
        return list?.filter {
            if (drinkFilterLiveData.value?.get(DrinkFilterType.CATEGORY) != DrinkFilterCategory.DISABLE) {
                it.category.key.equals(drinkFilterLiveData.value?.get(DrinkFilterType.CATEGORY)?.key, true)
            } else true
        }?.filter {
            if (drinkFilterLiveData.value?.get(DrinkFilterType.ALCOHOL) != DrinkFilterAlcohol.DISABLE) {
                it.alcoholType.key.equals(drinkFilterLiveData.value?.get(DrinkFilterType.ALCOHOL)?.key, true)
            } else true
        }?.filter {
            if (drinkFilterLiveData.value?.get(DrinkFilterType.INGREDIENT) != DrinkFilterIngredient.DISABLE) {
                it.ingredientsWithMeasures.containsKey(drinkFilterLiveData.value?.get(DrinkFilterType.INGREDIENT)?.key)
            } else true
        }?.filter {
            if (drinkFilterLiveData.value?.get(DrinkFilterType.GLASS) != DrinkFilterGlass.DISABLE) {
                it.glass.key.equals(drinkFilterLiveData.value?.get(DrinkFilterType.GLASS)?.key, true)
            } else true
        }
    }

    private fun sortCocktailList(list: List<CocktailModel>?): List<CocktailModel>? {
        return when(drinkSortLiveData.value) {
            DrinkSort.NAME_ASCENDING -> list?.sortedBy { it.names.baseValue }
            DrinkSort.NAME_DESCENDING -> list?.sortedByDescending { it.names.baseValue }
            DrinkSort.ALCOHOL_FIRST -> {
                list?.sortedWith(compareBy(
                        { it.alcoholType.key == DrinkFilterAlcohol.NON_ALCOHOLIC.key.toLowerCase(Locale.ROOT) },
                        { it.alcoholType.key == DrinkFilterAlcohol.OPTIONAL_ALCOHOL.key.toLowerCase(Locale.ROOT) },
                        { it.alcoholType.key == DrinkFilterAlcohol.ALCOHOLIC.key.toLowerCase(Locale.ROOT) }))
            }
            DrinkSort.NON_ALCOHOL_FIRST -> {
                list?.sortedWith(compareBy(
                        { it.alcoholType.key == DrinkFilterAlcohol.ALCOHOLIC.key.toLowerCase(Locale.ROOT) },
                        { it.alcoholType.key == DrinkFilterAlcohol.OPTIONAL_ALCOHOL.key.toLowerCase(Locale.ROOT) },
                        { it.alcoholType.key == DrinkFilterAlcohol.NON_ALCOHOLIC.key.toLowerCase(Locale.ROOT) }))
            }
            DrinkSort.INGREDIENT_COUNT_ASCENDING -> list?.sortedBy { it.ingredientsWithMeasures.size }
            DrinkSort.INGREDIENT_COUNT_DESCENDING -> list?.sortedByDescending { it.ingredientsWithMeasures.size }
            else -> list
        }
    }

    fun setDrinkFilter(filterList: HashMap<DrinkFilterType, DrinkFilter>) {
        savedDrinkFilterLiveData.value = drinkFilterLiveData.value
        drinkFilterLiveData.value = filterList
    }

    fun resetDrinkFilter() {
        savedDrinkFilterLiveData.value = drinkFilterLiveData.value
        drinkFilterLiveData.value = drinkFilterInitialValue
    }

    fun backDrinkFilter() {
        drinkFilterLiveData.value = savedDrinkFilterLiveData.value
    }

    fun deleteCocktail(id: Long) {
        launchRequest {
            cocktailRepository.deleteCocktailById(id)
        }
    }

    fun setCocktailFavorite(cocktailId: Long, isFavorite: Boolean) {
        launchRequest {
            cocktailRepository.setCocktailFavorite(cocktailId, isFavorite)
        }
    }

    fun switchCocktailFavorite(cocktailId: Long, isFavorite: Boolean) {
        launchRequest {
            if (isFavorite) {
                cocktailRepository.setCocktailFavorite(cocktailId, false)
            } else {
                cocktailRepository.setCocktailFavorite(cocktailId, true)
            }
        }
    }

}