package com.mh55.easymvvm.ui.loadsir

import android.view.View

/**
 *   公司名称: ~漫漫人生路~总得错几步~
 *   创建作者: Android 孟从伦
 *   创建时间: 2022/10/08
 *   功能描述:
 */
interface ILoadsir {
    /**
     * 获取状态布局
     */
    fun getLoadSirView(): View?=null

    /**
     * 加载中布局
     */
    fun showLoading()

    /**
     * 加载成功，显示根布局
     */
    fun dismissLoading()
}