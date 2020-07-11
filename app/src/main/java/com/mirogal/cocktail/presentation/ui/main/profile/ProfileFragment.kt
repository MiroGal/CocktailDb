package com.mirogal.cocktail.presentation.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.model.auth.AuthDataValidStatus
import com.mirogal.cocktail.presentation.ui.auth.AuthActivity
import com.mirogal.cocktail.presentation.ui.auth.dialog.InvalidAuthDataDialogFragment
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.main.profile.dialog.LogoutProfileDialogFragment
import kotlinx.android.synthetic.main.fragment_drink_pager.toolbar
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment<ProfileViewModel>() {

    override val contentLayoutResId = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.profile_label)

        btn_logout.setOnClickListener {
//            showLogoutProfileDialog()
            openAuthActivity()
        }
    }

    private fun showLogoutProfileDialog() {
        val dialogFragment = LogoutProfileDialogFragment.newInstance()
        dialogFragment.show(childFragmentManager, LogoutProfileDialogFragment::class.java.simpleName)
    }

    private fun openAuthActivity() {
        val intent = Intent(activity, AuthActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Close all activities
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Close all activities
        }
        startActivity(intent)
    }

}