package com.mh55.demo

import com.mh55.easy.App.ConfigBuilder
import com.mh55.easy.EasyApplication

open class App : EasyApplication() {
    override fun onCreate() {
        super.onCreate()
        ConfigBuilder.apply {
            isOpenLog = true
            mBaseHost = ""
        }
    }

}