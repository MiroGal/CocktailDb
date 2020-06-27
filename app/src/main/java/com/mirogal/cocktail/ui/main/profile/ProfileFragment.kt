package com.mirogal.cocktail.ui.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.auth.AuthActivity
import com.mirogal.cocktail.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_drink_history_pager.toolbar
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment() {

    override val contentLayoutResId = R.layout.fragment_profile
    private var listener: OnFragmentActionListener? = null


    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnFragmentActionListener
        if (listener == null) {
            throw ClassCastException("$context must implement Listener")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.profile_label)

        btn_logout.setOnClickListener { openAuthActivity() }

        btn_test.setOnClickListener { listener?.onStartTestButtonClick() }
    }


    private fun openAuthActivity() {
        val intent = Intent(activity, AuthActivity::class.java)
        startActivity(intent)
    }


    interface OnFragmentActionListener {
        fun onStartTestButtonClick()
    }

}