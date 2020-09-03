package com.mirogal.cocktail.presentation.ui.main.settings.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.mirogal.cocktail.R
import com.mirogal.cocktail.presentation.extension.sharedViewModels
import com.mirogal.cocktail.presentation.ui.base.dialog.BaseBottomSheetDialogFragment
import com.mirogal.cocktail.presentation.ui.main.settings.SettingsViewModel
import kotlinx.android.synthetic.main.dialog_fragment_language.*

class LanguageDialogFragment : BaseBottomSheetDialogFragment() {

    override val contentLayoutResId = R.layout.dialog_fragment_language
    private var listener: OnActionListener? = null
    private val viewModel: SettingsViewModel by sharedViewModels()

    companion object {
        fun newInstance() = LanguageDialogFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnActionListener
        if (listener == null) {
            throw ClassCastException("$context must implement Listener")
        }
    }

    override fun configureView(view: View, savedInstanceState: Bundle?) {
        btn_dialog_language_en.setOnClickListener {
            viewModel.appLanguageLiveData.value = "en"
            listener?.onDialogLanguageBtnClick()
//            dismiss()
        }
        btn_dialog_language_uk.setOnClickListener {
            viewModel.appLanguageLiveData.value = "uk"
            listener?.onDialogLanguageBtnClick()
//            dismiss()
        }
    }

    interface OnActionListener {
        fun onDialogLanguageBtnClick()
    }

}