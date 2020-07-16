package com.mirogal.cocktail.presentation.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.main.profile.dialog.LogoutProfileDialogFragment
import kotlinx.android.synthetic.main.dialog_fragment_profile_logout.*
import kotlinx.android.synthetic.main.fragment_drink_pager.toolbar
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

        btn_logout.setOnClickListener { showBottomSheetDialog() }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetFragment = LogoutProfileDialogFragment()
        bottomSheetFragment.show(childFragmentManager, LogoutProfileDialogFragment::class.java.simpleName)
    }

}