package com.mirogal.cocktail.presentation.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mirogal.cocktail.R

class DrinkFilterDialogFragment : DialogFragment() {

    companion object {
        fun newInstance() = DrinkFilterDialogFragment()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())

        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.layout_dialog_drink_filter, null)

        builder.setView(view)
                .setTitle(R.string.app_name)
                .setNegativeButton(android.R.string.cancel, null)
        return builder.create()
    }

}