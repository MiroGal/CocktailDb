package com.mirogal.cocktail.data.network;

import com.mirogal.cocktail.data.network.entity.ContainerNetEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {

    @GET("api/json/v1/1/search.php")
    Call<ContainerNetEntity> getCocktailContainer(@Query("s") String name);
}
