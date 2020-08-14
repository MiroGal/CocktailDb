package com.mirogal.cocktail.presentation.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity2<ViewModel: BaseViewModel, DataBinding: ViewDataBinding> : AppCompatActivity() {

    protected abstract val contentLayoutResId: Int
    protected abstract val viewModel: ViewModel
    protected open lateinit var dataBinding: DataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setApplicationTheme()
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, contentLayoutResId)!!
        dataBinding.lifecycleOwner = this@BaseActivity2
        configureView(savedInstanceState)
        configureDataBinding(dataBinding)
        configureObserver(savedInstanceState)
    }

    protected open fun setApplicationTheme() {
        // stub
    }

    protected open fun configureView(savedInstanceState: Bundle?) {
        // stub
    }

    protected open fun configureDataBinding(binding: DataBinding) {
        // stub
    }

    protected open fun configureObserver(savedInstanceState: Bundle?) {
        // stub
    }

}