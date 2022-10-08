package com.mh55.easymvvm

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadSir
import com.mh55.easymvvm.App.CrashHandlerUtil
import com.mh55.easymvvm.ui.loadsir.LoadingCallback
import com.mh55.easymvvm.utils.MmkvUtil

/**
 * 程序入口  基类
 */
abstract class EasyApplication : MultiDexApplication() {
    companion object {
        lateinit var instance: EasyApplication
        fun getContext() = instance.applicationContext

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MmkvUtil.init(this)
        CrashHandlerUtil.init()
        initLoadSir()
    }

    private fun initLoadSir(){
        val builder = LoadSir.beginBuilder()
        if (getStateCallback()?.size?:0>0){
            getStateCallback()?.forEach {
                builder.addCallback(it)
            }
        }

        builder.commit()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //初始化方法查出最大数处理
        MultiDex.install(this)
    }

    open fun getStateCallback():MutableList<Callback>? = mutableListOf()
}