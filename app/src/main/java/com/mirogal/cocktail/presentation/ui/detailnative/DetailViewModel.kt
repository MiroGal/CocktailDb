package com.mirogal.cocktail.presentation.ui.detailnative

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.mirogal.cocktail.datanative.db.model.CocktailDbModel
import com.mirogal.cocktail.datanative.repository.CocktailRepository
import com.mirogal.cocktail.presentation.ui.basenative.BaseViewModel

class DetailViewModel(application: Application) : BaseViewModel(application) {

    private val repository = CocktailRepository.newInstance(application)

    val cocktailIdLiveData: MutableLiveData<Int> = MutableLiveData()

    val cocktailLiveData: LiveData<CocktailDbModel> = cocktailIdLiveData.switchMap {
        repository.getCocktailById(it)
    }

}