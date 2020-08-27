package com.mirogal.cocktail.presentation.extension

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.mirogal.cocktail.di.Injector
import kotlin.reflect.KClass

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.baseViewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        Injector.ViewModelFactory(this)
    }

    return ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
}

@MainThread
inline fun <reified VM : ViewModel> Fragment.baseViewModels(
        noinline ownerProducer: () -> ViewModelStoreOwner = { this },
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> Fragment.sharedViewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(VM::class, { requireActivity().viewModelStore },
        factoryProducer ?: { Injector.ViewModelFactory(this) })

@MainThread
fun <VM : ViewModel> Fragment.createViewModelLazy(
        viewModelClass: KClass<VM>,
        storeProducer: () -> ViewModelStore,
        factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        Injector.ViewModelFactory(this)
    }
    return ViewModelLazy(viewModelClass, storeProducer, factoryPromise)
}