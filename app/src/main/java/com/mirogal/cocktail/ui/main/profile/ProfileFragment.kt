package com.mirogal.cocktail.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.auth.AuthActivity
import com.mirogal.cocktail.ui.base.BaseFragment
import com.mirogal.cocktail.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_history_pager.toolbar
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : BaseFragment<MainViewModel>() {

    override val contentLayoutResId = R.layout.fragment_profile
    override val viewModel: MainViewModel by activityViewModels()


    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.profile_label)

        btn_logout.setOnClickListener { openAuthActivity() }
    }


    private fun openAuthActivity() {
        val intent = Intent(activity, AuthActivity::class.java)
        startActivity(intent)
    }

}