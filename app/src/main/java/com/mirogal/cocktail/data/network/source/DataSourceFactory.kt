package com.mirogal.cocktail.data.network.source

import androidx.lifecycle.MutableLiveData
import com.mirogal.cocktail.data.db.model.CocktailDbModel

class DataSourceFactory : androidx.paging.DataSource.Factory<Int?, CocktailDbModel?>() {

    private val mutableLiveData = MutableLiveData<DataSource>()
    private var currentQuery: String? = null

    fun setCurrentQuery(currentQuery: String?) {
        this.currentQuery = currentQuery
    }

    override fun create(): DataSource {
        val cocktailDataSource = DataSource(currentQuery)
        mutableLiveData.postValue(cocktailDataSource)
        return cocktailDataSource
    }

}