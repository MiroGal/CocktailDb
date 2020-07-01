package com.mirogal.cocktail.ui.main.settings

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseFragment
import com.mirogal.cocktail.ui.main.MainViewModel

class SettingsFragment : BaseFragment<SettingsViewModel>() {

    override val contentLayoutResId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

}