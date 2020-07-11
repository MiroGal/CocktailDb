package com.mirogal.cocktail.presentation.ui.main.drink

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mirogal.cocktail.R
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

    val drinkFilterLiveData: MutableLiveData<HashMap<DrinkFilterType, DrinkFilter>?> = MutableLiveData()
    val isDrinkFilterEnableLiveData: LiveData<Boolean>

    val drinkSortLiveData: MutableLiveData<DrinkSort> = MutableLiveData()
    val isDrinkSortEnableLiveData: LiveData<Boolean>

    val currentDrinkPage: MutableLiveData<DrinkPage> = MutableLiveData()
    val filterButtonResultTextLiveData: LiveData<String>

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

        favoriteCocktailListLiveData = Transformations.map(historyCocktailListLiveData) { list ->
            list?.filter { it.isFavorite }
        }

        drinkFilterLiveData.value = hashMapOf(
                Pair(DrinkFilterType.CATEGORY, DrinkFilterCategory.DISABLE),
                Pair(DrinkFilterType.ALCOHOL, DrinkFilterAlcohol.DISABLE),
                Pair(DrinkFilterType.INGREDIENT, DrinkFilterIngredient.DISABLE),
                Pair(DrinkFilterType.GLASS, DrinkFilterGlass.DISABLE))

        isDrinkFilterEnableLiveData = MediatorLiveData<Boolean>().apply {
            addSource(drinkFilterLiveData) {
                value = drinkFilterLiveData.value?.get(DrinkFilterType.CATEGORY) == DrinkFilterCategory.DISABLE
                        && drinkFilterLiveData.value?.get(DrinkFilterType.ALCOHOL) == DrinkFilterAlcohol.DISABLE
                        && drinkFilterLiveData.value?.get(DrinkFilterType.INGREDIENT) == DrinkFilterIngredient.DISABLE
                        && drinkFilterLiveData.value?.get(DrinkFilterType.GLASS) == DrinkFilterGlass.DISABLE
            }
        }

        drinkSortLiveData.value = DrinkSort.DISABLE

        isDrinkSortEnableLiveData = MediatorLiveData<Boolean>().apply {
            addSource(drinkSortLiveData) {
                value = drinkSortLiveData.value == DrinkSort.DISABLE
            }
        }

        filterButtonResultTextLiveData = MediatorLiveData<String>().apply {
            addSource(currentDrinkPage) {
                value = getFilterButtonResultText()
            }
            addSource(historyCocktailListLiveData) {
                value = getFilterButtonResultText()
            }
            addSource(favoriteCocktailListLiveData) {
                value = getFilterButtonResultText()
            }
        }
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

    private fun getFilterButtonResultText(): String {
        return if (currentDrinkPage.value == DrinkPage.HISTORY) {
            if (historyCocktailListLiveData.value != null && historyCocktailListLiveData.value!!.isNotEmpty()) {
                context.getString(R.string.drink_filter_btn_result_found) + " " + historyCocktailListLiveData.value!!.size.toString()
            } else {
                context.getString(R.string.drink_filter_btn_result_not_found)
            }
        } else {
            if (favoriteCocktailListLiveData.value != null && favoriteCocktailListLiveData.value!!.isNotEmpty()) {
                context.getString(R.string.drink_filter_btn_result_found) + " " + favoriteCocktailListLiveData.value!!.size.toString()
            } else {
                context.getString(R.string.drink_filter_btn_result_not_found)
            }
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

    fun switchCocktailStateFavorite(cocktailId: Int, isFavorite: Boolean) {
        if (isFavorite) {
            repository.setCocktailStateFavorite(cocktailId, false)
        } else {
            repository.setCocktailStateFavorite(cocktailId, true)
        }
    }

}