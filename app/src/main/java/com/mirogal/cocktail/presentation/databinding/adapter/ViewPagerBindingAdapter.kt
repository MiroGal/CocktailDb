package com.mirogal.cocktail.presentation.databinding.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2

object ViewPagerBindingAdapter {

    @BindingAdapter("android:bind_vp_currentPage")
    @JvmStatic
    fun ViewPager2.setPage(pageLiveData: MutableLiveData<Int?>) {
        pageLiveData.value?.let {
            if (pageLiveData.value != null && pageLiveData.value != currentItem) {
                setCurrentItem(pageLiveData.value!!, true)
            }
        }
    }

    @InverseBindingAdapter(attribute = "android:bind_vp_currentPage", event = "android:bind_vp_currentPageAttrChanged")
    @JvmStatic
    fun ViewPager2.getPage() = currentItem

}