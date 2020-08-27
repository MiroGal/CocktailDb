package com.mirogal.cocktail.presentation.ui.main.settings

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel

class SettingsViewModel(
        viewStateHandle: SavedStateHandle,
        application: Application
) : BaseViewModel(viewStateHandle, application)