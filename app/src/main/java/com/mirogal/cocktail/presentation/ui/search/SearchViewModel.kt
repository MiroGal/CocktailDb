package com.mirogal.cocktail.presentation.ui.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mirogal.cocktail.data.repository.source.CocktailRepository
import com.mirogal.cocktail.presentation.mapper.CocktailModelMapper
import com.mirogal.cocktail.presentation.model.cocktail.CocktailModel
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel

class SearchViewModel(
        private val cocktailRepository: CocktailRepository,
        private val cocktailModelMapper: CocktailModelMapper,
        viewStateHandle: SavedStateHandle,
        application: Application
) : BaseViewModel(viewStateHandle, application) {

    val cocktailListLiveData: LiveData<List<CocktailModel>>
    val searchStringLiveData: MutableLiveData<String?> = MutableLiveData()

    init {
        cocktailListLiveData = MediatorLiveData<List<CocktailModel>>().apply {
            addSource(searchStringLiveData) {
                launchRequest {
                    if (it.isNullOrEmpty()) {
                        postValue(mutableListOf())
                    } else {
                        postValue(cocktailRepository.getCocktailListByName(it).map(cocktailModelMapper::mapTo))
                    }
                }
            }
        }
        cocktailListLiveData.value = null
    }

    fun setSearchString(search: String?) {
        searchStringLiveData.value = search
    }

    fun saveCocktail(cocktail: CocktailModel) {
        Log.d(">>>", "VM, id: ${cocktail.id}")
        launchRequest {
            cocktailRepository.addOrReplaceCocktail(cocktailModelMapper.mapFrom(cocktail))
        }
    }

}