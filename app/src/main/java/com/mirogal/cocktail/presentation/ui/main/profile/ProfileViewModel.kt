package com.mirogal.cocktail.presentation.ui.main.profile

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.mirogal.cocktail.presentation.ui.base.BaseViewModel

class ProfileViewModel(
        viewStateHandle: SavedStateHandle,
        application: Application
) : BaseViewModel(viewStateHandle, application)