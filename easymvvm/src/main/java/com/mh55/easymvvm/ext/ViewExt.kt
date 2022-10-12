package com.mh55.easymvvm.ext

import android.os.SystemClock
import android.view.View
import com.mh55.easymvvm.R

private var lastClickTime: Long = 0
//防止快速点击造成打开多个界面   只允许在 1秒内只能点击一次  single(2000){}   可自定义时间
/**
 * 防止快速点击造成打开多个界面
 */
fun <T : View> T.singleClick(time: Int = 500, block: (T) -> Unit) {
    this.setOnClickListener {
        val curClickTime = SystemClock.uptimeMillis()
        val lastClickTime = (it.getTag(R.id.singleClickId) as? Long) ?:0
        // 超过点击间隔后再将lastClickTime重置为当前点击时间
        it.setTag(R.id.singleClickId,lastClickTime)
        if (curClickTime - lastClickTime >= time) {
            block(it as T)
        }
    }
}


/**
 * view 显示隐藏
 */
fun View.visibleOrGone(show: Boolean) {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

/**
 * view 显示与占位
 */
fun View.visibleOrInvisible(show: Boolean) {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}