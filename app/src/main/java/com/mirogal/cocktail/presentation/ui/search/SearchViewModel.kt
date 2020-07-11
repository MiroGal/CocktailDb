package com.mirogal.cocktail.presentation.ui.search

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.db.model.CocktailDbModel
import com.mirogal.cocktail.data.network.model.NetworkStatus
import com.mirogal.cocktail.data.repository.CocktailRepository
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel

class SearchViewModel(application: Application) : BaseViewModel(application) {

    private val repository = CocktailRepository.newInstance(application)

    private val sharedPreferences: SharedPreferences = getApplication<Application>()
            .getSharedPreferences(getApplication<Application>()
                    .resources.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()

    val cocktailListLiveData: LiveData<PagedList<CocktailDbModel?>> = repository.loadCocktailListLiveData
    val searchNameMutableLiveData: MutableLiveData<String?> = MutableLiveData()
    val networkStatusLiveData: LiveData<NetworkStatus.Status> = repository.networkStatusMutableLiveData

    companion object {
        private const val KEY_SEARCH_NAME = "search_name"
    }

    init {
        searchNameMutableLiveData.value = loadSearchNameFromSharedPreferences()
        if (searchNameMutableLiveData.value != null && searchNameMutableLiveData.value != repository.searchNameMutableLiveData.value) {
            repository.searchNameMutableLiveData.value = searchNameMutableLiveData.value
        }
    }


    fun setSearchName(searchName: String) {
        saveSearchNameToSharedPreferences(searchName)
        this.searchNameMutableLiveData.value = searchName
        repository.searchNameMutableLiveData.value = searchName
    }

    private fun saveSearchNameToSharedPreferences(value: String) {
        sharedPreferencesEditor.putString(KEY_SEARCH_NAME, value).apply()
    }

    private fun loadSearchNameFromSharedPreferences(): String? {
        return sharedPreferences.getString(KEY_SEARCH_NAME, "")
    }

    fun addCocktailToDb(cocktail: CocktailDbModel?) {
        repository.addCocktailToDb(cocktail)
    }

}