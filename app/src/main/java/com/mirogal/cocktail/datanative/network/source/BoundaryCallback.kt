package com.mirogal.cocktail.datanative.network.source

import androidx.paging.PagedList
import com.mirogal.cocktail.datanative.db.model.CocktailDbModel
import com.mirogal.cocktail.datanative.network.model.NetworkStatus
import com.mirogal.cocktail.datanative.repository.CocktailRepository

class BoundaryCallback(private val repository: CocktailRepository) : PagedList.BoundaryCallback<CocktailDbModel?>() {

    override fun onItemAtFrontLoaded(itemAtFront: CocktailDbModel) {
        super.onItemAtFrontLoaded(itemAtFront)
        repository.networkStatusMutableLiveData.postValue(NetworkStatus.LOADING)
    }

    override fun onItemAtEndLoaded(itemAtEnd: CocktailDbModel) {
        super.onItemAtEndLoaded(itemAtEnd)
        repository.networkStatusMutableLiveData.postValue(NetworkStatus.LOADED)
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        repository.networkStatusMutableLiveData.postValue(NetworkStatus.EMPTY)
    }

}