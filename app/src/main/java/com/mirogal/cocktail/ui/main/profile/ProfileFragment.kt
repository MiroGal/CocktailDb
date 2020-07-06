package com.mirogal.cocktail.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.auth.AuthActivity
import com.mirogal.cocktail.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_history_pager.toolbar
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
//            openAuthActivity()
            cut()
        }
    }

    private fun cut() {

        val stockWoodBarLength = 3000

        val woodBarList = ArrayList<Int>()
        for (i in 1..4) {
            woodBarList.add(900)
        }
        for (i in 1..7) {
            woodBarList.add(1275)
        }
        for (i in 1..7) {
            woodBarList.add(375)
        }
        for (i in 1..5) {
            woodBarList.add(1150)
        }
        for (i in 1..8) {
            woodBarList.add(1700)
        }

        var currentWoodBarLength = stockWoodBarLength
        var longestCut = 0
        var wasteLength = 0
        var totalWasteLength = 0
        var numberWoodBar = 0
        var counter = 0

        val stringBuffer = StringBuffer()

        do {
            currentWoodBarLength = stockWoodBarLength
            wasteLength = 0
            counter += 1

            stringBuffer.append("${counter}.\t")
            stringBuffer.append("$currentWoodBarLength")
            stringBuffer.append(" - ")
            do {
                longestCut = 0
                for (i in woodBarList) {
                    if (i in (longestCut + 1)..currentWoodBarLength) {
                        longestCut = i
                    }
                }
                if (longestCut != 0) {
                    currentWoodBarLength -= longestCut
                    woodBarList.remove(longestCut)
                } else {
                    wasteLength = currentWoodBarLength
                    totalWasteLength += wasteLength
                    numberWoodBar += 1
                }

                if (longestCut > 0) {
                    stringBuffer.append("$longestCut / ")
                }

            } while (longestCut != 0)
            stringBuffer.append(" west: $wasteLength\n")

        } while (woodBarList.isNotEmpty())
        stringBuffer.append("Total west: $totalWasteLength")

        tv_result.text = stringBuffer
//        Toast.makeText(requireActivity(), stringBuffer, Toast.LENGTH_LONG).show()
    }


    private fun openAuthActivity() {
        val intent = Intent(activity, AuthActivity::class.java).apply {
            // Close all activities
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

}