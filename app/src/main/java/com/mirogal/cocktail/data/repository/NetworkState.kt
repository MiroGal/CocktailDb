package com.mirogal.cocktail.data.repository

object NetworkState {

    val EMPTY = Status.SUCCESS_EMPTY
    val LOADED = Status.SUCCESS_LOADED
    val LOADING = Status.RUNNING

    enum class Status {
        RUNNING, SUCCESS_LOADED, SUCCESS_EMPTY
        //TODO (FAILED status processing)
    }

}