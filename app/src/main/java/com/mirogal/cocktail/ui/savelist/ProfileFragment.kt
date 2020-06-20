package com.mirogal.cocktail.ui.savelist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.auth.AuthActivity
import com.mirogal.cocktail.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_save_list.toolbar

class ProfileFragment() : BaseFragment() {

    override val contentLayoutResId = R.layout.fragment_profile

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.profile_label)

        btn_logout.setOnClickListener { openAuthActivity() }
    }

    private fun openAuthActivity() {
        val intent = Intent(activity, AuthActivity::class.java)
        startActivity(intent)
    }

}