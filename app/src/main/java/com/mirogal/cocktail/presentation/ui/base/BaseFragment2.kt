package com.mirogal.cocktail.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.mirogal.cocktail.presentation.extension.*

abstract class BaseFragment2<ViewModel: BaseViewModel, DataBinding: ViewDataBinding> : Fragment() {

    @get:LayoutRes
    protected abstract val contentLayoutResId: Int
    protected abstract val viewModel: ViewModel
    protected open lateinit var dataBinding: DataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, contentLayoutResId, container, false)
        dataBinding.lifecycleOwner = this.viewLifecycleOwner

        configureDataBinding(dataBinding)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureView(savedInstanceState)
        configureObserver()
    }

    protected open fun configureDataBinding(binding: DataBinding) {
        //stub
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