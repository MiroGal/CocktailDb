package com.mirogal.cocktail.presentation.ui.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.mirogal.cocktail.data.repository.source.CocktailRepository
import com.mirogal.cocktail.presentation.mapper.CocktailModelMapper
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel

class DetailViewModel(
        private val cocktailRepository: CocktailRepository,
        private val cocktailModelMapper: CocktailModelMapper,
        viewStateHandle: SavedStateHandle,
        application: Application
) : BaseViewModel(viewStateHandle, application) {

    val cocktailIdLiveData: MutableLiveData<Long> = MutableLiveData()

    val cocktailModelLiveData = cocktailIdLiveData.switchMap { id ->
        cocktailRepository.getCocktailByIdLiveData(id).map { it?.run(cocktailModelMapper::mapTo) }
    }

}