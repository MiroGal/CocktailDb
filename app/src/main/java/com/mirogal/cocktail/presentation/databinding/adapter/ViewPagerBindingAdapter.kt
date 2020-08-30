package com.mirogal.cocktail.presentation.databinding.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2

object ViewPagerBindingAdapter {

    @BindingAdapter("android:bind_vp_page")
    @JvmStatic
    fun ViewPager2.setPage(pageLiveData: MutableLiveData<Int?>) {
        pageLiveData.value?.let {
            if (pageLiveData.value != null && pageLiveData.value != currentItem) {
                setCurrentItem(pageLiveData.value!!, true)
            }
        }
    }

    @InverseBindingAdapter(attribute = "android:bind_vp_page", event = "android:bind_vp_pageAttrChanged")
    @JvmStatic
    fun ViewPager2.getPage() = currentItem

    @BindingAdapter("android:bind_vp_pageAttrChanged")
    @JvmStatic
    fun ViewPager2.pageChangeListener(listener: InverseBindingListener?) {
        //TODO
    }

}