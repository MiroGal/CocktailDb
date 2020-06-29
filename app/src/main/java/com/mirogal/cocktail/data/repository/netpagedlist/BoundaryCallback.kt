package com.mirogal.cocktail.data.repository.netpagedlist

import androidx.paging.PagedList
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity
import com.mirogal.cocktail.data.repository.CocktailRepository
import com.mirogal.cocktail.data.repository.NetworkState

class BoundaryCallback(private val repository: CocktailRepository) : PagedList.BoundaryCallback<CocktailDbEntity?>() {

    override fun onItemAtFrontLoaded(itemAtFront: CocktailDbEntity) {
        super.onItemAtFrontLoaded(itemAtFront)
        repository.networkStatusMutableLiveData.postValue(NetworkState.LOADING)
    }

    override fun onItemAtEndLoaded(itemAtEnd: CocktailDbEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        repository.networkStatusMutableLiveData.postValue(NetworkState.LOADED)
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        repository.networkStatusMutableLiveData.postValue(NetworkState.EMPTY)
    }

}