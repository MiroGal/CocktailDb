package com.mirogal.cocktail.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel


abstract class BaseFragment : Fragment() {
//abstract class BaseFragment<BaseViewModel> : Fragment() {

    protected abstract val contentLayoutResId: Int
    protected abstract val viewModel: ViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(contentLayoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureView(savedInstanceState)
    }

    protected open fun configureView(savedInstanceState: Bundle?) {
        // stub
    }

}