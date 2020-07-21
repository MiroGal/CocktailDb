package com.mirogal.cocktail.presentation.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment() {

    protected abstract val contentLayoutResId: Int

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())

        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(contentLayoutResId, null)

        builder.setView(configureView(view, savedInstanceState))

        return configureDialog(builder, savedInstanceState).create()
    }

    protected open fun configureView(view: View, savedInstanceState: Bundle?): View {
        return view
    }

    protected open fun configureDialog(builder: AlertDialog.Builder, savedInstanceState: Bundle?): AlertDialog.Builder {
//        builder.setTitle(R.string.dialog_title)
//                .setNegativeButton(R.string.dialog_btn_cancel, null)
//                .setPositiveButton(R.string.dialog_btn_save, null)
        return builder
    }

}