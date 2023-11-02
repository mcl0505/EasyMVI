package com.mh55.easy.App

import com.mh55.easy.R

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
}