package com.mirogal.cocktail.ui.main.settings

import androidx.fragment.app.viewModels
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.base.BaseFragment
import com.mirogal.cocktail.ui.main.MainViewModel

class SettingsFragment : BaseFragment() {

    override val contentLayoutResId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModels()
    val mainViewModel: MainViewModel by viewModels()

}