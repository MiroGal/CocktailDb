package com.mirogal.cocktail.presentation.ui.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mirogal.cocktail.data.repository.source.CocktailRepository
import com.mirogal.cocktail.extension.log
import com.mirogal.cocktail.presentation.extension.debounce
import com.mirogal.cocktail.presentation.mapper.CocktailModelMapper
import com.mirogal.cocktail.presentation.model.cocktail.CocktailModel
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.Job

class SearchViewModel(
        private val cocktailRepository: CocktailRepository,
        private val cocktailModelMapper: CocktailModelMapper,
        viewStateHandle: SavedStateHandle,
        application: Application
) : BaseViewModel(viewStateHandle, application) {

    private var searchJob: Job? = null

    val searchResultCocktailListLiveData: LiveData<List<CocktailModel>> = MutableLiveData(emptyList())

    val searchQueryLiveData = MutableLiveData<String>(null)
    private val searchQueryDebounceLiveData =
            searchQueryLiveData.map { "LOG $it (${System.currentTimeMillis()})".log; it }
                    .debounce(1000L)
    private val searchTriggerObserver = Observer<String?> { query ->
        "LOG debounce $query (${System.currentTimeMillis()})".log
        searchCocktail(query)
    }

    init {
        searchQueryDebounceLiveData.observeForever(searchTriggerObserver)
    }

    override fun onCleared() {
        super.onCleared()
        searchQueryDebounceLiveData.removeObserver(searchTriggerObserver)
    }

    private fun searchCocktail(query: String?) {
        if (searchJob?.isActive == true) searchJob?.cancel()
        searchJob = launchRequest(searchResultCocktailListLiveData) {
            when {
                query.isNullOrEmpty() -> emptyList()
                else -> {
                    cocktailRepository
                            .searchCocktailRemote(query)
                            .map(cocktailModelMapper::mapTo)
                }
            }
        }
    }

    fun saveCocktail(cocktail: CocktailModel) {
        launchRequest {
            cocktailRepository.addOrReplaceCocktail(cocktail.run(cocktailModelMapper::mapFrom))
        }
    }

}