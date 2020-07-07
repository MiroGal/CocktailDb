package com.mirogal.cocktail.data.repository.netpagedlist

import androidx.lifecycle.MutableLiveData
import com.mirogal.cocktail.data.db.entity.CocktailDbEntity

class DataSourceFactory : androidx.paging.DataSource.Factory<Int?, CocktailDbEntity?>() {

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