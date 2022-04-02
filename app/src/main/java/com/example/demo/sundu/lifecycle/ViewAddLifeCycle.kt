package com.example.demo.sundu.lifecycle

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class ViewAddLifeCycle @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatButton(context, attrs, defStyleAttr), LifecycleOwner {

    var registlifecycle = LifecycleRegistry(this)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        registlifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        registlifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun getLifecycle(): Lifecycle {
        return registlifecycle
    }


    fun addListenerLifecycleCall(lifecycleObserver: LifecycleObserver) {
        registlifecycle.addObserver(lifecycleObserver)
    }

}