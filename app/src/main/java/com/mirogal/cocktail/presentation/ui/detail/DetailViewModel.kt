package com.mirogal.cocktail.presentation.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mirogal.cocktail.presentation.mapper.CocktailModelMapper
import com.mirogal.cocktail.presentation.model.cocktail.CocktailModel

class DetailViewModel(
        private val cocktailRepository: com.mirogal.cocktail.data.repository.source.CocktailRepository,
        private val cocktailModelMapper: CocktailModelMapper,
        viewStateHandle: SavedStateHandle,
        application: Application
) : com.mirogal.cocktail.presentation.ui.base.BaseViewModel(viewStateHandle, application) {

    val cocktailIdLiveData: MutableLiveData<Long> = MutableLiveData()

    val cocktailLiveData: LiveData<CocktailModel?>

    init {
        cocktailLiveData = MediatorLiveData<CocktailModel?>().apply {
            addSource(cocktailIdLiveData) {
                launchRequest {
                    postValue(cocktailRepository.getCocktailById(it)?.run(cocktailModelMapper::mapTo))
                }
            }
        }
    }

}