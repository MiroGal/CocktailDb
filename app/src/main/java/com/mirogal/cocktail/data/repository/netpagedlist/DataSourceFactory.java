package com.mirogal.cocktail.data.repository.netpagedlist;

import androidx.lifecycle.MutableLiveData;

import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;

public class DataSourceFactory extends androidx.paging.DataSource.Factory<Integer, CocktailDbEntity> {

    // TODO Dagger integration

    private final MutableLiveData<DataSource> mutableLiveData = new MutableLiveData<>();

    private DataSource cocktailDataSource;
    private String currentQuery;

    public void setCurrentQuery(String currentQuery) {
        this.currentQuery = currentQuery;
    }

    @Override
    public DataSource create() {
        cocktailDataSource = new DataSource(currentQuery);
        mutableLiveData.postValue(cocktailDataSource);
        return cocktailDataSource;
    }
}
