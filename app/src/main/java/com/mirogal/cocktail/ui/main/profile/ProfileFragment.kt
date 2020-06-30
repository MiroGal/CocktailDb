package com.mirogal.cocktail.ui.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.mirogal.cocktail.R
import com.mirogal.cocktail.ui.auth.AuthActivity
import com.mirogal.cocktail.ui.base.BaseFragment
import com.mirogal.cocktail.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_pager.toolbar
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : BaseFragment() {

    override val contentLayoutResId = R.layout.fragment_profile
    override val viewModel: MainViewModel by viewModels()

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

        btn_test.setOnClickListener {
//            listener?.onStartTestButtonClick()
            calculateMininumAmountOfWoodBars(90000)
        }
    }

    fun calculateMininumAmountOfWoodBars(vararg length: Int) {
        val stockWoodBarLength = 3000
        require(length.all { it < stockWoodBarLength }) { "Some wood planks segment length exceed stock bar length ($stockWoodBarLength mm)" }
//        val woodBarList = ArrayList<Int>()
//        woodBarList.addAll(length.asList())
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
        do {
            currentWoodBarLength = stockWoodBarLength
            wasteLength = 0
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
            } while (longestCut != 0)

        } while (woodBarList.isNotEmpty())
        Log.d("TAG", "numberWoodBar = $numberWoodBar; totalWasteLength = $totalWasteLength")
    }

    fun main() {
        calculateMininumAmountOfWoodBars(4444)
    }


    private fun openAuthActivity() {
        val intent = Intent(activity, AuthActivity::class.java)
        startActivity(intent)
    }


    interface OnFragmentActionListener {
        fun onStartTestButtonClick()
    }

}