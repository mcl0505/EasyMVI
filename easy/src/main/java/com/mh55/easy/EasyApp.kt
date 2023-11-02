package com.mh55.easy

import android.os.Build
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.Utils
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadSir
import com.mh55.easy.manager.AppManager
import com.mh55.easy.ui.loadsir.LoadSirDefaultNetCallback
import com.mh55.easy.utils.LogUtil
import com.mh55.easy.utils.MmkvUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * 程序入口  基类
 */
open class EasyApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        AppManager.register(this)
        CrashUtils.init()
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            //数据存储
            MmkvUtil.init(AppManager.getContext())
            initOtherSDK()
        }
        initLoadSir()
        LogUtil.d("${AppUtils.getAppName()}    启动成功 " +
                "\nAPP包名       => ${AppUtils.getAppPackageName()} " +
                "\nAPP版本名称    => ${AppUtils.getAppVersionName()} " +
                "\nAPP版本号      => ${AppUtils.getAppVersionCode()} " +
                "\n32位 MD5 签名  => ${AppUtils.getAppSignaturesMD5()}"+
                "\n当前手机CPU     => ${Build.CPU_ABI}")
    }

    fun initOtherSDK(){}

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

    open fun getStateCallback():MutableList<Callback>? = mutableListOf()
}