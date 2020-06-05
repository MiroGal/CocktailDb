package com.mirogal.cocktail.data.repository;

public class NetworkState {

    public static Status EMPTY = Status.SUCCESS_EMPTY;
    public static Status LOADED = Status.SUCCESS_LOADED;
    public static Status LOADING = Status.RUNNING;

    public enum Status {
        RUNNING,
        SUCCESS_LOADED,
        SUCCESS_EMPTY,
        FAILED;
    }
}
