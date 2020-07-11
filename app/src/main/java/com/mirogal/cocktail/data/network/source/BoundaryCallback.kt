package com.mirogal.cocktail.data.network.source

import androidx.paging.PagedList
import com.mirogal.cocktail.data.db.model.CocktailDbModel
import com.mirogal.cocktail.data.network.model.NetworkStatus
import com.mirogal.cocktail.data.repository.CocktailRepository

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