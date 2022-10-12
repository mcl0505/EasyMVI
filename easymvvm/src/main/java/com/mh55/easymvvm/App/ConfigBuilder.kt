package com.mh55.easymvvm.App

import com.mh55.easymvvm.EasyApplication

object ConfigBuilder {
    //是否开启日志打印
    var isOpenLog = true
    //是否开启日志采集
    var isOpenCarsh = true
    //网络请求地址
    var mBaseHost = ""

    object Path{
        //异常采集保存路径
        var CRASH_LOG_PATH = AppUtil.Cache.getCachePath(EasyApplication.getContext()) + "/crash/"
    }

}