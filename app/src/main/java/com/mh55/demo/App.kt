package com.mh55.demo

import com.mh55.easy.App.ConfigBuilder
import com.mh55.easy.EasyApp

open class App : EasyApp() {
    override fun onCreate() {
        super.onCreate()
        ConfigBuilder.apply {
            isOpenLog = true
            mBaseHost = ""
        }
    }

}