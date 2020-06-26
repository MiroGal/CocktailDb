package com.mirogal.cocktail.ui.search

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.mirogal.cocktail.R
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.CocktailRepository
import com.mirogal.cocktail.data.repository.NetworkState

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CocktailRepository.getInstance(application)
    private val sharedPreferences: SharedPreferences = getApplication<Application>().getSharedPreferences(
            getApplication<Application>().resources.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()

    val cocktailList: LiveData<PagedList<CocktailDbEntity?>>
    private val requestQuery: MutableLiveData<String?>

    val networkStatus: LiveData<NetworkState.Status>
        get() = repository?.networkStatus!!


    init {
        cocktailList = repository?.selectCocktailList!!
        requestQuery = MutableLiveData()
        requestQuery.value = loadStringPreference()
        if (requestQuery.value != null && requestQuery.value != repository.requestQuery.value) {
            repository.requestQuery.value = requestQuery.value
        }
    }


    fun getRequestQuery(): LiveData<String?> {
        return requestQuery
    }

    fun setRequestQuery(requestQuery: String) {
        saveStringPreference(requestQuery)
        this.requestQuery.value = requestQuery
        repository?.requestQuery?.value = requestQuery
    }

    private fun saveStringPreference(value: String) {
        sharedPreferencesEditor.putString(SAVE_REQUEST_QUERY, value).apply()
    }

    private fun loadStringPreference(): String? {
        return sharedPreferences.getString(SAVE_REQUEST_QUERY, "")
    }

    fun saveCocktail(cocktail: CocktailDbEntity?) {
        repository?.saveCocktail(cocktail)
    }


    companion object {
        private const val SAVE_REQUEST_QUERY = "save_request_query"
    }

}