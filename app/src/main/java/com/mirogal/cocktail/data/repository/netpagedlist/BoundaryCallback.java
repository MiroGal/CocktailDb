package com.mirogal.cocktail.data.repository.netpagedlist;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;
import com.mirogal.cocktail.data.repository.CocktailRepository;
import com.mirogal.cocktail.data.repository.NetworkState;

public class BoundaryCallback extends PagedList.BoundaryCallback<CocktailDbEntity> {

    private final CocktailRepository repository;

    public BoundaryCallback(CocktailRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull CocktailDbEntity itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
        repository.getNetworkStatus().postValue(NetworkState.LOADING);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull CocktailDbEntity itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        repository.getNetworkStatus().postValue(NetworkState.LOADED);
    }

    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        repository.getNetworkStatus().postValue(NetworkState.EMPTY);
    }
}
