package com.mirogal.cocktail.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
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
            calculateMinimumAmountOfWoodBars(900, 900, 900, 900,
                    1275, 1275, 1275, 1275, 1275, 1275, 1275,
                    375, 375, 375, 375, 375, 375, 375,
                    1150, 1150, 1150, 1150, 1150,
                    1700, 1700, 1700, 1700, 1700, 1700, 1700, 1700)
        }
    }

    private fun openAuthActivity() {
        val intent = Intent(activity, AuthActivity::class.java).apply {
            // Close all activities
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

    private fun calculateMinimumAmountOfWoodBars(vararg length: Int) {

        val stockWoodBarLength = 3000

        require(length.all { it < stockWoodBarLength }) { "Some wood planks segment length exceed stock bar length ($stockWoodBarLength mm)" }

        val woodBarList = length.toMutableList()

        var currentWoodBarNumber = 0
        var currentWoodBarLength: Int
        var longestSegmentLength: Int
        var offCutLength: Int

        var segmentInWoodBarLength: Int
        var totalSegmentLength = 0
        var totalOffCutLength = 0

        val mainSb = StringBuffer()
        val localSb = StringBuffer()

        mainSb.append("Index\t| Sum\t| Off\t| Segments\n\n")

        while (woodBarList.isNotEmpty()) {
            // Оновлюю змінні для поточного бруска
            currentWoodBarNumber += 1
            currentWoodBarLength = stockWoodBarLength
            segmentInWoodBarLength = 0
            localSb.setLength(0)
            do {
                // Визначаю найдовший відрізок в списку коротший за поточну довжину бруска
                longestSegmentLength = 0
                for (i in woodBarList) {
                    if (i in (longestSegmentLength + 1)..currentWoodBarLength) {
                        longestSegmentLength = i
                    }
                }

                if (longestSegmentLength != 0) {
                    // Вкорочую брусок на визначений відрізок і видаляю цей відрізок із списку
                    currentWoodBarLength -= longestSegmentLength
                    woodBarList.remove(longestSegmentLength)
                    segmentInWoodBarLength += longestSegmentLength

                    localSb.append("$longestSegmentLength\t")
                } else {
                    // Відправляю відрізок у відходи
                    offCutLength = currentWoodBarLength
                    totalOffCutLength += offCutLength

                    mainSb.append("${currentWoodBarNumber}.\t| $segmentInWoodBarLength\t| $offCutLength\t| " + localSb + "\n")
                }
            } while (longestSegmentLength != 0)
            totalSegmentLength += segmentInWoodBarLength
        }
        mainSb.append("\nTotal Amount Of Wood Planks: $currentWoodBarNumber\n"
                + "Total Segment Length: $totalSegmentLength\n"
                + "Total Off-Cut Length: $totalOffCutLength")
        tv_result.text = mainSb
    }

}