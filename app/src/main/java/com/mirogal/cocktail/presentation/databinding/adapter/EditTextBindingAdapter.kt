package com.mirogal.cocktail.presentation.databinding.adapter

import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.mirogal.cocktail.presentation.extension.toEditable


object EditTextBindingAdapter {

    @BindingAdapter("android:bind_et_setTextWatcher")
    @JvmStatic
    fun EditText.setTextWatcher(textWatcher: TextWatcher) {
        this.addTextChangedListener(textWatcher)
    }

    @BindingAdapter("android:bind_et_setInputFilter")
    @JvmStatic
    fun EditText.setInputFilter(inputFilter: InputFilter) {
        this.filters = arrayOf(inputFilter)
    }

    @BindingAdapter("android:bind_tv_AuthActivity_setText")
    @JvmStatic
    fun EditText.setText(data: MutableLiveData<String?>) {
        val oldValue = this.text.toString()
        val newValue = data.value
        if (newValue != oldValue) {
            this.text = newValue!!.toEditable()
        }
    }

}