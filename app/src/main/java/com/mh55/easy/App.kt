package com.mh55.easy

import com.mh55.easymvvm.App.ConfigBuilder
import com.mh55.easymvvm.EasyApplication

open class App : MuApp() {
    override fun onCreate() {
        super.onCreate()
        ConfigBuilder.apply {
            isOpenLog = true
            mBaseHost = ""
        }
    }

}