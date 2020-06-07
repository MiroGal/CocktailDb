package com.mirogal.cocktail.presentation.viewmodel.searchlist;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.mirogal.cocktail.R;
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;
import com.mirogal.cocktail.data.repository.CocktailRepository;
import com.mirogal.cocktail.data.repository.NetworkState;

import static android.content.Context.MODE_PRIVATE;

public class CocktailSearchListViewModel extends AndroidViewModel {

    private static final String SAVE_REQUEST_QUERY = "save_request_query";

    // TODO Dagger integration

    private final LiveData<PagedList<CocktailDbEntity>> cocktailList;
    private final CocktailRepository repository;

    private final MutableLiveData<String> requestQuery;

    private final SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    public CocktailSearchListViewModel(@NonNull Application application) {
        super(application);
        repository = CocktailRepository.getInstance(application);
        cocktailList = repository.getSelectCocktailList();

        sharedPreferences = getApplication().getSharedPreferences(
                getApplication().getResources().getString(R.string.app_name), MODE_PRIVATE);
        requestQuery = new MutableLiveData<>();
        requestQuery.setValue(loadStringPreference());
        if (requestQuery.getValue() != null
                && !requestQuery.getValue().equals(repository.getRequestQuery().getValue())) {
            repository.getRequestQuery().setValue(requestQuery.getValue());
        }
    }

    public LiveData<PagedList<CocktailDbEntity>> getCocktailList() {
        return cocktailList;
    }

    public LiveData<NetworkState.Status> getNetworkStatus() {
        return repository.getNetworkStatus();
    }

    public LiveData<String> getRequestQuery() {
        return requestQuery;
    }

    public void setRequestQuery(String requestQuery) {
        saveStringPreference(requestQuery);
        this.requestQuery.setValue(requestQuery);
        repository.getRequestQuery().setValue(requestQuery);
    }

    private void saveStringPreference(String value) {
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(SAVE_REQUEST_QUERY, value).apply();
    }

    private String loadStringPreference() {
       return sharedPreferences.getString(SAVE_REQUEST_QUERY, "");
    }

    public void saveCocktail(CocktailDbEntity cocktail) {
        repository.saveCocktail(cocktail);
    }
}
