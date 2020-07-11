package com.mirogal.cocktail.presentation.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<ViewModel : BaseViewModel> : AppCompatActivity() {

    protected abstract val contentLayoutResId: Int

    protected abstract val viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setApplicationTheme()
        super.onCreate(savedInstanceState)
        setContentView(contentLayoutResId)
        configureView(savedInstanceState)
    }

    protected open fun setApplicationTheme() {
        // stub
    }

    protected open fun configureView(savedInstanceState: Bundle?) {
        // stub
    }

}