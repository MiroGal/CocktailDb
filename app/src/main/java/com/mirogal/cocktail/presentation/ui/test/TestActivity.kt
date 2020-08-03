package com.mirogal.cocktail.presentation.ui.test

import android.os.Bundle
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.extension.viewModels
import com.mirogal.cocktail.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity<TestViewModel>() {

    override val contentLayoutResId = R.layout.activity_test

    /**
     * if you write code like this "override val viewModelClass = MainViewModel::class" it will crash
     */
    override fun getViewModelClass() = TestViewModel::class

    override val viewModel: TestViewModel by viewModels()

    override fun configureView(savedInstanceState: Bundle?) {
        super.configureView(savedInstanceState)

        val id = intent.getLongExtra("KEY", 1)

        txt_main_test.setOnClickListener {
            viewModel.addCocktail()
        }

        txt_main_test.setOnLongClickListener {
            viewModel.removeCocktail()
            true
        }

        viewModel.getCocktailById(id) {
            it ?: return@getCocktailById

        }
    }

    override fun configureObserver() {
        super.configureObserver()


//        viewModel.textLiveData.observe {
//            txt_main_test.text = it
//        }

        viewModel.userFullNameLiveData.observe {
            txt_main_test.text = it
        }

        viewModel.cocktailCountLiveData.observe {
            txt_main_test_2.text = it.toString()
        }
    }

}