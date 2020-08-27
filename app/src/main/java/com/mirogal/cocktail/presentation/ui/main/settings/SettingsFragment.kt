package com.mirogal.cocktail.presentation.ui.main.settings

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.mirogal.cocktail.R
import com.mirogal.cocktail.databinding.FragmentSettingsBinding
import com.mirogal.cocktail.presentation.extension.baseViewModels
import com.mirogal.cocktail.presentation.extension.sharedViewModels
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_drink_pager.*
import kotlinx.android.synthetic.main.fragment_settings_content.*

class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>() {

    override val contentLayoutResId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by baseViewModels()
    private val mainViewModel: MainViewModel by sharedViewModels()

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun configureDataBinding(binding: FragmentSettingsBinding) {
        super.configureDataBinding(binding)
        dataBinding.viewmodel = viewModel
    }

    override fun configureView(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.settings_label)

        chb_show_bottom_nav_label.setOnCheckedChangeListener(onCheckedChangeListener)
        chb_show_battery_indicator.setOnCheckedChangeListener(onCheckedChangeListener)
    }

    private val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        when (buttonView) {
            chb_show_bottom_nav_label -> {
                if (mainViewModel.isBottomNavLabelVisibleLiveData.value != isChecked)
                    mainViewModel.isBottomNavLabelVisibleLiveData.value = isChecked
            }
            chb_show_battery_indicator -> {
                if (mainViewModel.isBatteryIndicatorVisibleLiveData.value != isChecked)
                    mainViewModel.isBatteryIndicatorVisibleLiveData.value = isChecked
            }
        }

    }

    override fun configureObserver() {
        mainViewModel.isBottomNavLabelVisibleLiveData.observe(this, Observer {
            if (chb_show_bottom_nav_label.isChecked != it)
                chb_show_bottom_nav_label.isChecked = it
        })
        mainViewModel.isBatteryIndicatorVisibleLiveData.observe(this, Observer {
            if (chb_show_battery_indicator.isChecked != it)
                chb_show_battery_indicator.isChecked = it
        })
    }

}