package com.mirogal.cocktail.presentation.databinding.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.mirogal.cocktail.presentation.constant.DrinkPage

object ViewPagerBindingAdapter {

    @BindingAdapter("android:bind_vp_page")
    @JvmStatic
    fun ViewPager2.setPage(pageLiveData: MutableLiveData<DrinkPage>) {
        pageLiveData.value?.let {
            if (pageLiveData.value != null && pageLiveData.value!!.ordinal != currentItem) {
                setCurrentItem(pageLiveData.value!!.ordinal, true)
            }
        }
    }

    @InverseBindingAdapter(attribute = "android:bind_vp_page", event = "android:bind_vp_pageAttrChanged")
    @JvmStatic
    fun ViewPager2.getPage() = DrinkPage.values()[currentItem]

    @BindingAdapter("android:bind_vp_pageAttrChanged")
    @JvmStatic
    fun ViewPager2.pageChangeListener(listener: InverseBindingListener?) {
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                listener?.onChange()
            }
        })
    }

}