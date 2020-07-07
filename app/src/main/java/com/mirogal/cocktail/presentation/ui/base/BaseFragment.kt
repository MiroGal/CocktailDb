package com.mirogal.cocktail.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


abstract class BaseFragment<ViewModel : BaseViewModel> : Fragment() {

    protected abstract val contentLayoutResId: Int

    protected abstract val viewModel: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(contentLayoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureView(view, savedInstanceState)
    }

    protected open fun configureView(view: View, savedInstanceState: Bundle?) {
        // stub
    }

}