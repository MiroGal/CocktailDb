package com.mirogal.cocktail.data.repository;

public class NetworkState {

    public static final Status EMPTY = Status.SUCCESS_EMPTY;
    public static final Status LOADED = Status.SUCCESS_LOADED;
    public static final Status LOADING = Status.RUNNING;

    public enum Status {
        RUNNING,
        SUCCESS_LOADED,
        SUCCESS_EMPTY,
        //TODO (FAILED status processing)
    }
}
