package com.mirogal.cocktail.presentation.extension

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.mirogal.cocktail.di.Injector
import com.mirogal.cocktail.presentation.ui.base.BaseActivity
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel

//region Activity
@MainThread
inline fun <reified ViewModel : BaseViewModel> BaseActivity<ViewModel>.viewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<ViewModel> {
    val factoryPromise = factoryProducer ?: {
        Injector.ViewModelFactory(this)
    }

    return ViewModelLazy(ViewModel::class, { viewModelStore }, factoryPromise)
}

@MainThread
fun <ViewModel: BaseViewModel> BaseActivity<ViewModel>.baseViewModels(
        factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<ViewModel> {
    val factoryPromise = factoryProducer ?: {
        Injector.ViewModelFactory(this)
    }
    return ViewModelLazy(getViewModelClass(), { viewModelStore }, factoryPromise)
}
//endregion

//region Fragment
@MainThread
inline fun <reified ViewModel: BaseViewModel> BaseFragment<ViewModel>.viewModels(
        owner: SavedStateRegistryOwner = this,
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<ViewModel> {
    val factoryPromise = factoryProducer ?: {
        Injector.ViewModelFactory(owner)
    }

    return ViewModelLazy(ViewModel::class, { viewModelStore }, factoryPromise)
}

@MainThread
fun <ViewModel: BaseViewModel> BaseFragment<ViewModel>.baseViewModels(
        owner: SavedStateRegistryOwner = this,
        factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<ViewModel> {
    val factoryPromise = factoryProducer ?: {
        Injector.ViewModelFactory(owner)
    }

    return ViewModelLazy(getViewModelClass(), { viewModelStore }, factoryPromise)
}
//endregion