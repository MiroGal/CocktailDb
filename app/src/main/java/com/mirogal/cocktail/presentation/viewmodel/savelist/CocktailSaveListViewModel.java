package com.mirogal.cocktail.presentation.viewmodel.savelist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;
import com.mirogal.cocktail.data.repository.CocktailRepository;

public class CocktailSaveListViewModel extends AndroidViewModel {

    private LiveData<PagedList<CocktailDbEntity>> cocktailList;
    private CocktailRepository repository;

    public CocktailSaveListViewModel(@NonNull Application application) {
        super(application);
        repository = CocktailRepository.getInstance(application);
        cocktailList = repository.getSaveCocktailList();
    }

    public LiveData<PagedList<CocktailDbEntity>> getCocktailList() {
        return cocktailList;
    }

    public void deleteCocktail(int id) {
        repository.deleteCocktail(id);
    }
}
