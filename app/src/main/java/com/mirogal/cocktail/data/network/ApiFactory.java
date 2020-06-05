package com.mirogal.cocktail.data.network;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final String BASE_URL = "https://www.thecocktaildb.com/";
    private static volatile WebService INSTANCE_SERVICE;

    @NonNull
    public static WebService getWebService() {
        if (INSTANCE_SERVICE == null) {
            synchronized (ApiFactory.class) {
                if (INSTANCE_SERVICE == null) {
                    INSTANCE_SERVICE = buildRetrofit().create(WebService.class);
                }
            }
        }
        return INSTANCE_SERVICE;
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
