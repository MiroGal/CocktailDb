package com.mirogal.cocktail.presentation.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.ui.main.history.dialog.adapter.ListAdapter


abstract class BaseDialogFragment<ViewModel : BaseViewModel> : DialogFragment() {

    protected abstract val contentLayoutResId: Int

    protected abstract val viewModel: ViewModel

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