package com.mh55.easymvvm.ui.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback

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
     * 显示状态布局
     * @param clazz 状态布局
     */
    fun showCallback(clazz: Class<out Callback>)

    /**
     * 显示状态布局
     * @param clazz 状态布局
     * @param block 布局回调  可在这里面订制布局样式与点击事件
     */
    fun showCallback(clazz: Class<out Callback>,block:(context:Context,view:View) -> Unit)

    /**
     * 隐藏当前正在展示的状态布局  即显示原图
     */
    fun dismissCallback()
}