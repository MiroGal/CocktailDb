package com.mirogal.cocktail.presentation.ui.main.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.main.MainViewModel
import kotlinx.android.synthetic.main.content_settings.*
import kotlinx.android.synthetic.main.fragment_history_pager.*

class SettingsFragment : BaseFragment<SettingsViewModel>() {

    override val contentLayoutResId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.settings_label)

        chb_show_bottom_nav_label.setOnCheckedChangeListener { buttonView, isChecked ->
            if (activityViewModel.isBottomNavLabelShowLiveData.value != isChecked)
                activityViewModel.isBottomNavLabelShowLiveData.value = isChecked
        }

        setObserver()
    }

    private fun setObserver() {
        activityViewModel.isBottomNavLabelShowLiveData.observe(this, Observer {
            if (chb_show_bottom_nav_label.isChecked != it)
                chb_show_bottom_nav_label.isChecked = it
        })
    }

}