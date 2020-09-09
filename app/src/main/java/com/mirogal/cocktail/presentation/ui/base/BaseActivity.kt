package com.mirogal.cocktail.presentation.ui.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import com.mirogal.cocktail.presentation.extension.*
import java.util.*

abstract class BaseActivity<ViewModel: BaseViewModel, DataBinding: ViewDataBinding> : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val contentLayoutResId: Int
    protected abstract val viewModel: ViewModel
    protected open lateinit var dataBinding: DataBinding

    companion object {
        var dLocale: Locale? = null
    }

    init {
        updateConfig(wrapper = this)
    }

    private fun updateConfig(wrapper: ContextThemeWrapper) {
        if (dLocale == Locale("")) {
            return
        }
        Locale.setDefault(dLocale)
        val configuration = Configuration()
        configuration.setLocale(dLocale)
        wrapper.applyOverrideConfiguration(configuration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        configureTheme()
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, contentLayoutResId)
        dataBinding.lifecycleOwner = this@BaseActivity

        configureDataBinding(dataBinding)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        configureView(savedInstanceState)
        configureObserver()
        configureError()
    }

    protected open fun configureTheme() {
        //stub
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

    protected open fun configureError() {
        viewModel.errorLiveData.observeNotNull {
            //TODO handle error
            Toast.makeText(this, "error = ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    //region Convenient Observe Methods
    @MainThread
    protected inline fun <reified T> LiveData<T>.observe(noinline observer: (T) -> Unit) {
        this.observe(this@BaseActivity, observer)
    }

    @MainThread
    protected fun <T> LiveData<T?>.observeNotNull(observer: (T) -> Unit) {
        this.observeNotNull(this@BaseActivity, observer)
    }

    @MainThread
    protected inline fun <T> observeTillDestroyNotNull(crossinline observer: (T) -> Unit) {
        observeTillDestroyNotNull(this@BaseActivity, observer)
    }

    @MainThread
    protected inline fun <T> observeTillDestroy(crossinline observer: (T) -> Unit) {
        observeTillDestroy(this@BaseActivity, observer)
    }

    @MainThread
    protected inline fun <T> LiveData<T>.observeNonNullOnce(crossinline observer: (T) -> Unit) {
        this.observeNotNullOnce(this@BaseActivity, observer)
    }
    //endregion

}