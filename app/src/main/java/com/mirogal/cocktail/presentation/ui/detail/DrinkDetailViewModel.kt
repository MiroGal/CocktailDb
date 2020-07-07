package com.mirogal.cocktail.presentation.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mirogal.cocktail.data.db.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.CocktailRepository
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel


class DrinkDetailViewModel(application: Application) : BaseViewModel(application) {

    private val repository = CocktailRepository.newInstance(application)

    val cocktailIdLiveData: MutableLiveData<Int> = MutableLiveData()

    val cocktailLiveData: LiveData<CocktailDbEntity> = Transformations.switchMap(cocktailIdLiveData) {
        repository.getCocktailById(it)
    }

}