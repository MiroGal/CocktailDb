package com.mirogal.cocktail.presentation.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

inline fun Lifecycle.addOnCreateObserver(crossinline onCreate: LifecycleObserver.() -> Unit) {
    addObserver(
            object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
                fun onCreate() = onCreate.invoke(this)
            }
    )
}

inline fun Lifecycle.doOnCreate(crossinline onCreate: () -> Unit) {
    if (currentState.isAtLeast(Lifecycle.State.CREATED)) {
        onCreate()
    } else {
        addObserver(
                object : LifecycleObserver {
                    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
                    fun onCreate() {
                        onCreate.invoke()
                        removeObserver(this)
                    }
                }
        )
    }
}