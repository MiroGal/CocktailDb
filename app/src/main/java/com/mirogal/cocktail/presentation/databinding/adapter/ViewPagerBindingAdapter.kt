package com.mirogal.cocktail.presentation.databinding.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2

object ViewPagerBindingAdapter {

    @BindingAdapter("android:bind_vp_page")
    @JvmStatic
    fun ViewPager2.getPage(newPage: MutableLiveData<Int?>) {
        if (newPage.value != null && newPage.value != currentItem)
            setCurrentItem(newPage.value!!, true)
    }

    @InverseBindingAdapter(attribute = "android:bind_vp_page", event = "android:bind_vp_pageAttrChanged")
    @JvmStatic
    fun ViewPager2.setPage() = currentItem

}