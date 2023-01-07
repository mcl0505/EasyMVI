package com.mh55.easymvvm

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadSir
import com.mh55.easymvvm.App.AppManager
import com.mh55.easymvvm.App.AppUtil
import com.mh55.easymvvm.App.ConfigBuilder
import com.mh55.easymvvm.App.CrashHandlerUtil
import com.mh55.easymvvm.ui.loadsir.LoadSirDefaultNetCallback
import com.mh55.easymvvm.utils.MmkvUtil
import com.tencent.bugly.crashreport.CrashReport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * 程序入口  基类
 */
open abstract class EasyApplication : MultiDexApplication() {
    companion object {
        lateinit var instance: EasyApplication
        fun getContext() = instance.applicationContext

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        AppManager.register(this)
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            //数据存储
            MmkvUtil.init(this@EasyApplication)
            //收集异常信息  保存到手机中方便查看
            CrashHandlerUtil.init()

            initOtherSDK()
        }

        initLoadSir()
        initCrashReport()
    }

    fun initOtherSDK(){}

    private fun initCrashReport(){
        val strategy = CrashReport.UserStrategy(this)
        strategy.apply {
            appPackageName = AppUtil.getPackageName()

        }
        CrashReport.initCrashReport(this, ConfigBuilder.mCrashReportAppId, false,strategy)
    }


    private fun initLoadSir(){
        val builder = LoadSir.beginBuilder()
        if ((getStateCallback()?.size ?: 0) > 0){
            getStateCallback()?.forEach {
                builder.addCallback(it)
            }
        }

        builder.addCallback(LoadSirDefaultNetCallback())
        builder.commit()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //初始化方法查出最大数处理
        MultiDex.install(this)
    }

    open fun getStateCallback():MutableList<Callback>? = mutableListOf()
}