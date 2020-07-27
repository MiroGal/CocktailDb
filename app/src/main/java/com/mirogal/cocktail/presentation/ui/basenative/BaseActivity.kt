package com.mirogal.cocktail.presentation.ui.basenative

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
        configureObserver(savedInstanceState)
    }

    protected open fun setApplicationTheme() {
        // stub
    }

    protected open fun configureView(savedInstanceState: Bundle?) {
        // stub
    }

    protected open fun configureObserver(savedInstanceState: Bundle?) {
        // stub
    }

}