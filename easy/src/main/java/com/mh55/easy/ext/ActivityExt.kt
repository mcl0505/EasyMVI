package com.mh55.easy.ext

import android.app.Activity
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.view.View
import com.mh55.easy.App.ConfigBuilder

/**
 * Activity  相关扩展
 */

inline fun Activity.changeGray(isGray:Boolean){
    //全局置灰处理
    val paint = Paint()
    val cm = ColorMatrix()
    cm.setSaturation(if (isGray) 0f else 1f)
    paint.colorFilter = ColorMatrixColorFilter(cm)
    this.window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
}