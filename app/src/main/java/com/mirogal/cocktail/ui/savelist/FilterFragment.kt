package com.mirogal.cocktail.ui.savelist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_save_list.*

class FilterFragment() : BaseFragment() {

    override val contentLayoutResId = R.layout.fragment_filter

    companion object {
        fun newInstance() = FilterFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.filter_label)
    }

}