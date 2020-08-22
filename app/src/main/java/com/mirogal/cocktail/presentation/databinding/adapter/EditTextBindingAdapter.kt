package com.mirogal.cocktail.presentation.databinding.adapter

import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter


object EditTextBindingAdapter {

    @BindingAdapter("bind_et_setTextWatcher")
    @JvmStatic
    fun EditText.setTextWatcher(textWatcher: TextWatcher) {
        this.addTextChangedListener(textWatcher)
    }

    @BindingAdapter("bind_et_setInputFilter")
    @JvmStatic
    fun EditText.setInputFilter(inputFilter: InputFilter) {
        this.filters = arrayOf(inputFilter)
    }

//    @BindingAdapter("bind_et_setTextWatcher")
//    @JvmStatic
//    fun EditText.setTextWatcher(newValue: MutableLiveData<String?>) {
//        val textWatcher = object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                Log.d(">>>", "+")
//                newValue.value = s.toString()
//            }
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//        }
//        Log.d(">>>", "+++++++++++++++++++")
//        this.addTextChangedListener(textWatcher)
//    }

}