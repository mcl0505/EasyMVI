package com.mh55.easymvvm.ext

import android.content.Context
import android.content.res.Resources

/**
 *   公司名称: ~漫漫人生路~总得错几步~
 *   创建作者: Android 孟从伦
 *   创建时间: 2022/10/17
 *   功能描述:
 */

fun Context.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}
fun Int.toDp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Float.toDp(): Float = (this * Resources.getSystem().displayMetrics.density)