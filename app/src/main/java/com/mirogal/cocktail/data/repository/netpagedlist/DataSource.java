package com.mirogal.cocktail.data.repository.netpagedlist;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.mirogal.cocktail.data.database.entity.CocktailDbEntity;
import com.mirogal.cocktail.data.network.ApiFactory;
import com.mirogal.cocktail.data.network.entity.CocktailNetEntity;
import com.mirogal.cocktail.data.network.entity.ContainerNetEntity;
import com.mirogal.cocktail.data.repository.NetDbMapper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSource extends PositionalDataSource<CocktailDbEntity> {

    private String currentQuery;

    public DataSource(String currentQuery) {
        this.currentQuery = currentQuery;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<CocktailDbEntity> callback) {

        Call<ContainerNetEntity> data = ApiFactory.getWebService().getCocktailContainer(currentQuery);
        data.enqueue(new Callback<ContainerNetEntity>() {
            @Override
            public void onResponse(@NotNull Call<ContainerNetEntity> call,
                                   @NotNull Response<ContainerNetEntity> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<CocktailNetEntity> netEntityList = response.body().getCocktailList();
                    List<CocktailDbEntity> dbEntityList = new ArrayList<>();
                    if (netEntityList != null) {
                        for (int i = 0; i < netEntityList.size(); i++) {
                            dbEntityList.add(NetDbMapper.toDbEntity(netEntityList.get(i)));
                        }
                    }
                    callback.onResult(dbEntityList, params.requestedStartPosition);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ContainerNetEntity> call, @NotNull Throwable t) {
            }
        });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<CocktailDbEntity> callback) {

    }
}
