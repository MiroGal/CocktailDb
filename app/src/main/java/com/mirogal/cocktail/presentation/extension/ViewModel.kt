package com.mirogal.cocktail.presentation.extension

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import com.mirogal.cocktail.di.Injector
import com.mirogal.cocktail.presentation.ui.base.BaseActivity
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel

//region Activity
@MainThread
inline fun <reified VM : BaseViewModel> BaseActivity<VM>.sharedViewModels(
        owner: ViewModelStoreOwner = this,
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        Injector.ViewModelFactory(this)
    }

    return ViewModelLazy(VM::class, { owner.viewModelStore }, factoryPromise)
}

@MainThread
fun <VM: BaseViewModel> BaseActivity<VM>.baseViewModels(
        owner: ViewModelStoreOwner = this,
        factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        Injector.ViewModelFactory(this)
    }
    return ViewModelLazy(getViewModelClass(), { owner.viewModelStore }, factoryPromise)
}
//endregion

//region Fragment
@MainThread
inline fun <reified VM: BaseViewModel> BaseFragment<VM>.sharedViewModels(
        owner: SavedStateRegistryOwner = this,
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        Injector.ViewModelFactory(owner)
    }

    return ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
}

@MainThread
fun <VM: BaseViewModel> BaseFragment<VM>.baseViewModels(
        owner: SavedStateRegistryOwner = this,
        factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        Injector.ViewModelFactory(owner)
    }

    return ViewModelLazy(getViewModelClass(), { viewModelStore }, factoryPromise)
}
//endregion