package com.mirogal.cocktail.presentation.ui.basenative.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mirogal.cocktail.R

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    protected abstract val contentLayoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

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