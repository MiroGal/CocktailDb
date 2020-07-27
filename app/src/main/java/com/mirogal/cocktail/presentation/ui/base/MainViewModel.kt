package com.mirogal.cocktail.presentation.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import com.mirogal.cocktail.data.repository.source.AuthRepository
import com.mirogal.cocktail.data.repository.source.CocktailRepository
import com.mirogal.cocktail.data.repository.source.UserRepository
import com.mirogal.cocktail.extension.log
import com.mirogal.cocktail.presentation.extension.mapNotNull
import com.mirogal.cocktail.presentation.mapper.CocktailModelMapper
import com.mirogal.cocktail.presentation.mapper.UserModelMapper
import com.mirogal.cocktail.presentation.model.cocktail.*

class MainViewModel(
        private val cocktailRepository: CocktailRepository,
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository,
        private val userModelMapper: UserModelMapper,
        private val cocktailModelMapper: CocktailModelMapper,
        viewStateHandle: SavedStateHandle
) : BaseViewModel(viewStateHandle) {

    private val cocktailListLiveData =
            cocktailRepository.cocktailListLiveData.map(cocktailModelMapper::mapTo)

    val cocktailCountLiveData = cocktailListLiveData.map { it.size }
    val textLiveData: LiveData<String> = MutableLiveData()

    val userLiveData = userRepository.userLiveData.map {
        when {
            it != null -> userModelMapper.mapTo(it)
            else -> null
        }
    }

    val userFullNameLiveData = userLiveData.mapNotNull() { "$name $lastName" }

    init {
        textLiveData.setValue("HELLO FROM VIEW MODEL")
        launchRequest {
            authRepository.signIn(
                    email = "andrew.malitchuk@gmail.com",
                    password = "123456"
            )
        }
    }

    fun addCocktail() {
        launchRequest {
            val id = (0L..10000L).random()
            cocktailRepository.addOrReplaceCocktail(
                    CocktailModel(
                            id = id,
                            names = LocalizedStringModel(
                                    "id",
                                    "id".toCharArray().joinToString(separator = "_")
                            ),
                            category = CocktailCategory.values().random(),
                            alcoholType = CocktailAlcoholType.values().random(),
                            glass = CocktailGlass.values().random(),
                            image = "",
                            instructions = LocalizedStringModel(default = "Просто додай води"),
                            ingredientsWithMeasures = CocktailIngredient.values().toList().shuffled()
                                    .take((1..10).random())
                                    .mapIndexed { index, ingredient -> ingredient to index.toString() }
                                    .toMap()
                    ).run(cocktailModelMapper::mapFrom)
            )

            "LOG COCKTAIL ADDED".log
        }
    }

    fun getCocktailById(id: Long, onComplete: (cocktail: CocktailModel?) -> Unit = {}) {
        launchRequest {
            cocktailRepository.getCocktailById(id)
                    ?.run(cocktailModelMapper::mapTo)
                    ?.run(onComplete)
        }
    }

    fun removeCocktail() {
        launchRequest {
            cocktailRepository.deleteCocktails(
                    cocktailRepository.getFirstCocktail() ?: return@launchRequest
            )
            "LOG COCKTAIL DELETED".log
        }
    }

}