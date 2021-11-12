package com.example.demo.sundu.jetpack.databinding.bidirectional

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener

object CustomTimeViewBindAdapter {
    @BindingAdapter(
        value = ["onTimeChanged", "timeAttrChanged"],
        requireAll = false
    )
    @JvmStatic
    fun setValueChangedListener(
        view: CustomTimeView,
        valueChangedListener: ITimeChangeListener?,
        bindingListener: InverseBindingListener?
    ) {
        if (bindingListener == null) {
            view.timeChangeList = valueChangedListener
        } else {
            view.timeChangeList = object : ITimeChangeListener {
                override fun onTimeChange(time: String) {
                    // 通知 ViewModel
                    bindingListener.onChange()
                }

            }
        }
    }
}