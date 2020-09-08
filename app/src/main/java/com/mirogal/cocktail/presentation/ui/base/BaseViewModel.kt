package com.mirogal.cocktail.presentation.ui.base

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class BaseViewModel(
        protected val viewStateHandle: SavedStateHandle,
        application: Application
): AndroidViewModel(application) {

    val errorLiveData: LiveData<java.lang.Exception?> = MutableLiveData()

    protected fun <T> launchRequest(
            liveData: LiveData<T>? = null,
            context: CoroutineContext = Dispatchers.IO,
            request: suspend CoroutineScope.() -> T
    ): Job {
        return viewModelScope.launch {
            try {
                val result: T = withContext(context) { request() }
                liveData?.setValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    errorLiveData.setValue(e)
                    errorLiveData.setValue(null)
                }
            }
        }
    }

    protected fun <T> LiveData<T>.setValue(value: T) {
        (this as? MutableLiveData)?.value = value
    }

    protected fun <T> LiveData<T>.postValue(value: T) {
        (this as? MutableLiveData)?.postValue(value)
    }

    // SavedStateHandle Delegate
    protected fun <T> stateHandle(
            initialValue: T? = null,
            key: String? = null
    ) = ReadOnlyProperty<Any, MutableLiveData<T>> { thisRef, property ->
        val stateKey = key ?: property.name
        if (initialValue == null) {
            viewStateHandle.getLiveData(stateKey)
        } else {
            viewStateHandle.getLiveData(stateKey, initialValue)
        }
    }

}