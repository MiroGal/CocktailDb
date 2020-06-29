package com.mirogal.cocktail.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel


abstract class BaseActivity : AppCompatActivity() {
//abstract class BaseActivity<ViewModel : BaseViewModel> : AppCompatActivity() {

    protected abstract val viewModel: ViewModel

}