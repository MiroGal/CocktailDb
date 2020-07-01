package com.mirogal.cocktail.ui.detail

import android.app.Application
import com.mirogal.cocktail.data.repository.CocktailRepository
import com.mirogal.cocktail.ui.base.BaseViewModel


class DrinkDetailViewModel(application: Application) : BaseViewModel(application) {

    private val repository = CocktailRepository.newInstance(application)

}