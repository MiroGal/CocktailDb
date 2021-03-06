package com.mirogal.cocktail.presentation.ui.main.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mirogal.cocktail.R
import com.mirogal.cocktail.databinding.FragmentProfileBinding
import com.mirogal.cocktail.presentation.extension.baseViewModels
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.main.profile.dialog.LogoutDialogFragment
import kotlinx.android.synthetic.main.fragment_drink_pager.*
import kotlinx.android.synthetic.main.fragment_profile_content.*

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {

    override val contentLayoutResId = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by baseViewModels()

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun configureDataBinding(binding: FragmentProfileBinding) {
        super.configureDataBinding(binding)
        dataBinding.viewmodel = viewModel
    }

    override fun configureView(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.profile_label)

        btn_logout.setOnClickListener { showLogoutDialog() }
    }

    private fun showLogoutDialog() {
        val dialogFragment = LogoutDialogFragment.newInstance()
        dialogFragment.show(childFragmentManager, LogoutDialogFragment::class.java.name)
    }

}