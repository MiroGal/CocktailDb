package com.mirogal.cocktail.presentation.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.mirogal.cocktail.presentation.extension.*
import kotlin.reflect.KClass

abstract class BaseActivity<ViewModel: BaseViewModel> : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val contentLayoutResId: Int

    abstract fun getViewModelClass(): KClass<ViewModel>

    protected open val viewModel: ViewModel by baseViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentLayoutResId)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        configureView(savedInstanceState)
        configureObserver()

        viewModel.errorLiveData.observeNotNull {
            //TODO handle error
            Toast.makeText(this, "error = ${it.message}", Toast.LENGTH_SHORT).show()
        }
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
        this.observe(this@BaseActivity, observer)
    }

    @MainThread
    protected fun <T> LiveData<T?>.observeNotNull(observer: (T) -> Unit) {
        this.observeNotNull(this@BaseActivity, observer)
    }

    @MainThread
    protected inline fun <T> LiveData<T?>.observeTillDestroyNotNull(crossinline observer: (T) -> Unit) {
        this.observeTillDestroyNotNull(this@BaseActivity, observer)
    }

    @MainThread
    protected inline fun <T> LiveData<T>.observeTillDestroy(crossinline observer: (T) -> Unit) {
        this.observeTillDestroy(this@BaseActivity, observer)
    }

    @MainThread
    protected inline fun <T> LiveData<T>.observeNonNullOnce(crossinline observer: (T) -> Unit) {
        this.observeNotNullOnce(this@BaseActivity, observer)
    }
    //endregion

}