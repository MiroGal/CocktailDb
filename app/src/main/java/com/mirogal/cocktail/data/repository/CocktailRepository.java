package com.mirogal.cocktail.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.mirogal.cocktail.data.database.CocktailDao;
import com.mirogal.cocktail.data.database.CocktailDatabase;
import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;
import com.mirogal.cocktail.data.repository.netpagedlist.BoundaryCallback;
import com.mirogal.cocktail.data.repository.netpagedlist.DataSourceFactory;

public class CocktailRepository {

    private LiveData<PagedList<CocktailDbEntity>> saveCocktailList;
    private LiveData<PagedList<CocktailDbEntity>> selectCocktailList;
    private MutableLiveData<NetworkState.Status> networkStatus;
    private MutableLiveData<String> requestQuery;

    private CocktailDao cocktailDao;
    private DataSourceFactory dataSourceFactory;
    private BoundaryCallback boundaryCallback;

    private static CocktailRepository INSTANCE_REPOSITORY;

    public static CocktailRepository getInstance(final Context context) {
        if (INSTANCE_REPOSITORY == null) {
            synchronized (CocktailDatabase.class) {
                if (INSTANCE_REPOSITORY == null) {
                    INSTANCE_REPOSITORY = new CocktailRepository(context);
                }
            }
        }
        return INSTANCE_REPOSITORY;
    }

    private CocktailRepository(Context context) {
        selectCocktailList = new MutableLiveData<>();
        CocktailDatabase db = CocktailDatabase.getInstance(context);
        cocktailDao = db.getDao();
        networkStatus = new MutableLiveData<>();
        requestQuery = new MutableLiveData<>();
        initSaveCocktailList();
        initSelectCocktailList();
    }

    private void initSaveCocktailList() {
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setPageSize(10)
                .setPrefetchDistance(30)
                .setInitialLoadSizeHint(30)
                .setEnablePlaceholders(false)
                .build();

        saveCocktailList = new LivePagedListBuilder<>(cocktailDao.getCocktailList(), pagedListConfig)
                .build();
    }

    private void initSelectCocktailList() {
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setPageSize(10)
                .setPrefetchDistance(30)
                .setInitialLoadSizeHint(30)
                .setEnablePlaceholders(false)
                .build();

        dataSourceFactory = new DataSourceFactory();
        boundaryCallback = new BoundaryCallback(this);

        selectCocktailList = Transformations.switchMap(requestQuery, input -> {

            if (requestQuery.getValue() == null || requestQuery.getValue().isEmpty()) {
                dataSourceFactory.setCurrentQuery(null);
            } else {
                dataSourceFactory.setCurrentQuery(requestQuery.getValue());
            }
            dataSourceFactory.create();

            return new LivePagedListBuilder<>(dataSourceFactory, pagedListConfig)
                    .setBoundaryCallback(boundaryCallback)
                    .build();
        });
    }

    public LiveData<PagedList<CocktailDbEntity>> getSaveCocktailList() {
        return saveCocktailList;
    }

    public LiveData<PagedList<CocktailDbEntity>> getSelectCocktailList() {
        return selectCocktailList;
    }

    public MutableLiveData<NetworkState.Status> getNetworkStatus() {
        return networkStatus;
    }

    public MutableLiveData<String> getRequestQuery() {
        return requestQuery;
    }

    public void saveCocktail(CocktailDbEntity cocktail) {
        new Thread(new Runnable() {
            public void run() {
                cocktailDao.insertCocktail(cocktail);
            }
        }).start();
    }

    public void deleteCocktail(int cocktailId) {
        new Thread(new Runnable() {
            public void run() {
                cocktailDao.deleteCocktail(cocktailId);
            }
        }).start();
    }
}
