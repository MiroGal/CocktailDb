package com.mirogal.cocktail.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.mirogal.cocktail.presentation.extension.*
import kotlin.reflect.KClass

abstract class BaseFragment<ViewModel: BaseViewModel> : Fragment() {

    @get:LayoutRes
    protected abstract val contentLayoutResId: Int
    abstract fun getViewModelClass(): KClass<ViewModel>
    protected open val viewModel: ViewModel by baseViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(contentLayoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureView(savedInstanceState)
        configureObserver()
    }

    protected open fun configureView(savedInstanceState: Bundle?) {
        //stub
    }

    protected open fun configureObserver() {
        //stub
    }

    //region Convenient Observe Methods
    @MainThread
    protected inline fun <reified T> LiveData<T>.observe(noinline observer: (T) -> Unit) {
        this.observe(viewLifecycleOwner, observer)
    }

    @MainThread
    protected inline fun <T> LiveData<T?>.observeNotNull(crossinline observer: (T) -> Unit) {
        this.observeNotNull(viewLifecycleOwner, observer)
    }

    @MainThread
    protected inline fun <T> LiveData<T?>.observeTillDestroyNotNull(crossinline observer: (T) -> Unit) {
        this.observeTillDestroyNotNull(viewLifecycleOwner, observer)
    }

    @MainThread
    protected inline fun <T> LiveData<T>.observeTillDestroy(crossinline observer: (T) -> Unit) {
        this.observeTillDestroy(viewLifecycleOwner, observer)
    }

    @MainThread
    protected inline fun <T> LiveData<T>.observeNonNullOnce(crossinline observer: (T) -> Unit) {
        this.observeNotNullOnce(viewLifecycleOwner, observer)
    }
    //endregion

}