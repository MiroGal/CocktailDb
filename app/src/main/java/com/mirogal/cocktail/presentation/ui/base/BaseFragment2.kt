package com.mirogal.cocktail.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment2<ViewModel: BaseViewModel, DataBinding: ViewDataBinding> : Fragment() {

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

        configureView(view, savedInstanceState)
        configureObserver(view, savedInstanceState)
    }

    protected open fun configureDataBinding(binding: DataBinding) {
        // stub
    }

    protected open fun configureView(view: View, savedInstanceState: Bundle?) {
        // stub
    }

    protected open fun configureObserver(view: View, savedInstanceState: Bundle?) {
        // stub
    }

}