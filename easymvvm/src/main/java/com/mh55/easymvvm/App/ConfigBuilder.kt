package com.mh55.easymvvm.App

import com.mh55.easymvvm.EasyApplication
import com.mh55.easymvvm.R

object ConfigBuilder {
    //是否开启日志打印
    var isOpenLog = true
    //是否开启日志采集
    var isOpenCarsh = true
    //网络请求地址
    var mBaseHost = ""
    //是否开启全局置灰  用作特殊节日默哀处理
    var isGray = true
    var mImagePlaceholder = R.mipmap.icon_avatar_default
    //Bugly appID
    var mCrashReportAppId = "73fef7c8f3"
    object Path{
        //异常采集保存路径
        var CRASH_LOG_PATH = AppUtil.Cache.getCachePath(EasyApplication.getContext()) + "/crash/"
    }

}