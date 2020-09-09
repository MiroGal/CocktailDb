package com.mirogal.cocktail.presentation.ui.main.settings

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.mirogal.cocktail.R
import com.mirogal.cocktail.databinding.FragmentSettingsBinding
import com.mirogal.cocktail.presentation.extension.baseViewModels
import com.mirogal.cocktail.presentation.extension.sharedViewModels
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.main.MainViewModel
import com.mirogal.cocktail.presentation.ui.main.settings.dialog.LanguageDialogFragment
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

        btn_app_language.setOnClickListener { showLanguageDialog() }
        chb_show_bottom_nav_label.setOnCheckedChangeListener(onCheckedChangeListener)
        chb_show_battery_indicator.setOnCheckedChangeListener(onCheckedChangeListener)
    }

    private val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        when (buttonView) {
            chb_show_bottom_nav_label -> mainViewModel.isBottomNavLabelShowLiveData.value = isChecked
            chb_show_battery_indicator -> mainViewModel.isBatteryIndicatorShowLiveData.value = isChecked
        }
    }

    override fun configureObserver() {
        mainViewModel.isBottomNavLabelShowLiveData.observe(this, {
            if (chb_show_bottom_nav_label.isChecked != it)
                chb_show_bottom_nav_label.isChecked = it
        })
        mainViewModel.isBatteryIndicatorShowLiveData.observe(this, {
            if (chb_show_battery_indicator.isChecked != it)
                chb_show_battery_indicator.isChecked = it
        })
    }

    private fun showLanguageDialog() {
        val dialogFragment = LanguageDialogFragment.newInstance()
        dialogFragment.show(childFragmentManager, LanguageDialogFragment::class.java.name)
    }

}