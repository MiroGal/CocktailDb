package com.mirogal.cocktail.presentation.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.base.BaseFragment
import com.mirogal.cocktail.presentation.ui.main.profile.dialog.BottomSheetFragment
import com.mirogal.cocktail.presentation.ui.main.profile.dialog.LogoutProfileDialogFragment
import kotlinx.android.synthetic.main.dialog_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_drink_pager.toolbar
import kotlinx.android.synthetic.main.fragment_profile.*
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

        val sheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        btn_logout.setOnClickListener {
//            showLogoutProfileDialog()
            if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                btn_logout.text = "Close sheet"
            } else {
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                btn_logout.text = "Expand sheet"
            }
        }

        btn_logout_2.setOnClickListener { showBottomSheetDialog() }

        sheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(view: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> { }
                    BottomSheetBehavior.STATE_EXPANDED -> { btn_logout.text = "Close Sheet" }
                    BottomSheetBehavior.STATE_COLLAPSED -> { btn_logout.text = "Expand Sheet" }
                    BottomSheetBehavior.STATE_DRAGGING -> { }
                    BottomSheetBehavior.STATE_SETTLING -> { }
                }
            }

            override fun onSlide(view: View, v: Float) {}
        })
    }

    private fun showLogoutProfileDialog() {
        val dialogFragment = LogoutProfileDialogFragment.newInstance()
        dialogFragment.show(childFragmentManager, LogoutProfileDialogFragment::class.java.simpleName)
    }

    private fun showBottomSheetDialog() {
        val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

}