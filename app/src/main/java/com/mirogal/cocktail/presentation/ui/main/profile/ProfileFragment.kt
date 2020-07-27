package com.mirogal.cocktail.presentation.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.basenative.BaseFragment
import com.mirogal.cocktail.presentation.ui.main.profile.dialog.LogoutDialogFragment
import kotlinx.android.synthetic.main.fragment_drink_pager.*
import kotlinx.android.synthetic.main.fragment_profile_content.*

class ProfileFragment : BaseFragment<ProfileViewModel>() {

    override val contentLayoutResId = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.profile_label)

        btn_logout.setOnClickListener { showLogoutDialog() }
    }

    private fun showLogoutDialog() {
        val dialogFragment = LogoutDialogFragment.newInstance()
        dialogFragment.show(childFragmentManager, LogoutDialogFragment::class.java.simpleName)
    }

}