package com.mh55.easy

import com.kingja.loadsir.callback.Callback
import com.mh55.easymvvm.App.ConfigBuilder
import com.mh55.easymvvm.EasyApplication
import com.mh55.easymvvm.ui.loadsir.LoadingCallback

open class App : EasyApplication() {
    override fun onCreate() {
        super.onCreate()
        ConfigBuilder.apply {
            isOpenLog = true
            mBaseHost = ""
        }
    }

    override fun getStateCallback(): MutableList<Callback>? {
        return mutableListOf(LoadingCallback())
    }
}