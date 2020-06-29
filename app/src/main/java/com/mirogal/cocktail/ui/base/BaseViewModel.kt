package com.mirogal.cocktail.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mirogal.cocktail.data.repository.CocktailRepository


open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val repository = CocktailRepository.newInstance(application)

}